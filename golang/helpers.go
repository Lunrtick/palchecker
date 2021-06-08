package main

import "k8s.io/apimachinery/pkg/util/sets"

func makeRange(min, max int) []int {
	a := make([]int, max-min+1)
	for i := range a {
		a[i] = min + i
	}
	return a
}

func buildAnnouncements(vars *[]int, depth int) []*Formula {
	if depth == len(*vars) {
		props := make([]*Formula, 0, len(*vars))
		for _, v := range *vars {
			props = append(props, &Formula{Type: Proposition, Proposition: Variable(v)})
		}
		return []*Formula{{Type: And, Children: props}}
	} else {
		knows := make([]*Formula, 0, len(*vars))
		for _, v := range *vars {
			knows = append(knows, &Formula{Type: KnowsWhether, Agent: Agent(v), Children: []*Formula{{Type: Proposition, Proposition: Variable(v)}}})
		}
		return []*Formula{
			{
				Type:         PubAnnounceBox,
				Announcement: &Formula{Type: Not, Children: []*Formula{{Type: Or, Children: knows}}},
				Children:     buildAnnouncements(vars, depth+1),
			},
		}
	}
}

func buildQuery(vars *[]int) *Query {
	disj := make([]*Formula, 0, len(*vars))
	for _, v := range *vars {
		disj = append(disj, &Formula{Type: Proposition, Proposition: Variable(v)})
	}
	q := &Query{Type: VALID, Formula: &Formula{
		Type: PubAnnounceBox,
		Announcement: &Formula{
			Type:     Or,
			Children: disj,
		},
		Children: buildAnnouncements(vars, 1),
	}}
	return q
}

func BuildMC(n int) (*[]int, *Formula, *map[Agent][]int, *[]*Query) {
	vars := makeRange(1, n)
	law := &Formula{Type: Top}
	obs := make(map[Agent][]int, n)
	all_obs := sets.NewInt(vars...)
	for _, v := range vars {
		obs[Agent(v)] = all_obs.Difference(sets.NewInt(v)).List()
	}
	queries := []*Query{buildQuery(&vars)}

	return &vars, law, &obs, &queries
}
