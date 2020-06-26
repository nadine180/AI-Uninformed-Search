import java.awt.Point;
import java.util.HashSet;
import java.util.Iterator;
import java.util.StringTokenizer;

public class End_Game extends Problem {

	State initState;
	Operator[] operators = { Operator.SNAP, Operator.COLLECT, Operator.UP,
			Operator.DOWN, Operator.LEFT, Operator.RIGHT, Operator.KILL };

	public End_Game(EndGameState initState, Operator[] operators) {

		super(initState, operators);

		this.initState = initState;

	}

	@Override
	public int pathCost(State s, String qFunc) {
		if (qFunc.equals("GR1")) {
			return heauristicStones(s);
		}
		if (qFunc.equals("GR2")) {
			return heauristicDistance(s);
		}
		if (qFunc.equals("AS1")) {
			return heauristicStones(s) + getDamage(((EndGameState) s).state);
		}
		if (qFunc.equals("AS2")) {
			return heauristicDistance(s) + getDamage(((EndGameState) s).state);
		}
		if (qFunc.equals("UC")) {

			return getDamage(((EndGameState) s).state);

		}

		return getDamage(((EndGameState) s).state);
	}

	@Override
	public boolean goalTest(State n) {

		String s = ((EndGameState) n).state;

		int damage = getDamage(s);

		HashSet<Point> stones = getStones(s);

		if (damage < 100) {

			if (stones.size() == 0) {

				if (((EndGameState) n).snap) {

					return true;

				}
			}
		}

		return false;

	}

	@Override
	public State transitionFun(Operator operator, State oldState) {

		EndGameState oldEndGameState = (EndGameState) oldState;

		switch (operator) {
		case UP:
			return UP(oldEndGameState);
		case DOWN:
			return DOWN(oldEndGameState);
		case LEFT:
			return LEFT(oldEndGameState);
		case RIGHT:
			return RIGHT(oldEndGameState);
		case KILL:
			return KILL(oldEndGameState);
		case COLLECT:
			return COLLECT(oldEndGameState);
		case SNAP:
			return SNAP(oldEndGameState);
		default:
			break;
		}

		return oldState;
	}

	public EndGameState UP(EndGameState oldState) {

		String s = oldState.state;
		int damage = getDamage(s);
		HashSet<Point> stones = getStones(s);
		Point ironman = getIronman(s);
		Point thanos = getThanos(s);
		HashSet<Point> warriors = getWarriors(s);
		Point newIronman = new Point(ironman.x - 1, ironman.y);

		if (ironman.x - 1 < 0)
			return oldState;

		else {

			if (((damage > 100) || !(stones.size() == 0))
					&& thanos.x == ironman.x - 1 && thanos.y == ironman.y)
				return oldState;

			if (warriors.contains(newIronman)) {
				return oldState;
			}

		}

		int dam = checkDamage(newIronman, thanos, warriors) + damage;

		String newState = setDamage(dam, s);
		newState = setIronman(newState, newIronman);
		// System.out.println("New State  "+newState);
		if (dam < 100)
			return (new EndGameState(newState));
		else
			return oldState;
	}

	public EndGameState DOWN(EndGameState oldState) {
		Point ironman = getIronman(oldState.state);
		int damage = getDamage(oldState.state);
		Point gridDimensions = getDimensions(oldState.state);
		Point thanos = getThanos(oldState.state);
		HashSet<Point> warriors = getWarriors(oldState.state);
		HashSet<Point> stones = getStones(oldState.state);
		Point newIronman = new Point(ironman.x + 1, ironman.y);

		if (ironman.x + 1 >= gridDimensions.x)
			return oldState;

		else {

			if (((damage > 100) || !(stones.size() == 0))
					&& thanos.x == ironman.x + 1 && thanos.y == ironman.y)
				return oldState;
			if (warriors.contains(newIronman)) {
				return oldState;
			}

		}

		int dam = checkDamage(newIronman, thanos, warriors) + damage;

		String newState = setDamage(dam, oldState.state);
		newState = setIronman(newState, newIronman);
		if (dam < 100)
			return (new EndGameState(newState));
		else
			return oldState;

	}

	public EndGameState LEFT(EndGameState oldState) {
		Point ironman = getIronman(oldState.state);
		int damage = getDamage(oldState.state);
		Point thanos = getThanos(oldState.state);
		HashSet<Point> warriors = getWarriors(oldState.state);
		HashSet<Point> stones = getStones(oldState.state);
		Point newIronman = new Point(ironman.x, ironman.y - 1);

		if (ironman.y - 1 < 0)
			return oldState;

		else {

			if (((damage > 100) || !(stones.size() == 0))
					&& thanos.y == ironman.y - 1 && thanos.x == ironman.x)
				return oldState;
			if (warriors.contains(newIronman)) {
				return oldState;
			}

		}

		int dam = checkDamage(newIronman, thanos, warriors) + damage;

		String newState = setDamage(dam, oldState.state);
		newState = setIronman(newState, newIronman);

		if (dam < 100)
			return (new EndGameState(newState));

		else
			return oldState;

	}

	public EndGameState RIGHT(EndGameState oldState) {
		Point ironman = getIronman(oldState.state);
		int damage = getDamage(oldState.state);
		Point thanos = getThanos(oldState.state);
		Point gridDimensions = getDimensions(oldState.state);
		HashSet<Point> warriors = getWarriors(oldState.state);
		HashSet<Point> stones = getStones(oldState.state);
		Point newIronman = new Point(ironman.x, ironman.y + 1);

		if (ironman.y + 1 >= gridDimensions.y)
			return oldState;

		else {

			if (((damage > 100) || !(stones.size() == 0))
					&& thanos.y == ironman.y + 1 && thanos.x == ironman.x)
				return oldState;
			if (warriors.contains(newIronman)) {
				return oldState;
			}

		}

		int dam = checkDamage(newIronman, thanos, warriors) + damage;

		String newState = setDamage(dam, oldState.state);
		newState = setIronman(newState, newIronman);
		if (dam < 100) {
			return (new EndGameState(newState));
		} else
			return oldState;

	}

	public EndGameState COLLECT(EndGameState oldState) {
		HashSet<Point> stones = getStones(oldState.state);
		Point ironman = getIronman(oldState.state);
		Point thanos = getThanos(oldState.state);
		HashSet<Point> warriors = getWarriors(oldState.state);
		int damage = getDamage(oldState.state);

		int dam = checkDamage(ironman, thanos, warriors);
		int removeStone = -1;

		if (stones.contains(ironman)) {
			dam += 3;
			removeStone = 1;
			stones.remove(ironman);
		}

		if (removeStone > 0 && (dam + damage) < 100) {
			String newState = setDamage((dam + damage), oldState.state);
			newState = setStones(newState, stones);

			return (new EndGameState(newState));

		}

		else
			return oldState;
	}

	public EndGameState KILL(EndGameState oldState)

	{

		Point ironman = getIronman(oldState.state);
		Point thanos = getThanos(oldState.state);
		HashSet<Point> warriors = getWarriors(oldState.state);
		int initSize = warriors.size();
		Point p1 = new Point(ironman.x, ironman.y - 1);
		Point p2 = new Point(ironman.x, ironman.y + 1);
		Point p3 = new Point(ironman.x + 1, ironman.y);
		Point p4 = new Point(ironman.x - 1, ironman.y);

		int damage = getDamage(oldState.state);
		if (warriors.size() > 0) {

			if (warriors.contains(p1)) {
				damage += 2;
				warriors.remove(p1);
			}
			if (warriors.contains(p2)) {
				damage += 2;
				warriors.remove(p2);
			}
			if (warriors.contains(p3)) {
				damage += 2;
				warriors.remove(p3);
			}
			if (warriors.contains(p4)) {
				damage += 2;
				warriors.remove(p4);
			}

		}
		if (thanos.x == ironman.x) {
			if (thanos.y == ironman.y - 1 || thanos.y == ironman.y + 1)
				damage += 5;
		}

		if (thanos.y == ironman.y
				&& (thanos.x == ironman.x - 1 || thanos.x == ironman.x + 1))
			damage += 5;

		if (initSize == warriors.size()) {
			return oldState;
		}

		String newState = setDamage(damage, oldState.state);
		newState = setWarriors(newState, warriors);
		if (damage < 100)
			return (new EndGameState(newState));
		else
			return oldState;
	}

	public EndGameState SNAP(EndGameState oldState) {
		String s = oldState.state;
		Point ironman = getIronman(s);
		Point thanos = getThanos(s);
		HashSet<Point> stones = getStones(s);

		int damage = getDamage(s);
		if (stones.size() == 0 && ironman.x == thanos.x
				&& ironman.y == thanos.y && damage < 100) {

			return (new EndGameState(oldState.state + ";", true));

		}

		else
			return oldState;

	}

	public int checkDamage(Point newIronman, Point thanos,
			HashSet<Point> warriors)

	{
		int damage = 0;
		Point p1 = new Point(newIronman.x, newIronman.y - 1);
		Point p2 = new Point(newIronman.x, newIronman.y + 1);
		Point p3 = new Point(newIronman.x - 1, newIronman.y);
		Point p4 = new Point(newIronman.x + 1, newIronman.y);

		// Check if iron man adajcent to warrior receive damage

		if (warriors.contains(p1))
			damage += 1;
		if (warriors.contains(p2)) {
			damage += 1;

		}
		if (warriors.contains(p3)) {
			damage += 1;
		}

		if (warriors.contains(p4))
			damage += 1;

		// Check if Thanos's location adjacent to Iron-man new location; receive
		// damage
		if (thanos.x == newIronman.x) {
			if (thanos.y == newIronman.y - 1 || thanos.y == newIronman.y + 1)
				damage += 5;
		}

		if (thanos.y == newIronman.y
				&& (thanos.x == newIronman.x - 1 || thanos.x == newIronman.x + 1))
			damage += 5;
		return damage;
	}

	public int getDamage(String state) {
		StringTokenizer st = new StringTokenizer(state, ";");
		return Integer.parseInt(st.nextToken());
	}

	public String setDamage(int damage, String state) {
		StringTokenizer st = new StringTokenizer(state, ";");
		st.nextToken();
		String newState = damage + "";
		while (st.hasMoreElements()) {
			newState += ";" + st.nextToken();
		}

		return newState;

	}

	// Get Grid Dimensions
	public Point getDimensions(String state) {
		StringTokenizer st = new StringTokenizer(state, ";");
		st.nextToken();
		StringTokenizer sst = new StringTokenizer(st.nextToken(), ",");
		int x = Integer.parseInt(sst.nextToken());
		int y = Integer.parseInt(sst.nextToken());
		return new Point(x, y);
	}

	public Point getIronman(String state) {

		StringTokenizer st = new StringTokenizer(state, ";");
		st.nextToken();
		st.nextToken();
		StringTokenizer sst = new StringTokenizer(st.nextToken(), ",");
		int x = Integer.parseInt(sst.nextToken());
		int y = Integer.parseInt(sst.nextToken());
		return new Point(x, y);

	}

	public String setIronman(String state, Point im) {
		StringTokenizer st = new StringTokenizer(state, ";");
		String newState = st.nextToken() + ";" + st.nextToken() + ";";
		st.nextToken();
		newState += im.x + "," + im.y;
		while (st.hasMoreElements()) {
			newState += ";" + st.nextToken();
		}
		return newState;

	}

	public Point getThanos(String state) {
		StringTokenizer st = new StringTokenizer(state, ";");
		// System.out.println("State sent to getThanos "+ state);
		st.nextToken();// damage
		st.nextToken();// gridDime
		st.nextToken();// ironman
		StringTokenizer sst = new StringTokenizer(st.nextToken(), ",");
		String next = sst.nextToken();
		if (!next.equals("s") && !next.equals("w")) {
			int x = Integer.parseInt(next);
			int y = Integer.parseInt(sst.nextToken());
			return new Point(x, y);
		} else
			return null;
	}

	public HashSet<Point> getStones(String state) {
		StringTokenizer st = new StringTokenizer(state, ";");
		HashSet<Point> stones = new HashSet<Point>();
		st.nextToken();// damage
		st.nextToken();// dimensions
		st.nextToken();// ironman
		st.nextToken();// thanos
		if (!st.hasMoreElements()) {
			return stones;
		}
		String stoneString = st.nextToken();

		StringTokenizer sst = new StringTokenizer(stoneString, ",");
		String b = sst.nextToken();
		if (!b.equals("s")) {
			return stones;
		}
		while (sst.hasMoreTokens()) {
			int x = Integer.parseInt(sst.nextToken());
			int y = Integer.parseInt(sst.nextToken());
			Point p = new Point(x, y);
			stones.add(p);
		}

		return stones;
	}

	// the array list is ready i just transform it to a string
	public String setStones(String state, HashSet<Point> stones) {
		Iterator<Point> is = stones.iterator();

		String stoneString = "s";
		while (is.hasNext()) {
			Point p = is.next();
			stoneString += "," + p.x + "," + p.y;
		}
		if (stoneString.equals("s")) {
			stoneString = "";
		}

		StringTokenizer st = new StringTokenizer(state, ";");
		String damage = st.nextToken();
		String grid = st.nextToken();
		String ironman = st.nextToken();
		String thanos = st.nextToken();
		if (!st.hasMoreElements() && stones.size() == 0) {
			return state;
		}
		st.nextToken();
		String restofstate = "";

		if (st.hasMoreTokens())
			restofstate += st.nextToken();

		if (!restofstate.equals("")) {
			if (!stoneString.isEmpty())
				state = damage + ";" + grid + ";" + ironman + ";" + thanos
						+ ";" + stoneString + ";" + restofstate;
			else
				state = damage + ";" + grid + ";" + ironman + ";" + thanos
						+ ";" + restofstate;
		}

		else {
			if (!stoneString.isEmpty())
				state = damage + ";" + grid + ";" + ironman + ";" + thanos
						+ ";" + stoneString;
			else {
				state = damage + ";" + grid + ";" + ironman + ";" + thanos;
			}
		}
		return state;
	}

	public HashSet<Point> getWarriors(String state) {
		String warriorString = "";
		StringTokenizer st = new StringTokenizer(state, ";");
		st.nextToken();// damage
		st.nextToken();// dimensions
		st.nextToken();// ironman
		st.nextToken();// thanos
		if (st.hasMoreElements()) {
			String s = st.nextToken();// stones
			StringTokenizer sst = new StringTokenizer(s, ",");
			String b = sst.nextToken();
			if (b.equals("w")) {
				warriorString = s;
			}
		}
		if (st.hasMoreElements()) {
			warriorString = st.nextToken();
		}

		HashSet<Point> warriors = new HashSet<Point>();
		if (warriorString != "") {
			StringTokenizer sst = new StringTokenizer(warriorString, ",");

			String b = sst.nextToken();
			if (!b.equals("w")) {
				return warriors;
			}
			while (sst.hasMoreTokens()) {
				int x = Integer.parseInt(sst.nextToken());
				int y = Integer.parseInt(sst.nextToken());
				Point p = new Point(x, y);
				warriors.add(p);
			}

		}

		return warriors;
	}

	public String setWarriors(String state, HashSet<Point> warriors) {
		String warriorsString = "w";
		Iterator<Point> iw = warriors.iterator();

		for (int i = 0; i < warriors.size(); i++) {
			Point p = iw.next();
			;
			warriorsString += "," + p.x + "," + p.y;
		}
		if (warriorsString.equals("w")) {
			warriorsString = "";
		}

		StringTokenizer st = new StringTokenizer(state, ";");
		String damage = st.nextToken();
		String grid = st.nextToken();
		String ironman = st.nextToken();
		String thanos = st.nextToken();
		String stones = st.nextToken();
		StringTokenizer sst = new StringTokenizer(stones, ",");
		String b = sst.nextToken();
		if (b.equals("s") && st.hasMoreElements()) {
			st.nextToken();
		}

		if (b.equals("s"))
			state = damage + ";" + grid + ";" + ironman + ";" + thanos + ";"
					+ stones + ";" + warriorsString;

		else
			state = damage + ";" + grid + ";" + ironman + ";" + thanos + ";"
					+ warriorsString;

		return state;
	}

	// Heuristic Function used for GR1 and AS1
	public int heauristicStones(State s) {

		EndGameState es = (EndGameState) s;
		HashSet<Point> stones = getStones(es.state);
		int damage = getDamage(es.state);

		if (es.snap && damage < 100) {
			return 0;
		}
		if (stones.size() == 0) {
			return 1;
		}
		if (stones.size() > 0) {
			return (3 * stones.size());
		}
		return 0;
	}

	// Heuristic Function used for GR2 and AS2
	public int heauristicDistance(State s) {
		EndGameState es = (EndGameState) s;
		HashSet<Point> stones = getStones(es.state);
		Point ironman = getIronman(es.state);
		int minDist = Integer.MAX_VALUE;
		int damage = getDamage(es.state);
		Iterator<Point> is = stones.iterator();

		if (es.snap && damage < 100) {
			return 0;
		}
		if (stones.size() == 0) {
			return 1;
		}
		for (int i = 0; i < stones.size(); i++) {

			Point stone = is.next();
			int manDist = Math.abs(stone.x - ironman.x)
					+ Math.abs(stone.y - ironman.y) / 2;
			if (manDist < minDist)
				minDist = manDist;

		}
		if (minDist == 0)
			return 1;
		return minDist;
	}

	public void printGrid(String grid) {
		Point ironman = getIronman(grid);
		Point thanos = getThanos(grid);
		HashSet<Point> stones = getStones(grid);
		HashSet<Point> warriors = getWarriors(grid);
		Point dim = getDimensions(grid);
		String r = "";
		for (int i = 0; i < dim.x; i++) {
			for (int j = 0; j < dim.y; j++) {

				Point p = new Point(i, j);
				if (i == ironman.x && j == ironman.y) {
					r += " I ";
				}

				else {
					if (i == thanos.x && j == thanos.y) {

						r += " T ";

					} else {
						if (stones.contains(p)) {
							r += " S ";
						} else {
							if (warriors.contains(p)) {
								r += " W ";
							} else {
								r += " . ";
							}

						}
					}

				}

			}
			r += "\n";
		}

		System.out.print(r);

	}

}
