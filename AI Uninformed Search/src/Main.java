import java.awt.Point;
import java.io.IOException;
import java.util.HashSet;
import java.util.StringTokenizer;

public class Main {

	public static String solve(String grid, String strategy, boolean visualize) {
		Operator[] operators = { Operator.SNAP, Operator.COLLECT, Operator.UP,
				Operator.DOWN, Operator.LEFT, Operator.RIGHT, Operator.KILL };

		StringTokenizer st = new StringTokenizer(grid, ";");
		String gridDime = st.nextToken();
		String ironman = st.nextToken();
		String thanos = st.nextToken();
		String stones = st.nextToken();
		String warriors = st.nextToken();
		String g = gridDime + ";" + ironman + ";" + thanos + ";" + "s,"
				+ stones + ";" + "w," + warriors;

		EndGameState initState = new EndGameState("0;" + g);
		End_Game endGame = new End_Game(initState, operators);

		if (strategy.equals("ID")) {
			return endGame.ID(endGame);
		}
		StringTokenizer st2 = new StringTokenizer(gridDime, ",");
		int m = Integer.parseInt(st2.nextToken());
		int n = Integer.parseInt(st2.nextToken());
		StringTokenizer st3 = new StringTokenizer(ironman, ",");
		int ix = Integer.parseInt(st3.nextToken());
		int iy = Integer.parseInt(st3.nextToken());
		StringTokenizer sthanos = new StringTokenizer(thanos, ",");
		int tx = Integer.parseInt(sthanos.nextToken());
		int ty = Integer.parseInt(sthanos.nextToken());
		HashSet<Point> hstones = new HashSet<Point>();
		StringTokenizer ss = new StringTokenizer(stones, ",");
		while (ss.hasMoreTokens()) {
			int stx = Integer.parseInt(ss.nextToken());
			int sty = Integer.parseInt(ss.nextToken());
			hstones.add(new Point(stx, sty));
		}
		HashSet<Point> hwarriors = new HashSet<Point>();
		StringTokenizer sw = new StringTokenizer(warriors, ",");
		while (sw.hasMoreTokens()) {
			int wx = Integer.parseInt(sw.nextToken());
			int wy = Integer.parseInt(sw.nextToken());
			hwarriors.add(new Point(wx, wy));
		}

		if (visualize) {
			for (int i = 0; i < m; i++) {
				for (int j = 0; j < n; j++) {
					Point p = new Point(i, j);
					if (i == ix && j == iy) {
						System.out.print(" I ");
					}

					else {
						if (i == tx && j == ty) {

							System.out.print(" T ");

						} else {
							if (hstones.contains(p)) {
								System.out.print(" S ");
							} else {
								if (hwarriors.contains(p)) {
									System.out.print(" W ");
								} else {
									System.out.print(" . ");
								}

							}
						}
					}
				}
				System.out.println(" ");
			}
			System.out
					.println("------------------------- INITIAL -------------------------------------");

		}
		Problem.setVis(visualize);
		return endGame.General_Search(endGame, strategy);

	}

	public static void main(String[] args) throws IOException {
		/*
		 * BufferedReader br = new BufferedReader(new
		 * InputStreamReader(System.in));
		 * 
		 * System.out.println("Enter Grid:"); String grid = br.readLine();
		 * System.out.println("Enter Strategy:"); String strategy =
		 * br.readLine(); System.out.println("Visualize?"); boolean
		 * visualization = new Boolean(br.readLine());
		 */

		// String g5= "5,5;2,2;4,2;4,0,1,2,3,0,2,1,4,1,2,4;3,2,0,0,3,4,4,3,4,4";
		String grid1 = "15,15;12,13;5,7;7,0,9,14,14,8,5,8,8,9,8,4;6,6,4,3,10,2,7,4,3,11";
		String strategy = "GR2";
		boolean visualization = false;

		solve(grid1, strategy, visualization);

	}

}
