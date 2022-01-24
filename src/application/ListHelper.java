package application;

import java.util.ArrayList;
import java.util.Collections;

// class for populating player and matchup objects from 2d String arrays from csvs via FileHelper
public class ListHelper {

	// get 2d array containing data from DraftKings csv
	public static String[][] dkPlayers = FileHelper.storeCSV("football.csv");
	
	// get 2d array containing data by position from FantasyPros
	public static String[][] fpQbs = FileHelper.storeCSV("qbs.csv");
	public static String[][] fpRbs = FileHelper.storeCSV("rbs.csv");
	public static String[][] fpWrs = FileHelper.storeCSV("wrs.csv");
	public static String[][] fpTes = FileHelper.storeCSV("tes.csv");
	public static String[][] fpDsts = FileHelper.storeCSV("dsts.csv");
	
	// get 2d arrau containing projections by position from FantasyPros
	public static String[][] fpQbsR = FileHelper.storeCSV("qbsR.csv");
	public static String[][] fpRbsR = FileHelper.storeCSV("rbsR.csv");
	public static String[][] fpWrsR = FileHelper.storeCSV("wrsR.csv");
	public static String[][] fpTesR = FileHelper.storeCSV("tesR.csv");
	public static String[][] fpDstsR = FileHelper.storeCSV("dstsR.csv");
	
	// create ArrayList for each position
	public static ArrayList<QB> qbs = new ArrayList<QB>();
	public static ArrayList<RB> rbs = new ArrayList<RB>();
	public static ArrayList<WR> wrs = new ArrayList<WR>();
	public static ArrayList<TE> tes = new ArrayList<TE>();
	public static ArrayList<Offense> flexs = new ArrayList<Offense>();
	public static ArrayList<DST> dsts = new ArrayList<DST>();
	
	// create ArrayList to view stacks
	public static ArrayList<Offense> stacks = new ArrayList<Offense>();
	
	// create ArrayList for all offense positions
	public static ArrayList<Offense> allOffense = new ArrayList<Offense>();
	
	// create ArrayList for matchups
	public static ArrayList<Matchup> nflMatchups = new ArrayList<Matchup>();
	
	// create ArrayList for locked position players
	public static ArrayList<Player> lockedPlayers = new ArrayList<Player>();
	
	// method to populate stack list given QB
	public static void createStacks(QB qb) {
		stacks.clear();
		for (Offense offense : allOffense) {
			if (qb.getPlayerTeam().equals(offense.getPlayerTeam()) && !offense.getPos().equals("QB")) {
				stacks.add(offense);
			}
		}
		Collections.sort(stacks);
		Collections.reverse(stacks);
	}
	
	//method to create array lists for each position
	public static void createPosLists() {
		
		// custom position ids for team comparison
		int qbID = 1;
		int dstID = 1;
		int flexID = 1;
		
		
		// go through csv and create lists for each position
		for (int i = 8; i < dkPlayers.length; i++) {
			
			if (dkPlayers[i][21].equals("FA")) {
				continue;
			}

			switch(dkPlayers[i][14]) {
			case "QB":
				QB qb = new QB();
				qb.setPos("QB");
				qb.setName(shortName(dkPlayers[i][16]));
				qb.setId(dkPlayers[i][17]);
				qb.setPlayerTeam(Team.getNflTeam(dkPlayers[i][21]));
				qb.setOppTeam(Team.getNflTeam(getOpponent(dkPlayers[i][20], dkPlayers[i][21])));
				qb.setPosID(qbID);
				qb.setSalary(Integer.parseInt(dkPlayers[i][19]));
				qbID++;
				// get data from first set of fp sheets
				int j = getRow(qb.getName(), fpQbs);
				if (j >= 0) {
					qb.setProjection(Double.parseDouble(fpQbs[j][10]));
					qb.setOverUnder(fpQbs[j][5]);
					qb.setEstStart(fpQbs[j][13]);
					if (j + 1 < fpQbs.length && fpQbs[j+1][0] != null) {
						qb.setInjStatus(fpQbs[j+1][0]);
					}
				}

				
				
				// get data from second set of fp sheets
				int jj = getRow(qb.getName(), fpQbsR);
				if (jj >= 0) {
					qb.setMatchupRating(fpQbsR[jj][3]);
					qb.setMatchupLetter(fpQbsR[jj][4]);
					qb.setAvgPerformance(fpQbsR[jj][6]);
					qb.setHistPerformance(fpQbsR[jj][7]);
					qb.setRzOpp(fpQbsR[jj][8]);
					qb.setRzPerf(fpQbsR[jj][9]);
				}
				
				
				
				for (Matchup matchup : nflMatchups) {
					if (matchup.getHomeTeam().equals(qb.getPlayerTeam()) ||
							matchup.getAwayTeam().equals(qb.getPlayerTeam())){
						qb.setMatchup(matchup);
						break;
					}
				}
				
				qbs.add(qb);
				j = -1;
				jj = -1;   
				break;
				
			
			case "RB":
				RB rb = new RB();
				rb.setPos("RB");
				rb.setName(shortName(dkPlayers[i][16]));
				rb.setId(dkPlayers[i][17]);
				rb.setPlayerTeam(Team.getNflTeam(dkPlayers[i][21]));
				rb.setOppTeam(Team.getNflTeam(getOpponent(dkPlayers[i][20], dkPlayers[i][21])));
				rb.setPosID(qbID);
				rb.setSalary(Integer.parseInt(dkPlayers[i][19]));
				rb.setLockedPerc("0.0");
				rb.setPosID(flexID);
				flexID++;
				// get data from first set of fp sheets
				int k = getRow(rb.getName(), fpRbs);
				if (k >= 0) {
					rb.setProjection(Float.parseFloat(fpRbs[k][10]));
					rb.setOverUnder(fpRbs[k][5]);
					rb.setEstStart(fpRbs[k][13]);
					if (k + 1 < fpRbs.length && fpRbs[k+1][0] != null) {
						rb.setInjStatus(fpRbs[k+1][0]);
					}
				}
				
				// get data from second set of fp sheets
				int kk = getRow(rb.getName(), fpRbsR);
				if (kk >= 0) {
					rb.setMatchupRating(fpRbsR[kk][3]);
					rb.setMatchupLetter(fpRbsR[kk][4]);
					rb.setAvgPerformance(fpRbsR[kk][6]);
					rb.setHistPerformance(fpRbsR[kk][7]);
					rb.setRzOpp(fpRbsR[kk][8]);
					rb.setRzPerf(fpRbsR[kk][9]);
				}
				
				for (Matchup matchup : nflMatchups) {
					if (matchup.getHomeTeam().equals(rb.getPlayerTeam()) ||
							matchup.getAwayTeam().equals(rb.getPlayerTeam())){
						rb.setMatchup(matchup);
						break;
					}
				}
				
				rbs.add(rb);
				k = -1;
				kk = -1;  
				break;
				
			case "WR":
				WR wr = new WR();
				wr.setPos("WR");
				wr.setName(shortName(dkPlayers[i][16]));
				wr.setId(dkPlayers[i][17]);
				wr.setPlayerTeam(Team.getNflTeam(dkPlayers[i][21]));
				wr.setOppTeam(Team.getNflTeam(getOpponent(dkPlayers[i][20], dkPlayers[i][21])));
				wr.setPosID(qbID);
				wr.setSalary(Integer.parseInt(dkPlayers[i][19]));
				wr.setLockedPerc("0.0");
				wr.setPosID(flexID);
				flexID++;
				// get data from first set of fp sheets
				int l = getRow(wr.getName(), fpWrs);
				if (l >= 0) {
					wr.setProjection(Float.parseFloat(fpWrs[l][10]));
					wr.setOverUnder(fpWrs[l][5]);
					wr.setEstStart(fpWrs[l][13]);
					if (l + 1 < fpWrs.length && fpWrs[l+1][0] != null) {
						wr.setInjStatus(fpWrs[l+1][0]);
					}
				}
				
				
				// get data from second set of fp sheets
				int ll = getRow(wr.getName(), fpWrsR);
				if (ll >= 0) {
					wr.setMatchupRating(fpWrsR[ll][3]);
					wr.setMatchupLetter(fpWrsR[ll][4]);
					wr.setAvgPerformance(fpWrsR[ll][6]);
					wr.setHistPerformance(fpWrsR[ll][7]);
					wr.setRzOpp(fpWrsR[ll][8]);
					wr.setRzPerf(fpWrsR[ll][9]);
				}
				
				for (Matchup matchup : nflMatchups) {
					if (matchup.getHomeTeam().equals(wr.getPlayerTeam()) ||
							matchup.getAwayTeam().equals(wr.getPlayerTeam())){
						wr.setMatchup(matchup);
						break;
					}
				}
				
				wrs.add(wr);
				l = -1;
				ll = -1; 
				break;
				
				
			case "TE":
				TE te = new TE();
				te.setPos("TE");
				te.setName(shortName(dkPlayers[i][16]));
				te.setId(dkPlayers[i][17]);
				te.setPlayerTeam(Team.getNflTeam(dkPlayers[i][21]));
				te.setOppTeam(Team.getNflTeam(getOpponent(dkPlayers[i][20], dkPlayers[i][21])));
				te.setPosID(qbID);
				te.setSalary(Integer.parseInt(dkPlayers[i][19]));
				te.setLockedPerc("0.0");
				te.setPosID(flexID);
				flexID++;
				// get data from first set of fp sheets
				int m = getRow(te.getName(), fpTes);
				if (m >= 0) {
					te.setProjection(Float.parseFloat(fpTes[m][10]));
					te.setOverUnder(fpTes[m][5]);
					te.setEstStart(fpTes[m][13]);
				}
				if (m + 1 < fpTes.length && fpTes[m+1][0] != null) {
					te.setInjStatus(fpTes[m+1][0]);
				}
				
				// get data from second set of fp sheets
				int mm = getRow(te.getName(), fpTesR);
				if (mm >= 0) {
					te.setMatchupRating(fpTesR[mm][3]);
					te.setMatchupLetter(fpTesR[mm][4]);
					te.setAvgPerformance(fpTesR[mm][6]);
					te.setHistPerformance(fpTesR[mm][7]);
					te.setRzOpp(fpTesR[mm][8]);
					te.setRzPerf(fpTesR[mm][9]);
				}
				
				for (Matchup matchup : nflMatchups) {
					if (matchup.getHomeTeam().equals(te.getPlayerTeam()) ||
							matchup.getAwayTeam().equals(te.getPlayerTeam())){
						te.setMatchup(matchup);
						break;
					}
				}
				
				tes.add(te);
				m = -1;
				mm = -1; 
				break;
				
				
			case "DST":
				DST dst = new DST();
				dst.setPos("DST");
				dst.setName(shortName(dkPlayers[i][16]));
				dst.setId(dkPlayers[i][17]);
				dst.setPlayerTeam(Team.getNflTeam(dkPlayers[i][21]));
				dst.setOppTeam(Team.getNflTeam(getOpponent(dkPlayers[i][20], dkPlayers[i][21])));
				dst.setPosID(qbID);
				dst.setSalary(Integer.parseInt(dkPlayers[i][19]));
				dst.setLockedPerc("0.0");
				dst.setPosID(flexID);
				dst.setPosID(dstID);
				dstID++;
				// get data from first set of fp sheets
				int n = getRow(dst.getName(), fpDsts);
				
				if (n >= 0) {
					dst.setProjection(Float.parseFloat(fpDsts[n][10]));
					dst.setEstStart(fpDsts[n][13]);
					dst.setOverUnder(fpDsts[n][5]);
				}
				
				// get data from second set of fp sheets
				int nn = getRow(dst.getName(), fpDstsR);
				if (nn >= 0) {
					dst.setMatchupRating(fpDstsR[nn][3]);
					dst.setMatchupLetter(fpDstsR[nn][4]);
					dst.setAvgPerformance(fpDstsR[nn][6]);
					dst.setHistPerformance(fpDstsR[nn][7]);
					
				}
				
				for (Matchup matchup : nflMatchups) {
					if (matchup.getHomeTeam().equals(dst.getPlayerTeam()) ||
							matchup.getAwayTeam().equals(dst.getPlayerTeam())){
						dst.setMatchup(matchup);
						break;
					}
				}
				
				dsts.add(dst);
				n = -1;
				nn = -1;   
				break;		
			
			}		
		}
		
		// create all offense list
		allOffense.addAll(qbs);
		allOffense.addAll(rbs);
		allOffense.addAll(wrs);
		allOffense.addAll(tes);
		
		Collections.sort(qbs);
		Collections.reverse(qbs);
		
		Collections.sort(rbs);
		Collections.reverse(rbs);
		
		Collections.sort(wrs);
		Collections.reverse(wrs);
		
		Collections.sort(tes);
		Collections.reverse(tes);
		
		Collections.sort(dsts);
		Collections.reverse(dsts);
		
		Collections.sort(allOffense);
		Collections.reverse(allOffense);
		
	}
	// method to create matchup list
	
	
	public static void createMatchups() {
		
		for (int i = 0; i < fpQbs.length; i = i + 2) {
			int j = fpQbs[i][0].indexOf('(');
			int k = fpQbs[i][0].indexOf('-');
			String team = fpQbs[i][0].substring(j + 1, k - 1);
			Boolean atHome = true;
			String opp;
			Team homeTeam;
			Team awayTeam;
			if (fpQbs[i][1].contains("@")) {
				int l = fpQbs[i][1].indexOf('@');
				opp = fpQbs[i][1].substring(l + 1).trim();
				atHome = false;
				homeTeam = Team.getNflTeam(opp);
				awayTeam = Team.getNflTeam(team);
			} else {
				opp = fpQbs[i][1].trim();
				homeTeam = Team.getNflTeam(team);
				awayTeam = Team.getNflTeam(opp);
			}
			
			if (homeTeam == null || awayTeam == null) {
				System.out.println(fpQbs[i][0]);
				System.out.println("GOT A NULL");
				continue;
			}
			
			Matchup matchup = new Matchup(homeTeam, awayTeam, fpQbs[i][2]);
			matchup.setPtSpread(fpQbs[i][3]);
			matchup.setOverUnder(fpQbs[i][5]);
			matchup.setAwayAdjust("");
			matchup.setHomeAdjust("");
			
			boolean inList = false;
			for (Matchup match : nflMatchups) {
				if (match.getHomeTeam().equals(homeTeam)) {
					inList = true;
				}
			}
			if (inList) {
				continue;
			} else {
				nflMatchups.add(matchup);
			}
						
		}
		
	}
	
	
	// method to find row to match name in position list from fantasy pros data
	public static int getRow (String name, String[][] posData) {
		name = name.toLowerCase().trim();
		int i = 0; 
		while (i < posData.length) {
			String curName = posData[i][0]; 
			if (curName.indexOf('(') > -1) {
				int parenLoc = curName.indexOf('(');
				curName = curName.substring(0, parenLoc - 1);
			}
			
			curName = shortName(curName).toLowerCase();
			curName = curName.trim();
			
			
			if (curName.contains(name)) {
				break;
			} 
			i = i + 2;
		}
		if (i >= posData.length) {
			return -1;
		} else {
			return i;
		}
		
		
	}
	
		
	// method to remove suffix 'ie jr, sr, II, III, etc' from names
	public static String shortName(String name) {
		if (name == null || name.isEmpty()) {
			return null;
		} 
		String lowerName = name.toLowerCase();
		String[] split = lowerName.split("\\s+");
		split[0] = split[0].replaceAll("\\.", "");
		if (split.length > 2) {
			split[split.length - 1] = split[split.length - 1].replaceAll("(jr\\.|jr|sr\\.|sr|iii|ii|iv|v)", "");
			String ret = ""; 
			for (int i = 0; i < split.length; i++) {
				if (!split[i].isEmpty()) {
				ret = ret + " " + split[i].substring(0,1).toUpperCase() + split[i].substring(1).toLowerCase();
				}
			}
			ret = ret.trim();
			return ret;
		} else {
			String ret = ""; 
			for (int i = 0; i < split.length; i++) {
				if (!split[i].isEmpty()) {
				ret = ret + " " + split[i].substring(0,1).toUpperCase() + split[i].substring(1).toLowerCase();
				}
			}
			ret = ret.trim();
			return ret;
		}		
	}
	
	// method to get players opponent from matchup string given players team name
	public static String getOpponent(String matchup, String team) {
		
		int i = matchup.indexOf('@');
		int j = matchup.indexOf(' ');
		String first = matchup.substring(0,i);
		String second = matchup.substring(i+1,j);
		
		if (team.equals(first)){
			return second;
		} else {
			return first;
		}
		
	}
	
}
