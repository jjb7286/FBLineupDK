package application;

import java.util.ArrayList;
import java.util.Collections;

import view.GuiController;



public class LineupHelper {
	
	public static double progress = 0.0;
	
	public static double rbMaxTeams;
	public static double rbMinScore;
	
	public static double wrMaxTeams;
	public static double wrMinScore;
	
	public static double teMaxTeams;
	public static double teMinScore;
	
	public static double dstMaxTeams;
	public static double dstMinScore;
	
	// locked qb
	public static QB qbF; 
	
	// locked players and percTeams
	public static RB rbLock;
	public static double rbLockSize;
	
	public static WR wr1Lock;
	public static double wr1LockSize;
	
	public static WR wr2Lock;
	public static double wr2LockSize;
	
	public static TE teLock;
	public static double teLockSize;
	
	public static DST dstLock;
	public static double dstLockSize;
	
	// flex booleans
	public static boolean rbFlexF, wrFlexF, teFlexF;
	
	// dst no conflict boolen
	public static boolean dstConflict;
	
	// final position lists
	public static ArrayList<RB> rbsF = new ArrayList<RB>();
	public static ArrayList<WR> wrsF = new ArrayList<WR>();
	public static ArrayList<TE> tesF = new ArrayList<TE>();
	public static ArrayList<Offense> flexsF = new ArrayList<Offense>();
	public static ArrayList<DST> dstsF = new ArrayList<DST>();
	
	// lineup lists for each locked position and required length
	public static int rb1LineupsLength = 0;
	public static int rb2LineupsLength = 0;
	public static int wr1LineupsLength = 0;
	public static int wr2LineupsLength = 0;
	public static int wr3LineupsLength = 0;
	public static int teLineupsLength = 0;
	
	// array list for the lineups
	public static ArrayList<Lineup> lineupList = new ArrayList<Lineup>();
	
	// max salary
	public static final int maxSal = 50000;
	
	// number of total lineups
	public static int numLineups = 150;
	
	// number of iterations per lineup maximization
	public static int numIterations = 10000;
	
	public static int curLineIndex = 0;
	
	public static void generateLineups() {
		
		removeLowScores();
		addQbStacks();
		addLocks();
		
		ArrayList<Lineup> temp = new ArrayList<Lineup>();
		
		for (Lineup curLineup : lineupList) {
			temp.add(getLineup(curLineup));			
		}
		
		lineupList.clear();
		lineupList.addAll(temp);
		
		
								
	}
	
	// method to take in a partial lineup and return full lineup with highest score after certain amount of iterations
	public static Lineup getLineup(Lineup partial) {
		QB qbTemp = qbF;
		RB rb1Temp, rb2Temp;
		WR wr1Temp, wr2Temp, wr3Temp;
		TE teTemp;
		Offense flexTemp;
		DST dstTemp;
		
		Lineup topLineup = new Lineup();
		
		
		
		for (int i = 0; i < numIterations; i++) {
			
			int rb1, rb2, wr1, wr2, wr3, te, flex, dst = -1;
			
			// check if dst locked, if locked set to lock if not randomly get one from list
			if (partial.getDst() != null) {
				dstTemp = partial.getDst();
			} else {
				dstTemp = dstsF.get((int)(Math.abs(Math.random() - Math.random()) * dstsF.size()));
				while (dstTemp.getPlayerTeam().equals(qbF.getOppTeam())) {
					dstTemp = dstsF.get((int)(Math.abs(Math.random() - Math.random()) * dstsF.size()));
				}
			}
			// check if rb1 locked, if locked set to lock if not randomly get one from list
			if (partial.getRb1() != null) {
				rb1Temp = partial.getRb1();
			} else {
				rb1Temp = rbsF.get((int)(Math.abs(Math.random() - Math.random()) * rbsF.size()));
				while ((dstConflict && rb1Temp.getPlayerTeam().equals(dstTemp.getOppTeam()))) {
					rb1Temp = rbsF.get((int)(Math.abs(Math.random() - Math.random()) * rbsF.size()));
				}
			}
			// check if rb2 locked, if locked set to lock if not randomly get one from list
			if (partial.getRb2() != null) {
				rb2Temp = partial.getRb2();
			} else {
				rb2Temp = rbsF.get((int)(Math.abs(Math.random() - Math.random()) * rbsF.size()));
				while ((dstConflict && rb2Temp.getPlayerTeam().equals(dstTemp.getOppTeam())) || rb2Temp.equals(rb1Temp)) {
					rb2Temp = rbsF.get((int)(Math.abs(Math.random() - Math.random()) * rbsF.size()));
				}
			}
			// check if wr1 locked, if locked set to lock if not randomly get one from list
			if (partial.getWr1() != null) {
				wr1Temp = partial.getWr1();
			} else {
				wr1Temp = wrsF.get((int)(Math.abs(Math.random() - Math.random()) * wrsF.size()));
				while (dstConflict && wr1Temp.getPlayerTeam().equals(dstTemp.getOppTeam())) {
					wr1Temp = wrsF.get((int)(Math.abs(Math.random() - Math.random()) * wrsF.size()));
				}
			}
			// check if wr2 locked, if locked set to lock if not randomly get one from list
			if (partial.getWr2() != null) {
				wr2Temp = partial.getWr2();
			} else {
				wr2Temp = wrsF.get((int)(Math.abs(Math.random() - Math.random()) * wrsF.size()));
				while (dstConflict && wr2Temp.getPlayerTeam().equals(dstTemp.getOppTeam()) || wr2Temp.equals(wr1Temp)) {
					wr2Temp = wrsF.get((int)(Math.abs(Math.random() - Math.random()) * wrsF.size()));
				}
			}
			// check if wr3 locked, if locked set to lock if not randomly get one from list
			if (partial.getWr3() != null) {
				wr3Temp = partial.getWr3();
			} else {
				wr3Temp = wrsF.get((int)(Math.abs(Math.random() - Math.random()) * wrsF.size()));
				while (dstConflict && wr3Temp.getPlayerTeam().equals(dstTemp.getOppTeam()) || wr3Temp.equals(wr1Temp) || wr3Temp.equals(wr2Temp)) {
					wr3Temp = wrsF.get((int)(Math.abs(Math.random() - Math.random()) * wrsF.size()));
				}
			}
			// check if te locked, if locked set to lock if not randomly get one from list
			if (partial.getTe() != null) {
				teTemp = partial.getTe();
			} else {
				teTemp = tesF.get((int)(Math.abs(Math.random() - Math.random()) * tesF.size()));
				while (dstConflict && teTemp.getPlayerTeam().equals(dstTemp.getOppTeam())) {
					teTemp = tesF.get((int)(Math.abs(Math.random() - Math.random()) * tesF.size()));
				}
			}
			// check if flex locked, if locked set to lock if not randomly get one from list
			if (partial.getFlex() != null) {
				flexTemp = partial.getFlex();
			} else {
				flexTemp = flexsF.get((int)(Math.abs(Math.random() - Math.random()) * flexsF.size()));
				while (dstConflict && flexTemp.getPlayerTeam().equals(dstTemp.getOppTeam()) 
						|| flexTemp.equals(wr1Temp) || flexTemp.equals(wr2Temp) || flexTemp.equals(wr3Temp)
						|| flexTemp.equals(rb1Temp) || flexTemp.equals(rb2Temp) || flexTemp.equals(teTemp)) {
					flexTemp = flexsF.get((int)(Math.abs(Math.random() - Math.random()) * flexsF.size()));
				}
			}
			
			Lineup tempLineup = new Lineup(qbTemp, rb1Temp, rb2Temp, wr1Temp, wr2Temp, wr3Temp, teTemp, flexTemp, dstTemp);
			if (tempLineup.getLineupSalary() > maxSal) {
				continue;
			}
			boolean duplicate = false;
			for (Lineup lp : lineupList) {
				if (tempLineup.getLineupID().equals(lp.getLineupID())) {
					duplicate = true;
				}
			}
			if (duplicate) {
				continue;
			}
			if (tempLineup.getLineupProj() > topLineup.getLineupProj()) {
				topLineup = tempLineup;
			}
			
		}
		
		// if it didn't come up with a single team keep trying
		while (topLineup.getQb() == null) {
			// loop through defenses
			for (int dst = dstsF.size() - 1; dst > dstsF.size() / 4; dst--) {
				// check if the defense is locked if not iterate through defenses
				if (partial.getDst() != null) {
					dstTemp = partial.getDst();
				} else {
					dstTemp = dstsF.get(dst);
				}
				
				// loop through rbs for rb1
				for (int rb1 = rbsF.size() - 1; rb1 > rbsF.size() / 4; rb1--)	{
					// check if rb1 is locked if not iterate through rbs
					if (partial.getRb1() != null) {
						rb1Temp = partial.getRb1();
					} else if (dstConflict && rbsF.get(rb1).getOppTeam().equals(dstTemp.getPlayerTeam())) { // if conflict with defense get next team in loop
						continue;
					} else {
						rb1Temp = rbsF.get(rb1);
					}
					
					// loop through rbs for rb2
					for (int rb2 = rbsF.size() - 1; rb2 > rbsF.size() / 4; rb2--) {
						// check if rb2 is locked if not iterate through rbs
						if (partial.getRb2() != null) {
							rb2Temp = partial.getRb2();
						} else if (dstConflict && rbsF.get(rb2).getOppTeam().equals(dstTemp.getPlayerTeam())) { // if conflict with defense get next team in loop
							continue;
						} else {
							rb2Temp = rbsF.get(rb2);
						}
						
						// loop through wrs for wr1
						for (int wr1 = wrsF.size() - 1; wr1 > wrsF.size() / 4; wr1--) {
							// check if wr1 is locked if not iterate through wrs
							if (partial.getWr1() != null) {
								wr1Temp = partial.getWr1()	;
							} else if (dstConflict && wrsF.get(wr1).getOppTeam().equals(dstTemp.getPlayerTeam())) { // if conflict with defense get next team in loop
								continue;
							} else {
								wr1Temp = wrsF.get(wr1);
							}
							
							// loop through wrs for wr2
							for (int wr2 = wrsF.size() - 1; wr2 > wrsF.size() / 4; wr2--) {
								// check if wr1 is locked if not iterate through wrs
								if (partial.getWr2() != null) {
									wr2Temp = partial.getWr2()	;
								} else if (dstConflict && wrsF.get(wr2).getOppTeam().equals(dstTemp.getPlayerTeam())) { // if conflict with defense get next team in loop
									continue;
								} else if (wr1Temp.equals(wrsF.get(wr2))) {
									continue;
								} else {
									wr2Temp = wrsF.get(wr2);
								}
								
								// loop through wrs for wr3
								for (int wr3 = wrsF.size() - 1; wr3 > wrsF.size() / 4; wr3--) {
									// check if wr1 is locked if not iterate through wrs
									if (partial.getWr3() != null) {
										wr3Temp = partial.getWr3()	;
									} else if (dstConflict && wrsF.get(wr3).getOppTeam().equals(dstTemp.getPlayerTeam())) { // if conflict with defense get next team in loop
										continue;
									} else if (wr1Temp.equals(wrsF.get(wr3)) || wr2Temp.equals(wrsF.get(wr3))) {
										continue;
									} else {
										wr3Temp = wrsF.get(wr3);
									}
									
									// loop through test for te
									for (int te = tesF.size() - 1; te > tesF.size() / 4; te--) {
										// check if wr1 is locked if not iterate through wrs
										if (partial.getTe() != null) {
											teTemp = partial.getTe()	;
										} else if (dstConflict && tesF.get(te).getOppTeam().equals(dstTemp.getPlayerTeam())) { // if conflict with defense get next team in loop
											continue;
										} else {
											teTemp = tesF.get(te);
										}
										
										// loop through flexs for flex
										for (int flex = flexsF.size() - 1; flex > flexsF.size() / 4; flex--) {
											// check if wr1 is locked if not iterate through wrs
											if (partial.getFlex() != null) {
												flexTemp = partial.getFlex()	;
											} else if (dstConflict && flexsF.get(flex).getOppTeam().equals(dstTemp.getPlayerTeam())) { // if conflict with defense get next team in loop
												continue;
											} else if (wr1Temp.equals(flexsF.get(flex)) || wr2Temp.equals(flexsF.get(flex)) || wr3Temp.equals(flexsF.get(flex)) 
													|| rb1Temp.equals(flexsF.get(flex)) || rb2Temp.equals(flexsF.get(flex)) || teTemp.equals(flexsF.get(flex))) {
												continue;
											} else {
												flexTemp = flexsF.get(flex);
											}
											
											Lineup lineupTemp = new Lineup(qbF, rb1Temp, rb2Temp, wr1Temp, wr2Temp, wr3Temp, teTemp, flexTemp, dstTemp);
											
											if (lineupTemp.getLineupSalary() > maxSal) {
												continue;
											}
											
											if (lineupTemp.getLineupProj() < topLineup.getLineupProj()) {
												continue;
											} else {
												topLineup = lineupTemp;
											}
											
											
											// if this flex was a lock no need to iterate through the list
											if (flexTemp.equals(partial.getFlex())) {
												break;
											}
											
										} // end flex loop									
										
										// if this TE was a lock no need to iterate through the list
										if (teTemp.equals(partial.getTe())) {
											break;
										}
										
									} // end te loop								
									
									// if this WR3 was a lock no need to iterate through the list
									if (wr3Temp.equals(partial.getWr3())) {
										break;
									}
									
								} // end wr3 loop							
								
								// if this WR2 was a lock no need to iterate through the list
								if (wr2Temp.equals(partial.getWr2())) {
									break;
								}
								
							} // end wr2 loop
							
							// if this WR1 was a lock no need to iterate through the list
							if (wr1Temp.equals(partial.getWr1())) {
								break;
							}
							
						} // end wr1 loop
						
						// if this RB2 was a lock no need to iterate through the list
						if (rb2Temp.equals(partial.getRb2())) {
							break;
						}
						
					} // end rb2 loop
						
					
					// if this RB1 was a lock no need to iterate through the list
					if (rb1Temp.equals(partial.getRb1())) {
						break;
					}
					
				} // end rb1 loop
				
				// if this DST was a lock no need to iterate through the list, break out of loop
				if (dstTemp.equals(partial.getDst())) {
					break;
				}				
				
			} // end dst loop
			topLineup = getLineup(partial);
		}
		
		System.out.println(topLineup);
		topLineup.getRb1().setNumLineups(topLineup.getRb1().getNumLineups() + 1);
		if (topLineup.getRb1().getNumLineups() > rbMaxTeams) {
			rbsF.remove(topLineup.getRb1());
		}
		topLineup.getRb2().setNumLineups(topLineup.getRb2().getNumLineups() + 1);
		if (topLineup.getRb2().getNumLineups() > rbMaxTeams) {
			rbsF.remove(topLineup.getRb2());
		}
		topLineup.getWr1().setNumLineups(topLineup.getWr1().getNumLineups() + 1);
		if (topLineup.getWr1().getNumLineups() > wrMaxTeams) {
			wrsF.remove(topLineup.getWr1());
		}
		topLineup.getWr2().setNumLineups(topLineup.getWr2().getNumLineups() + 1);
		if (topLineup.getWr2().getNumLineups() > wrMaxTeams) {
			wrsF.remove(topLineup.getWr2());
		}
		topLineup.getWr3().setNumLineups(topLineup.getWr3().getNumLineups() + 1);
		if (topLineup.getWr3().getNumLineups() > wrMaxTeams) {
			wrsF.remove(topLineup.getWr3());
		}
		topLineup.getTe().setNumLineups(topLineup.getTe().getNumLineups() + 1);
		if (topLineup.getTe().getNumLineups() > teMaxTeams) {
			tesF.remove(topLineup.getTe());
		}
		topLineup.getFlex().setNumLineups(topLineup.getFlex().getNumLineups() + 1);
		if (topLineup.getFlex() instanceof RB && topLineup.getFlex().getNumLineups() > rbMaxTeams) {
			rbsF.remove(topLineup.getFlex());
		}
		if (topLineup.getFlex() instanceof WR && topLineup.getFlex().getNumLineups() > wrMaxTeams) {
			wrsF.remove(topLineup.getFlex());
		}
		if (topLineup.getFlex() instanceof TE && topLineup.getFlex().getNumLineups() > teMaxTeams) {
			tesF.remove(topLineup.getFlex());
		}	
		topLineup.getDst().setNumLineups(topLineup.getDst().getNumLineups() + 1);
		if (topLineup.getDst().getNumLineups() > dstMaxTeams) {
			dstsF.remove(topLineup.getDst());
		}
		return topLineup;
	}
	
	// method to add locked players to the teams randomly 
	public static void addLocks() {
		
		if (rbLockSize > 0)	{
			int i = (int) (rbLockSize/100 * numLineups); 
			while (i > 0) {
				int rand = (int) (Math.random() * numLineups);
				if (lineupList.get(rand).getRb1() == null) {
					lineupList.get(rand).setRb1(rbLock);
					i--;
					continue;
				} else if (!lineupList.get(rand).getRb1().equals(rbLock) && lineupList.get(rand).getRb2() == null) {
					lineupList.get(rand).setRb2(rbLock);
					i--;
					continue;
				} else {
					continue;
				}
				
			}
		}
		
		if (wr1LockSize > 0)	{
			int i = (int) (wr1LockSize/100 * numLineups); 
			while (i > 0) {
				int rand = (int) (Math.random() * numLineups);
				if (lineupList.get(rand).getWr1() == null) {
					lineupList.get(rand).setWr1(wr1Lock);
					i--;
					continue;
				} else if (!lineupList.get(rand).getWr1().equals(wr1Lock) && lineupList.get(rand).getWr2() == null) {
					lineupList.get(rand).setWr2(wr1Lock);
					i--;
					continue;
				} else if (!lineupList.get(rand).getWr1().equals(wr1Lock) && !lineupList.get(rand).getWr2().equals(wr1Lock)
						&& lineupList.get(rand).getWr3() == null) {
					lineupList.get(rand).setWr3(wr1Lock);
					i--;
					continue;
					
				}
					else {
					continue;
				}
				
			}
		}
		
		if (wr2LockSize > 0)	{
			int i = (int) (wr2LockSize/100 * numLineups); 
			while (i > 0) {
				int rand = (int) (Math.random() * numLineups);
				if (lineupList.get(rand).getWr1() == null) {
					lineupList.get(rand).setWr1(wr2Lock);
					i--;
					continue;
				} else if (!lineupList.get(rand).getWr1().equals(wr2Lock) && lineupList.get(rand).getWr2() == null) {
					lineupList.get(rand).setWr2(wr2Lock);
					i--;
					continue;
				} else if (!lineupList.get(rand).getWr1().equals(wr2Lock) && !lineupList.get(rand).getWr2().equals(wr2Lock)
						&& lineupList.get(rand).getWr3() == null) {
					lineupList.get(rand).setWr3(wr2Lock);
					i--;
					continue;
					
				}
					else {
					continue;
				}
				
			}
		}
		
		if (teLockSize > 0)	{
			int i = (int) (teLockSize/100 * numLineups); 
			while (i > 0) {
				int rand = (int) (Math.random() * numLineups);
				if (lineupList.get(rand).getTe() == null) {
					lineupList.get(rand).setTe(teLock);
					i--;
					continue;
				} else if (!lineupList.get(rand).getTe().equals(teLock) && lineupList.get(rand).getFlex() == null) {
					lineupList.get(rand).setFlex(teLock);
					i--;
					continue;
				} else {
					continue;
				}
				
			}
		}
			
		if (dstLockSize > 0)	{
			int i = (int) (dstLockSize/100 * numLineups); 
			while (i > 0) {
				int rand = (int) (Math.random() * numLineups);
				if (lineupList.get(rand).getDst() == null) {
					lineupList.get(rand).setDst(dstLock);
					i--;
					continue;
				
				} else {
					continue;
				}
				
			}
		}		
	}
	
	
	// method to set up lists with locked qb and stack players
	public static void addQbStacks() {
		
		lineupList.clear();
		
		for (int i  = 0; i < rb1LineupsLength; i++) {
			Lineup lineup = new Lineup();
			lineup.setQb(qbF);
			lineup.setRb1(Stack.getRb1());
			lineupList.add(lineup);			
		}
		for (int i  = 0; i < rb2LineupsLength; i++) {
			Lineup lineup = new Lineup();
			lineup.setQb(qbF);
			lineup.setRb1(Stack.getRb2());
			lineupList.add(lineup);			
		}
		for (int i  = 0; i < wr1LineupsLength; i++) {
			Lineup lineup = new Lineup();
			lineup.setQb(qbF);
			lineup.setWr1(Stack.getWr1());
			lineupList.add(lineup);			
		}
		for (int i  = 0; i < wr2LineupsLength; i++) {
			Lineup lineup = new Lineup();
			lineup.setQb(qbF);
			lineup.setWr1(Stack.getWr2());
			lineupList.add(lineup);			
		}
		for (int i  = 0; i < wr3LineupsLength; i++) {
			Lineup lineup = new Lineup();
			lineup.setQb(qbF);
			lineup.setWr1(Stack.getWr3());
			lineupList.add(lineup);			
		}
		for (int i  = 0; i < teLineupsLength; i++) {
			Lineup lineup = new Lineup();
			lineup.setQb(qbF);
			lineup.setTe(Stack.getTe());
			lineupList.add(lineup);			
		}
		
		int j = numLineups - lineupList.size();
		
		for (int i = 0; i < j; i++) {
			Lineup lineup = new Lineup();
			lineup.setQb(qbF);
			lineupList.add(lineup);	
		}
		
	}

	// method to remove scores below the thresholds for each list and remove locked players
	public static void removeLowScores() {
		
		if (rbsF.size() > 0) {
			rbsF.clear();
			wrsF.clear();
			tesF.clear();
			flexsF.clear();
			dstsF.clear();
		}
		
		//RB List
		for (RB rb : ListHelper.rbs) {
			if (rb.getCustom() >= rbMinScore && !rb.equals(rbLock) && !rb.equals(Stack.getRb1()) && !rb.equals(Stack.getRb2())) {
				rb.setNumLineups(0);
				rbsF.add(rb);
			}
		}
		
		// instance of cppCompare for comparator
		CppCompare cppCompare = new CppCompare();
		
		Collections.sort(rbsF, cppCompare);
		Collections.reverse(rbsF);
		
		//WR List
		for (WR wr : ListHelper.wrs) {
			if (wr.getCustom() >= wrMinScore && !wr.equals(wr1Lock) && !wr.equals(wr2Lock) && !wr.equals(Stack.getWr1())
					|| !wr.equals(Stack.getWr2()) || !wr.equals(Stack.getWr3())) {
				wr.setNumLineups(0);
				wrsF.add(wr);
			}
		}
		
		Collections.sort(wrsF, cppCompare);
		Collections.sort(wrsF);
		
		//TE List
		for (TE te : ListHelper.tes) {
			if (te.getCustom() >= teMinScore && !te.equals(teLock) && !te.equals(Stack.getTe())) {
				te.setNumLineups(0);
				tesF.add(te);
			}
		}
		
		Collections.sort(tesF, cppCompare);
		Collections.sort(tesF);
		
		//DST List
		for (DST dst : ListHelper.dsts) {
			if (dst.getCustom() >= dstMinScore && !dst.equals(dstLock)) {
				dst.setNumLineups(0);
				dstsF.add(dst);
			}
		}
		
		Collections.sort(dstsF, cppCompare);
		Collections.sort(dstsF);
		
		// create flexs list
		// if RBs should be included add to list
		if (rbFlexF) {
			for (RB rb : rbsF) {
				flexsF.add(rb);
			}
		}
		// if WRs should be included add to list
		if (wrFlexF) {
			for (WR wr : wrsF) {
				flexsF.add(wr);
			}
		}
		// if TEs should be included add to list
		if (teFlexF) {
			for (TE te : tesF) {
				flexsF.add(te);
			}
		}
		
		Collections.sort(flexsF, cppCompare);
		Collections.reverse(flexsF);
			
	}
	

	
	/*
	public static void adjustLists() {
		//rb list
		if (rbLock != null) {
			rbsF.remove(rbLock);
			rbsF.add(0, rbLock);
		}
		rbsF.remove(Stack.getRb2());
		if (Stack.getRb2Num() != 0) {
			rbsF.add(0, Stack.getRb2());
		}
		rbsF.remove(Stack.getRb1());
		if (Stack.getRb1Num() != 0) {
			rbsF.add(0, Stack.getRb1());
		}
		
		//wr list
		if (wr2Lock != null) {
			wrsF.remove(wr2Lock);
			wrsF.add(0, wr2Lock);
		}
		if (wr1Lock != null) {
			wrsF.remove(wr1Lock);
			wrsF.add(0, wr1Lock);
		}
		wrsF.remove(Stack.getWr3());
		if (Stack.getWr3Num() != 0) {
			wrsF.add(0, Stack.getWr3());
		}
		wrsF.remove(Stack.getWr2());
		if (Stack.getWr2Num() != 0) {
			wrsF.add(0, Stack.getWr2());
		}
		wrsF.remove(Stack.getWr1());
		if (Stack.getWr1Num() != 0) {
			wrsF.add(0, Stack.getWr1());
		}
		
		//te list
		if (teLock != null) {
			tesF.remove(teLock);
			tesF.add(0, teLock);
		}
		tesF.remove(Stack.getTe());
		if (Stack.getTeNum() != 0) {
			tesF.add(0, Stack.getTe());
		}
		
		//dst list
		if (dstLock != null) {
			dstsF.remove(dstLock);
			dstsF.add(0, dstLock);
		}
		
	}
	*/

}
