package application;


public class RB extends Offense {
	
	// static boolean for including rbs in flex list
	public static boolean rbFlex; 
	
	// static boolean for including rbs in qb stacks
	public static boolean rbStack;
	
	// static int for number of not stacked rbs locked, max 2
	public static int rbLockNum;
	
	// private boolean for excluding player from lineups
	private boolean rbAvail;
	
	// private int for maximum exposure
	private int rbMaxTeams;
	
	public RB() {
		super();
		this.setRbAvail(true);
		this.setRbMaxTeams(150);
		
	}
	
	
	// initialize the static variables
	static {
		rbFlex = true;
		rbStack = true;
	}


	public boolean isRbAvail() {
		return rbAvail;
	}


	public void setRbAvail(boolean rbAvail) {
		this.rbAvail = rbAvail;
	}


	public int getRbMaxTeams() {
		return rbMaxTeams;
	}


	public void setRbMaxTeams(int rbMaxTeams) {
		this.rbMaxTeams = rbMaxTeams;
	}
	

}
