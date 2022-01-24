package application;


public class Matchup {

	private Team homeTeam;
	private Team awayTeam;
	private String date;
	private String weather;
	private String ptSpread;
	private String overUnder;
	private String location;
	private String homeAdjust;
	private String awayAdjust;
	
	public Matchup() {
		setHomeTeam(null);
		setAwayTeam(null);
		setDate("");
		setWeather("");
		setPtSpread("");
		setOverUnder("");
		setLocation("");
		setHomeAdjust("");
		setAwayAdjust("");
	}
	
	public Matchup(Team homeTeam, Team awayTeam, String date) {
		this.setHomeTeam(homeTeam);
		this.setAwayTeam(awayTeam);
		this.setDate(date);
	}
	
	public Matchup(Team homeTeam, Team awayTeam, String date, String weather, String ptSpread, String oU) {
		this.setHomeTeam(homeTeam);
		this.setAwayTeam(awayTeam);
		this.setDate(date);
		this.setWeather(weather);
		this.setPtSpread(ptSpread);
		this.setOverUnder(oU);
		this.setLocation(homeTeam.getMarket());
		this.setHomeAdjust("");
		this.setAwayAdjust("");
	}
	
	// getters & setters

	public Team getHomeTeam() {
		return homeTeam;
	}

	public void setHomeTeam(Team homeTeam) {
		this.homeTeam = homeTeam;
		this.location = homeTeam.getMarket();
	}

	public Team getAwayTeam() {
		return awayTeam;
	}

	public void setAwayTeam(Team awayTeam) {
		this.awayTeam = awayTeam;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getWeather() {
		return weather;
	}

	public void setWeather(String weather) {
		this.weather = weather;
	}

	public String getPtSpread() {
		return ptSpread;
	}

	public void setPtSpread(String ptSpread) {
		this.ptSpread = ptSpread;
	}

	public String getOverUnder() {
		return overUnder;
	}

	public void setOverUnder(String overUnder) {
		this.overUnder = overUnder;
	}
	
	@Override
	public String toString() {
		return "Home: " + homeTeam.getAbrevName() + " vs. Away: " + awayTeam.getAbrevName(); 
	
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getHomeAdjust() {
		return homeAdjust;
	}

	public void setHomeAdjust(String homeAdjust) {
		this.homeAdjust = homeAdjust;
	}

	public String getAwayAdjust() {
		return awayAdjust;
	}

	public void setAwayAdjust(String awayAdjust) {
		this.awayAdjust = awayAdjust;
	}
	
	
}
