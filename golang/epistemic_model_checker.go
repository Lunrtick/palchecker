package main

import (
	"fmt"
	"strings"
	"sync"

	"k8s.io/apimachinery/pkg/util/sets"
)

type FormulaType int
type QueryType int
type Variable int
type Agent int

const (
	Top FormulaType = iota
	Bot
	And
	Or
	Not
	Proposition
	KnowsThat
	KnowsWhether
	PubAnnounceBox
	PubAnnounceDiamond
)

const (
	WHERE QueryType = iota
	TRUE
	VALID
)

type Formula struct {
	Type         FormulaType
	Proposition  Variable
	Children     []*Formula
	Agent        Agent
	Announcement *Formula
}

type Query struct {
	Type         QueryType
	Formula      *Formula
	Propositions []Variable
}

type KripkeModel struct {
	Worlds []*KripkeWorld
}

type KripkeWorld struct {
	Id               int
	TruePropositions *sets.Int
	Relations        map[Agent][]*KripkeWorld
	Model            *KripkeModel
}

func formulaMapString(fs []*Formula, f func(*Formula) string) []string {
	fsMapped := make([]string, 0, len(fs))
	for _, v := range fs {
		fsMapped = append(fsMapped, f(v))
	}
	return fsMapped
}

func formulaString(f *Formula) string {
	return f.String()
}
func bracketed(s string, withBrackets bool) string {
	if withBrackets {
		return "(" + s + ")"
	} else {
		return s
	}
}
func (f *Formula) String() string {
	switch f.Type {
	case Top:
		return "⊤"
	case Bot:
		return "⊥"
	case And:
		return bracketed(strings.Join(formulaMapString(f.Children, formulaString), " ∧ "), true)
	case Or:
		return bracketed(strings.Join(formulaMapString(f.Children, formulaString), " ∨ "), true)
	case Not:
		return "¬" + bracketed(f.Children[0].String(), true)
	case Proposition:
		return fmt.Sprint(f.Proposition)
	case KnowsThat:
		return bracketed(fmt.Sprint(f.Agent)+" knows that "+f.Children[0].String(), true)
	case KnowsWhether:
		return bracketed(fmt.Sprint(f.Agent)+" knows whether "+f.Children[0].String(), true)
	case PubAnnounceBox:
		return "[!" + f.Announcement.String() + "] " + bracketed(f.Children[0].String(), true)
	case PubAnnounceDiamond:
		return "<!" + f.Announcement.String() + "> " + bracketed(f.Children[0].String(), true)
	default:
		panic("Unknown Formula")
	}
}

func (w *KripkeWorld) String() string {
	return fmt.Sprintf("W(%s : %d)", fmt.Sprint(w.TruePropositions.List()), w.Id)
}

func min(a, b int) int {
	if a > b {
		return b
	} else {
		return a
	}
}

func GenerateModel(variables *[]int, stateLaw *Formula, observations *map[Agent][]int) *KripkeModel {
	model := KripkeModel{
		Worlds: make([]*KripkeWorld, 0, 1<<len(*variables)),
	}
	x := 1
	for w := range Powerset(variables) {
		bs := sets.NewInt(w...)
		if Valid(stateLaw, &bs) {
			model.Worlds = append(model.Worlds, &KripkeWorld{
				Id:               x,
				TruePropositions: &bs,
				Relations:        make(map[Agent][]*KripkeWorld),
				Model:            &model,
			})
			x += 1
		}
	}
	fmt.Println("Done With World Init")
	all_vars := sets.NewInt((*variables)...)

	const chunkSize = 100
	wg := sync.WaitGroup{}
	for i := 0; i < len(model.Worlds); i += chunkSize {
		chunk := model.Worlds[i:min(i+chunkSize, len(model.Worlds))]
		wg.Add(1)
		go func() {
			for _, w := range chunk {
				for agent, obs_list := range *observations {
					obs := sets.NewInt(obs_list...)
					unobserved := all_vars.Difference(obs)
					observablyTrue := w.TruePropositions.Difference(unobserved)
					for _, w1 := range model.Worlds {
						if observablyTrue.Equal(w1.TruePropositions.Difference(unobserved)) {
							w.Relations[agent] = append(w.Relations[agent], w1)
						}
					}
				}
			}
			defer wg.Done()
		}()
	}
	wg.Wait()
	fmt.Println("Done Generating")
	return &model
}

func Powerset(variables *[]int) <-chan []int {
	ch := make(chan []int, 1000)
	go func() {
		for i := 0; i < 1<<len(*variables); i++ {
			var these_results []int
			for j := 0; j < len(*variables); j++ {
				if (i & (1 << j)) != 0 {
					these_results = append(these_results, (*variables)[j])
				}
			}
			ch <- these_results
			// results = append(results, these_results)
		}
		close(ch)
	}()
	return ch
}

func Valid(f *Formula, true_propositions *sets.Int) bool {
	switch f.Type {
	case Top:
		return true
	case Bot:
		return false
	case And:
		for _, x := range f.Children {
			v := Valid(x, true_propositions)
			if !v {
				return false
			}
		}
		return true
	case Or:
		for _, x := range f.Children {
			v := Valid(x, true_propositions)
			if v {
				return true
			}
		}
		return false
	case Not:
		return !Valid(f.Children[0], true_propositions)
	case Proposition:
		return true_propositions.Has(int(f.Proposition))
	default:
		panic("Unknown Formula")
	}
}

// func TrueAtWorld(f *Formula, world *KripkeWorld, eliminatedWorlds sets.Int) bool {
// 	formula := f.String()
// 	w := world.String()
// 	ret := trueAtWorld(f, world, eliminatedWorlds)
// 	fmt.Println("At ", w, ", ", formula, " is ", ret)
// 	return ret
// }

func TrueAtWorld(f *Formula, world *KripkeWorld, eliminatedWorlds *sets.Int) bool {
	switch f.Type {
	case Top:
		return true
	case Bot:
		return false
	case And:
		for _, x := range f.Children {
			if !TrueAtWorld(x, world, eliminatedWorlds) {
				return false
			}
		}
		return true
	case Or:
		for _, x := range f.Children {
			if TrueAtWorld(x, world, eliminatedWorlds) {
				return true
			}
		}
		return false
	case Not:
		return !TrueAtWorld(f.Children[0], world, eliminatedWorlds)
	case Proposition:
		return world.TruePropositions.Has(int(f.Proposition))
	case KnowsThat:
		return TrueAtWorldKnowsThat(f.Children[0], world, f.Agent, eliminatedWorlds)
	case KnowsWhether:
		return TrueAtWorldKnowsThat(f.Children[0], world, f.Agent, eliminatedWorlds) || TrueAtWorldKnowsThat(&Formula{Type: Not, Children: f.Children}, world, f.Agent, eliminatedWorlds)
	case PubAnnounceBox:
		return !TrueAtWorldPublicAnnouncement(world, f.Announcement, &Formula{Type: Not, Children: f.Children}, eliminatedWorlds)
	case PubAnnounceDiamond:
		return TrueAtWorldPublicAnnouncement(world, f.Announcement, f.Children[0], eliminatedWorlds)
	default:
		panic("Unknown Formula")
	}
}

func TrueAtWorldKnowsThat(f *Formula, world *KripkeWorld, agent Agent, eliminatedWorlds *sets.Int) bool {
	moreThanOne := false
	for _, w := range world.Relations[agent] {
		if eliminatedWorlds.Has(w.Id) {
			continue
		}
		v := TrueAtWorld(f, w, eliminatedWorlds)
		moreThanOne = true
		if !v {
			return false
		}
	}
	return moreThanOne
}
func TrueAtWorldPublicAnnouncement(world *KripkeWorld, announcement *Formula, formula *Formula, eliminatedWorlds *sets.Int) bool {
	if eliminatedWorlds.Has(world.Id) || !TrueAtWorld(announcement, world, eliminatedWorlds) {
		return false
	}
	newEliminations := sets.NewInt()
	wg := sync.WaitGroup{}
	l := sync.Mutex{}
	for _, w := range world.Model.Worlds {
		if eliminatedWorlds.Has(w.Id) {
			continue
		}
		thisWorld := w
		wg.Add(1)
		go func() {
			defer wg.Done()
			if !TrueAtWorld(announcement, thisWorld, eliminatedWorlds) {
				l.Lock()
				defer l.Unlock()
				newEliminations.Insert(thisWorld.Id)
			}
		}()
	}
	wg.Wait()
	u := eliminatedWorlds.Union(newEliminations)
	return TrueAtWorld(formula, world, &u)
}

func SolveModel(model *KripkeModel, queries *[]*Query) {
	for _, q := range *queries {
		switch q.Type {
		case WHERE:
			for _, w := range model.Worlds {
				u := sets.NewInt()
				if TrueAtWorld(q.Formula, w, &u) {
					fmt.Printf("%s,", w.String())
				}
			}
		case VALID:
			valid := true
			for _, w := range model.Worlds {
				u := sets.NewInt()
				if !TrueAtWorld(q.Formula, w, &u) {
					fmt.Print("Not Valid")
					valid = false
					break
				}
			}
			if valid {
				fmt.Print("Valid!")
			}
		}
	}
	x := 1
	fmt.Println(x)
}
