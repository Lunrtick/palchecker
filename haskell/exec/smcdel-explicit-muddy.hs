module Main where

import Data.Function
import Data.List
import SMCDEL.Examples.MuddyChildren
import qualified SMCDEL.Explicit.DEMO_S5 as DEMO_S5
import qualified SMCDEL.Explicit.S5
import SMCDEL.Internal.Help (apply)
import SMCDEL.Language
import qualified SMCDEL.Symbolic.S5
import System.Environment (getArgs, getProgName)
import System.Exit (exitFailure, exitSuccess)
import System.IO (hPutStrLn, stderr)

checkForm :: Int -> Int -> Form
checkForm n 0 = nobodyknows n
checkForm n k = PubAnnounce (nobodyknows n) (checkForm n (k -1))

findNumberWith :: (Int -> Int -> a, a -> Form -> Bool) -> Int -> Int -> Int
findNumberWith (start, evalfunction) n m = k
  where
    k
      | loop 0 == (m -1) = m -1
      | otherwise = error $ "wrong Muddy Children result: " ++ show (loop 0)
    loop count =
      if evalfunction (start n m) (PubAnnounce (father n) (checkForm n count))
        then loop (count + 1)
        else count

mudPs :: Int -> [Prp]
mudPs n = [P 1 .. P n]

findNumberCacBDD :: Int -> Int -> Int
findNumberCacBDD = findNumberWith (cacMudScnInit, SMCDEL.Symbolic.S5.evalViaBdd)
  where
    cacMudScnInit n m = (SMCDEL.Symbolic.S5.KnS (mudPs n) (SMCDEL.Symbolic.S5.boolBddOf Top) [(show i, delete (P i) (mudPs n)) | i <- [1 .. n]], mudPs m)

mudKrpInit :: Int -> Int -> SMCDEL.Explicit.S5.PointedModelS5
mudKrpInit n m = (SMCDEL.Explicit.S5.KrMS5 ws rel val, cur)
  where
    ws = [0 .. (2 ^ n -1)]
    rel = [(show i, erelFor i) | i <- [1 .. n]]
      where
        erelFor i =
          sort $
            map sort $
              groupBy ((==) `on` setForAt i) $
                sortOn (setForAt i) ws
        setForAt i s = delete (P i) $ setAt s
        setAt s = map fst $ filter snd (apply val s)
    val = zip ws table
    ((cur, _) : _) = filter (\(_, ass) -> sort (map fst $ filter snd ass) == [P 1 .. P m]) val
    table = foldl buildTable [[]] [P k | k <- [1 .. n]]
    buildTable partrows p = [(p, v) : pr | v <- [True, False], pr <- partrows]

mudDemoKrpInit :: Int -> Int -> DEMO_S5.EpistM [Bool]
mudDemoKrpInit n m = DEMO_S5.Mo states agents [] rels points
  where
    states = DEMO_S5.bTables n
    agents = map DEMO_S5.Ag [1 .. n]
    rels =
      [ ( DEMO_S5.Ag i,
          [ [tab1 ++ [True] ++ tab2, tab1 ++ [False] ++ tab2]
            | tab1 <- DEMO_S5.bTables (i -1),
              tab2 <- DEMO_S5.bTables (n - i)
          ]
        )
        | i <- [1 .. n]
      ]
    points = [replicate (n - m) False ++ replicate m True]

findNumberDemoS5 :: Int -> Int -> Int
findNumberDemoS5 n m = findNumberDemoLoop n m 0 start
  where
    start = DEMO_S5.updPa (mudDemoKrpInit n m) (DEMO_S5.fatherN n)

findNumberDemoLoop :: Int -> Int -> Int -> DEMO_S5.EpistM [Bool] -> Int
findNumberDemoLoop n m count curMod =
  if DEMO_S5.isTrue curMod (DEMO_S5.dont n)
    then findNumberDemoLoop n m (count + 1) (DEMO_S5.updPa curMod (DEMO_S5.dont n))
    else count

runMC :: String -> Int -> Int
runMC s n
  | s == "explicit" = findNumberDemoS5 n n
  | s == "symbolic" = findNumberCacBDD n n

main :: IO ()
main = do
  args <- getArgs
  case args of
    [explicitOrSymbolic, numChildren]
      | [(n, _)] <- reads numChildren ->
        print (runMC explicitOrSymbolic n)
    _ -> do
      name <- getProgName
      hPutStrLn stderr $ "usage: " ++ name ++ " <'explicit' | 'symbolic'> <num_children:int>"
      exitFailure
