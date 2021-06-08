package main

import (
	"fmt"
	"testing"

	"k8s.io/apimachinery/pkg/util/sets"
)

func TestValidLeaves(t *testing.T) {
	top := Formula{Type: Top}
	trueprops := sets.NewInt()

	if Valid(&top, &trueprops) != true {
		t.Error("top not valid")
		t.Fail()
	}
	bot := Formula{Type: Bot}
	if Valid(&bot, &trueprops) != false {
		t.Error("bot not valid")
		t.Fail()
	}

	a := Formula{Type: Proposition, Proposition: 1}
	trueprops.Insert(1)
	if Valid(&a, &trueprops) != true {
		t.Errorf("1 not in %s? %t", fmt.Sprint(trueprops.List()), trueprops.Has(1))
		t.Fail()
	}
	trueprops.Delete(1)
	trueprops.Insert(2)
	if Valid(&a, &trueprops) != false {
		t.Error("a in {2}?")
		t.Fail()
	}
}

func TestValidAnd(t *testing.T) {
	trueprops := sets.NewInt()
	a := Formula{Type: Proposition, Proposition: 1}
	b := Formula{Type: Proposition, Proposition: 2}
	and_with_a_b := Formula{Type: And, Children: []*Formula{&a, &b}}

	trueprops.Insert(1)
	trueprops.Insert(2)
	if Valid(&and_with_a_b, &trueprops) != true {
		t.Errorf("1,2 not in %s", fmt.Sprint(trueprops.List()))
		t.Fail()
	}

	trueprops.Delete(2)
	if Valid(&and_with_a_b, &trueprops) != false {
		t.Errorf("1,2 in %s", fmt.Sprint(trueprops.List()))
		t.Fail()
	}

	trueprops.Insert(2)
	trueprops.Delete(1)
	if Valid(&and_with_a_b, &trueprops) != false {
		t.Errorf("1,2 in %s", fmt.Sprint(trueprops.List()))
		t.Fail()
	}

	trueprops.Delete()
	if Valid(&and_with_a_b, &trueprops) != false {
		t.Errorf("1,2 in %s", fmt.Sprint(trueprops.List()))
		t.Fail()
	}

}

func TestValidOr(t *testing.T) {
	trueprops := sets.NewInt()
	a := Formula{Type: Proposition, Proposition: 1}
	b := Formula{Type: Proposition, Proposition: 2}
	or_with_a_b := Formula{Type: Or, Children: []*Formula{&a, &b}}

	trueprops.Insert(1)
	trueprops.Insert(2)
	if Valid(&or_with_a_b, &trueprops) != true {
		t.Errorf("1,2 not in %s", fmt.Sprint(trueprops.List()))
		t.Fail()
	}

	trueprops.Delete(2)
	if Valid(&or_with_a_b, &trueprops) != true {
		t.Errorf("1,2 in %s", fmt.Sprint(trueprops.List()))
		t.Fail()
	}
	trueprops.Insert(2)
	trueprops.Delete(1)
	if Valid(&or_with_a_b, &trueprops) != true {
		t.Errorf("1,2 in %s", fmt.Sprint(trueprops.List()))
		t.Fail()
	}

	trueprops = sets.NewInt()
	if Valid(&or_with_a_b, &trueprops) != false {
		t.Errorf("1,2 in %s", fmt.Sprint(trueprops.List()))
		t.Fail()
	}

}

func TestValidNot(t *testing.T) {
	trueprops := sets.NewInt()
	top := Formula{Type: Top}
	bot := Formula{Type: Bot}
	not_with_top := Formula{Type: Not, Children: []*Formula{&top}}
	not_with_bot := Formula{Type: Not, Children: []*Formula{&bot}}

	if Valid(&not_with_top, &trueprops) != false {
		t.Error("~⊤ is true?")
		t.Fail()
	}
	if Valid(&not_with_bot, &trueprops) != true {
		t.Error("~⊥ is false?")
		t.Fail()
	}
}

func TestMakeWorlds(t *testing.T) {
	variables := []int{1, 2, 3}
	stateLaw := Formula{Type: Top}
	obs := map[Agent][]int{
		1: {2, 3},
		2: {1, 3},
		3: {1, 2},
	}
	model := GenerateModel(&variables, &stateLaw, &obs)
	fmt.Printf("%d Worlds:\n", len(model.Worlds))
	fmt.Println(model.Worlds)
}

func BenchmarkMuddyChildren(b *testing.B) {
	vars, law, obs, queries := BuildMC(10)
	for i := 0; i < b.N; i++ {
		model := GenerateModel(vars, law, obs)
		SolveModel(model, queries)
	}
}
