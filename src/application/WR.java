package application;

public class WR extends Offense {
	
	// static boolean for including wrs in flex list
	public static boolean wrFlex; 
	
	// static boolean for including wrs in qb stacks
	public static boolean wrStack;
	
	// static int for number of not stacked wrs locked, max 2
	public static int wrLockNum;
	
	// private boolean for excluding player from lineups
	private boolean wrAvail;
	
	// private int for maximum exposure
	private int wrMaxTeams;
	
	public WR() {
		super();
		this.setWrAvail(true);
		this.setWrMaxTeams(150);
		
	}
	
	
	// initialize the static variables
	static {
		wrFlex = true;
		wrStack = true;
	}


	public boolean isWrAvail() {
		return wrAvail;
	}


	public void setWrAvail(boolean wrAvail) {
		this.wrAvail = wrAvail;
	}


	public int getWrMaxTeams() {
		return wrMaxTeams;
	}


	public void setWrMaxTeams(int wrMaxTeams) {
		this.wrMaxTeams = wrMaxTeams;
	}

}
