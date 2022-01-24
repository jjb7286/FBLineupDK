package application;

import java.util.ArrayList;

public class Team {
	
	// team info 	
	private String market; 
	private String name;
	private String abrevName;
	
	// stadium zipCode and indoor/outdoor for weather lookup
	String zipCode;
	String city;
	String surface;
	boolean indoor;
	
	// array list for NFL teams
	public static ArrayList<Team> nflTeams = nflTeams();
	
	public Team () {
		this.market = null;
		this.name = null;
		this.abrevName = null;
		this.zipCode = null;
		this.indoor = false;
		
	}
	
	public Team (String market, String name, String abrevName, String zipCode, String city, String surface, boolean indoor) {
		this.market = market;
		this.name = name;
		this.abrevName = abrevName;
		this.zipCode = zipCode;
		this.city = city;
		this.surface = surface;
		this.indoor = indoor;
	}
	
	// method to return list of all 32 NFL teams
	public static ArrayList<Team> nflTeams(){
		ArrayList<Team> nflTeams = new ArrayList<Team>();
		nflTeams.add(new Team ("Las Vegas", "Raiders", "LV", "89014", "Paradise, NV", "Grass", true));
		nflTeams.add(new Team ("Kansas City", "Chiefs", "KC", "64030", "Kansas City, MO", "Grass", false));
		nflTeams.add(new Team ("Dallas", "Cowboys", "DAL", "76001", "Arlington, TX", "Turf", true));
		nflTeams.add(new Team ("Carolina", "Panthers", "CAR", "28105", "Charlotte, NC", "Turf", false));
		nflTeams.add(new Team ("New Orleans", "Saints", "NO", "70032", "New Orleans, LA", "Turf", true));
		nflTeams.add(new Team ("Denvers", "Broncos", "DEN", "80014", "Denver, CO", "Grass", false));
		nflTeams.add(new Team ("Washington", "Football Team", "WAS", "20784", "Landover, MD", "Grass", false));
		nflTeams.add(new Team ("Cleveland", "Browns", "CLE", "41101", "Cleveland, OH", "Grass", false));
		nflTeams.add(new Team ("Detroit", "Lions", "DET", "48127", "Detroit, MI", "Turf", true));
		nflTeams.add(new Team ("New England", "Patriots", "NE", "02035", "Foxborough, MA", "Turf", false));
		nflTeams.add(new Team ("Miami", "Dolphins", "MIA", "33014", "Miami Gardens, FL", "Grass", false));
		nflTeams.add(new Team ("Pittsburgh", "Steelers", "PIT", "15106", "Pittsburgh, PA", "Grass", false));
		nflTeams.add(new Team ("Buffalo", "Bills", "BUF", "14127", "Orchard Park, NY", "Turf", false));
		nflTeams.add(new Team ("Green Bay", "Packers", "GB", "54229", "Green Bay, WI", "Hybrid", false));
		nflTeams.add(new Team ("San Francisco", "49ers", "SF", "95050", "Santa Clara, CA", "Grass", false));
		nflTeams.add(new Team ("Philadelphia", "Eagles", "PHI", "19019", "Philadelphia, PA", "Hybrid", false));
		nflTeams.add(new Team ("Indianapolis", "Colts", "IND", "46077", "Indianapolis, IN", "Turf", true));
		nflTeams.add(new Team ("Seattle", "Seahawks", "SEA", "98101", "Seattle, WA", "Turf", false));
		nflTeams.add(new Team ("Baltimore", "Ravens", "BAL", "21201", "Baltimore, MD", "Grass", false));
		nflTeams.add(new Team ("Atlanta", "Falcons", "ATL", "30301", "Atlanta, GA", "Turf", true));
		nflTeams.add(new Team ("New York", "Giants", "NYG", "07071", "East Rutherford, NY", "Turf", false));
		nflTeams.add(new Team ("New York", "Jets", "NYJ", "07071", "East Rutherford, NY", "Turf", false));
		nflTeams.add(new Team ("Tennessee", "Titans", "TEN", "37011", "Nashville, TN", "Grass", false));
		nflTeams.add(new Team ("Houston", "Texans", "HOU", "77001", "Houston, TX", "Turf", true));
		nflTeams.add(new Team ("Cincinnati", "Bengals", "CIN", "41073", "Cincinnati, OH", "Turf", false));
		nflTeams.add(new Team ("Tampa Bay", "Buccaneers", "TB", "33601", "Tampa, FL", "Grass", false));
		nflTeams.add(new Team ("Los Angeles", "Rams", "LAR", "90301", "Inglewood, CA", "Turf", true));
		nflTeams.add(new Team ("Los Angeles", "Chargers", "LAC", "90301", "Inglewood, CA", "Turf", true));
		nflTeams.add(new Team ("Chicago", "Bears", "CHI", "60007", "Chicago, IL", "Grass", false));
		nflTeams.add(new Team ("Arizona", "Cardinals", "ARI", "85031", "Glendale, AZ", "Grass", true));
		nflTeams.add(new Team ("Jacksonville", "Jaguars", "JAX/JAC", "32034", "Jacksonville, FL", "Grass", false));
		nflTeams.add(new Team ("Minnesota", "Vikings", "MIN", "55428", "Minneapolis, MN", "Turf", true));
		
		return nflTeams;
	}
	
	// method to return nfl team object by nfl team abbreviation
	public static Team getNflTeam(String abrev) {
		int i = 0;
		while (i < nflTeams.size()) {
			if (nflTeams.get(i).getAbrevName().contains(abrev)) {
				break;
			}
			i++;
		}
		if (i >= nflTeams.size()) {
			return null;
		} else {
			return nflTeams.get(i);
		}
				
	}
	
	//getters & setters 
	// private market
	public String getMarket() {
		return market;
	}
	
	public void setMarket(String market) {
		this.market = market;
	}
	//private name
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	// private abrevName
	public String getAbrevName() {
		return abrevName;
	}
	
	public void setAbrevName(String abrevName) {
		this.abrevName = abrevName;
	}
	// private zipcode
	public String getZipCode() {
		return zipCode;
	}
	
	public void String(String zipCode) {
		this.zipCode = zipCode;
	}
	// private indoor
	public boolean getIndoor() {
		return indoor;
	}
	
	public void setIndoor(boolean indoor) {
		this.indoor = indoor;
	}
	
	@Override
	public String toString() {
		return abrevName;
	}
	
	
	

}
