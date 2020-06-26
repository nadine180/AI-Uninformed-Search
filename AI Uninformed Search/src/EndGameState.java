public class EndGameState extends State {

	String state;
	boolean snap;

	public EndGameState(String state) {
		super(state);
		this.state = state;
		this.snap = false;
	}

	public EndGameState(String state, boolean snap) {
		super(state);
		this.state = state;
		this.snap = snap;
	}

}
