package application;

public class TE extends Offense {
	
	// static boolean for including tes in flex list
	public static boolean teFlex; 
	
	// static boolean for including tes in qb stacks
	public static boolean teStack;
	
	// static int for number of not stacked tes locked, max 1
	public static int teLockNum;
	
	// private boolean for excluding player from lineups
	private boolean teAvail;
	
	// private int for maximum exposure
	private int teMaxTeams;
	
	public TE() {
		super();
		this.setTeAvail(true);
		this.setTeMaxTeams(150);
		
	}
	
	
	// initialize the static variables
	static {
		teFlex = true;
		teStack = true;
	}


	public boolean isTeAvail() {
		return teAvail;
	}


	public void setTeAvail(boolean teAvail) {
		this.teAvail = teAvail;
	}


	public int getTeMaxTeams() {
		return teMaxTeams;
	}


	public void setTeMaxTeams(int teMaxTeams) {
		this.teMaxTeams = teMaxTeams;
	}

}
