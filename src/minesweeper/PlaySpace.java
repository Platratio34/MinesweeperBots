package minesweeper;

public class PlaySpace {
	private int number;
	private boolean boomb;
	private PlaySpaceState state;
	
	public PlaySpace() {
		number = -1;
		boomb = false;
		state = PlaySpaceState.HIDDEN;
	}
	
	public void setNumber(int set) {
		number=set;
	}
	public void setBoomb(boolean set) {
		boomb = set;
	}
	public void setState(PlaySpaceState set) {
		state = set;
	}
	public int getNumber() {
		return number;
	}
	public boolean isBoomb() {
		return boomb;
	}
	public PlaySpaceState getState() {
		return state;
	}
	public boolean isFlagged() {
		if(state==PlaySpaceState.FLAGGED) {
			return true;
		} else
			return false;
	}
}
