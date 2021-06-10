package main

import "flag"

func main() {
	var numChildren int
	var stopAfterGenerate int
	flag.IntVar(&numChildren, "c", 3, "The number of children in the model.")
	flag.IntVar(&stopAfterGenerate, "s", 0, "Stop after generating the model (don't solve)")
	flag.Parse()

	vars, law, obs, queries := BuildMC(numChildren)
	model := GenerateModel(vars, law, obs)
	if stopAfterGenerate != 0 {
		return
	}
	SolveModel(model, queries)
}
