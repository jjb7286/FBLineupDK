package application;

public abstract class Player implements Comparable<Player> {
	
	// player name string
	private String name;
	
	// player draftkings id 
	private String id;
	
	// player position string
	private String pos;
	
	// player position id
	private int posID;
	
	// estimated start % string
	private String estStart;
	
	// over/under string
	private String overUnder;
	
	// projection double 
	private double projection;
	
	// custom adjusted projection
	private double custom;
	
	// salary integer
	private int salary;
	
	// team player is on
	private Team playerTeam;
	
	// double cost per point
	private double cpp;
	
	// team opponent is on
	private Team oppTeam;
	
	// matchup info matchup object
	private Matchup matchup;
	
	// matchup ranking out of 10 String
	private String matchupRating;
	
	// matchup ranking letter grade
	private String matchupLetter;
	
	// average performance vs projection String
	private String avgPerformance;
	
	// historical performance above projection String
	private String histPerformance;
	
	// string for locked %
	private String lockedPerc;
	
	// int to keep track of how many teams player is on
	private int numLineups;
	

	
	// no arg player constructor 
	
	public Player () {
		this.setName("");
		this.setId("");
		this.setPos("");
		this.setPosID(0);
		this.setEstStart("");
		this.setOverUnder("");
		this.setProjection(0.0);
		this.setSalary(0);
		this.setPlayerTeam(null);
		this.setOppTeam(null);
		this.setMatchup(null);
		this.setMatchupRating("");
		this.setMatchupLetter("");
		this.setAvgPerformance("");
		this.setHistPerformance("");
		this.setLockedPerc("");
		this.setNumLineups(0);

	}
	
	@Override
	public String toString() {
		return this.name;
	}
	
	@Override
	public int compareTo(Player comparePlayer) {
		if (comparePlayer.custom > this.custom) {
			return -1;
		} else if (comparePlayer.custom < this.custom) {
			return 1;
		} else {
			return 1;
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPos() {
		return pos;
	}

	public void setPos(String pos) {
		this.pos = pos;
	}

	public String getEstStart() {
		return estStart;
	}

	public void setEstStart(String estStart) {
		this.estStart = estStart;
	}


	public String getOverUnder() {
		return overUnder;
	}

	public void setOverUnder(String overUnder) {
		this.overUnder = overUnder;
	}
	
	public double getProjection() {
		return projection;
	}
	
	public void setProjection(double projection) {
		this.projection = projection;
		this.custom = projection;
		this.cpp = this.salary/custom;
	}
		
	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}
	
	public Team getPlayerTeam() {
		return playerTeam;
	}
	
	public void setPlayerTeam(Team playerTeam) {
		this.playerTeam = playerTeam;
	}

	public Team getOppTeam() {
		return oppTeam;
	}

	public void setOppTeam(Team oppTeam) {
		this.oppTeam = oppTeam;
	}



	public Matchup getMatchup() {
		return matchup;
	}



	public void setMatchup(Matchup matchup) {
		this.matchup = matchup;
	}



	public int getPosID() {
		return posID;
	}



	public void setPosID(int posID) {
		this.posID = posID;
	}

	public String getMatchupRating() {
		return matchupRating;
	}

	public void setMatchupRating(String matchupRating) {
		this.matchupRating = matchupRating;
	}

	public String getMatchupLetter() {
		return matchupLetter;
	}

	public void setMatchupLetter(String matchupLetter) {
		this.matchupLetter = matchupLetter;
	}

	public String getAvgPerformance() {
		return avgPerformance;
	}

	public void setAvgPerformance(String avgPerformance) {
		this.avgPerformance = avgPerformance;
	}

	public String getHistPerformance() {
		return histPerformance;
	}

	public void setHistPerformance(String histPerformance) {
		this.histPerformance = histPerformance;
	}

	public double getCustom() {
		return custom;
	}

	public void setCustom(double custom) {
		this.custom = custom;
		this.cpp = this.salary / custom;
	}

	public String getLockedPerc() {
		return lockedPerc;
	}

	public void setLockedPerc(String lockedPerc) {
		this.lockedPerc = lockedPerc;
	}

	public double getCpp() {
		return cpp;
	}

	public void setCpp(double cpp) {
		this.cpp = cpp;
	}

	public int getNumLineups() {
		return numLineups;
	}

	public void setNumLineups(int numLineups) {
		this.numLineups = numLineups;
	}



}
