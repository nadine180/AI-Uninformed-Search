import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

public abstract class Problem {
	State initState;
	Operator[] operators;
	public static int depthlimit = 0;
	private static boolean vis = false;

	public Problem(State initState, Operator[] operators) {
		this.initState = initState;
		this.operators = operators;
	}

	public abstract int pathCost(State s, String QuinFun);

	public abstract boolean goalTest(State n);

	public abstract State transitionFun(Operator operator, State oldState);

	public String General_Search(Problem p, String QuinFun)

	{

		Search_Node root = new Search_Node(p.initState);
		Queue<Search_Node> nodes = new LinkedList<Search_Node>();
		nodes.add(root);
		HashSet<String> hm = new HashSet<String>();
		hm.add(((State) root.state).state);
		int numNodes = 0;
		int maxdepth = 0;
		boolean checkID = !(QuinFun.equals("ID"));

		while (checkID || (!checkID && maxdepth <= depthlimit)) {
			Search_Node node = null;

			if (nodes.size() == 0) {
				System.out.println("Failed");
				return null;
			}

			else {
				node = nodes.poll();
				numNodes += 1;
				if (node.depth > maxdepth) {
					maxdepth = node.depth;
				}

				if (p.goalTest(node.state)) {
					hm.clear();
					StringTokenizer st = new StringTokenizer(
							((State) node.state).state, ";");
					String s = ";" + (st.nextToken()) + ";" + numNodes;
					int depth = node.depth;
					for (int i = depth; i > 0; i--) {

						if (i < depth)
							s = node.operator.toString().toLowerCase() + ","
									+ s;

						else
							s = node.operator.toString().toLowerCase() + s;
						if (vis) {

							p.printGrid(((State) node.state).state);

							System.out
									.println("----------------------- "
											+ "STEP "
											+ i
											+ " ---------------------------------------");
						}
						node = node.parent;

					}
					System.out.println(s);
					return s;
				}
			}
			ArrayList<Search_Node> expandedNodes = new ArrayList<Search_Node>();
			for (int i = 0; i < p.operators.length; i++) {

				State newState = transitionFun(p.operators[i], node.state);
				String check = ((State) newState).state;

				if (!hm.contains(check.substring(2, check.length()))) {

					Search_Node n = new Search_Node(newState, node, pathCost(
							newState, QuinFun), node.depth + 1, p.operators[i]);
					if ((!checkID && n.depth <= depthlimit) || checkID) {
						expandedNodes.add(n);
						hm.add(check.substring(2, check.length()));
					}
				}

			}

			switch (QuinFun) {
			case "BF":
				nodes = BF(nodes, expandedNodes);
				break;
			case "DF":
				nodes = DF(nodes, expandedNodes);
				break;
			case "UC":
				nodes = UC(nodes, expandedNodes);
				break;
			case "ID":
				nodes = DF(nodes, expandedNodes);
				break;
			case "GR1":
				nodes = GR1(nodes, expandedNodes);
				break;
			case "GR2":
				nodes = GR2(nodes, expandedNodes);
				break;
			case "AS1":
				nodes = AS1(nodes, expandedNodes);
				break;
			case "AS2":
				nodes = AS1(nodes, expandedNodes);
				break;
			default:
				break;
			}

		}
		return null;

	}

	// Searching Algorithms
	public Queue<Search_Node> BF(Queue<Search_Node> q,
			ArrayList<Search_Node> expandedNodes) {

		for (int i = 0; i < expandedNodes.size(); i++) {
			q.add(expandedNodes.get(i));
		}

		return q;

	}

	public Queue<Search_Node> DF(Queue<Search_Node> q,
			ArrayList<Search_Node> expandedNodes) {

		Queue<Search_Node> temp = new LinkedList<Search_Node>();

		for (int i = 0; i < expandedNodes.size(); i++) {
			temp.add(expandedNodes.get(i));
		}
		while (!q.isEmpty()) {
			temp.add(q.poll());
		}

		return temp;

	}

	public String ID(Problem p) {
		while (true) {
			String sol = General_Search(p, "ID");
			if (sol != null)
				return sol;
			else {
				depthlimit += 1;
			}

		}

	}

	public Queue<Search_Node> UC(Queue<Search_Node> q,
			ArrayList<Search_Node> expandedNodes) {

		PriorityQueue<Search_Node> pq = new PriorityQueue<Search_Node>();

		for (int i = 0; i < expandedNodes.size(); i++)
			pq.add(expandedNodes.get(i));

		while (!pq.isEmpty())
			q.add(pq.poll());

		return q;
	}

	public Queue<Search_Node> GR1(Queue<Search_Node> q,
			ArrayList<Search_Node> expandedNodes) {
		PriorityQueue<Search_Node> pq = new PriorityQueue<Search_Node>();

		for (int i = 0; i < expandedNodes.size(); i++) {
			pq.add(expandedNodes.get(i));
		}
		while (!q.isEmpty()) {
			pq.add(q.poll());

		}

		return pq;

	}

	public Queue<Search_Node> GR2(Queue<Search_Node> q,
			ArrayList<Search_Node> expandedNodes) {
		PriorityQueue<Search_Node> pq = new PriorityQueue<Search_Node>();

		for (int i = 0; i < expandedNodes.size(); i++) {
			pq.add(expandedNodes.get(i));
		}
		while (!pq.isEmpty()) {
			q.add(pq.poll());
		}
		return q;
	}

	public Queue<Search_Node> AS1(Queue<Search_Node> q,
			ArrayList<Search_Node> expandedNodes) {
		PriorityQueue<Search_Node> pq = new PriorityQueue<Search_Node>();

		for (int i = 0; i < expandedNodes.size(); i++) {
			pq.add(expandedNodes.get(i));
		}
		while (!pq.isEmpty()) {
			q.add(pq.poll());
		}
		return q;

	}

	public Queue<Search_Node> AS2(Queue<Search_Node> q,
			ArrayList<Search_Node> expandedNodes) {
		PriorityQueue<Search_Node> pq = new PriorityQueue<Search_Node>();

		for (int i = 0; i < expandedNodes.size(); i++) {
			pq.add(expandedNodes.get(i));
		}

		while (!pq.isEmpty()) {
			q.add(pq.poll());
		}
		return q;

	}

	abstract void printGrid(String state);

	public static boolean getVis() {
		return vis;
	}

	public static void setVis(boolean vis) {
		Problem.vis = vis;
	}

}