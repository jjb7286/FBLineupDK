package application;

public class DST extends Player {
	
	// boolean to disable teams with players facing the selected defense
	public static boolean noDstConflict;
	
	// static int for num of dst locked, max 2
	public static int dstLockNum;
	
	// private boolean for excluding player from lineups
	private boolean dstAvail;
		
	// private int for maximum exposure
	private int dstMaxTeams;
	
	// no arg constructor
	public DST() {
		super();
		this.setDstAvail(true);
		this.setDstMaxTeams(150);
	}

	public boolean isDstAvail() {
		return dstAvail;
	}

	public void setDstAvail(boolean dstAvail) {
		this.dstAvail = dstAvail;
	}

	public int getDstMaxTeams() {
		return dstMaxTeams;
	}

	public void setDstMaxTeams(int dstMaxTeams) {
		this.dstMaxTeams = dstMaxTeams;
	}

}
