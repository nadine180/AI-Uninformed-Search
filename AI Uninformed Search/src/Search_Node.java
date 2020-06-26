
public class Search_Node implements Comparable<Search_Node> {
	State state;
	Search_Node parent;
	int pathCost;
	int depth;
	Operator operator;

	public Search_Node(State state, Search_Node parent, int pathCost,
			int depth, Operator operator) {
		this.state = state;
		this.parent = parent;
		this.pathCost = pathCost;
		this.depth = depth;
		this.operator = operator;
	}

	public Search_Node(State state) {
		this.state = state;
		pathCost = 0;
		depth = 0;
		parent = null;
	}

	@Override
	public int compareTo(Search_Node a) {

		if (this.pathCost > a.pathCost) {
			return 1;
		}
		if (this.pathCost < a.pathCost)
			return -1;

		return 0;
	}

}
