package main

import "flag"

func main() {
	var numChildren int
	flag.IntVar(&numChildren, "c", 3, "The number of children in the model.")
	flag.Parse()

	vars, law, obs, queries := BuildMC(numChildren)
	model := GenerateModel(vars, law, obs)
	SolveModel(model, queries)
}
