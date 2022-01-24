package application;

import java.util.ArrayList;
import java.util.Collections;

public class Lineup implements Comparable<Lineup>{
	
	// positions
	private QB qb;
	private RB rb1;
	private RB rb2;
	private WR wr1;
	private WR wr2;
	private WR wr3;
	private TE te;
	private Offense flex;
	private DST dst;
	
	// total salary
	private int lineupSalary;
	
	// total score
	private double lineupProj;
	
	// unique identifier
	private String lineupID;
	
	// no arg constructor
	public Lineup() {
		this.setQb(null);
		this.setRb1(null);
		this.setRb2(null);
		this.setWr1(null);
		this.setWr2(null);
		this.setWr3(null);
		this.setTe(null);
		this.setFlex(null);
		this.setDst(null);
		this.setLineupID(null);
		this.setLineupSalary(0);
		this.setLineupProj(0.0);
	}
	
	// full arg constructor
	public Lineup(QB qb, RB rb1, RB rb2, WR wr1, WR wr2, WR wr3, TE te, Offense flex, DST dst) {
		this.setQb(qb);
		this.setRb1(rb1);
		this.setRb2(rb2);
		this.setWr1(wr1);
		this.setWr2(wr2);
		this.setWr3(wr3);
		this.setTe(te);
		this.setFlex(flex);
		this.setDst(dst);
		
		// calculate the total team salary by summing player salaries
		this.setLineupSalary(qb.getSalary() + rb1.getSalary() + rb2.getSalary() + wr1.getSalary()
							+ wr2.getSalary() + wr3.getSalary() + te.getSalary() + flex.getSalary() + dst.getSalary());
				
		// calculate the total team point projection by summing play projections
		this.setLineupProj(qb.getCustom() + rb1.getCustom() + rb2.getCustom() + wr1.getCustom()
						+ wr2.getCustom() + wr3.getCustom() + te.getCustom() + flex.getCustom() + dst.getCustom());
				
		// create team ID
		ArrayList<Integer> ids = new ArrayList<>();
		ids.add(rb1.getPosID());
		ids.add(rb2.getPosID());
		ids.add(wr1.getPosID());
		ids.add(wr2.getPosID());
		ids.add(wr3.getPosID());
		ids.add(te.getPosID());
		ids.add(flex.getPosID());
				
		Collections.sort(ids);
		ids.add(0, dst.getPosID());
		ids.add(0, qb.getPosID());
				
		ArrayList<String> strIDs = new ArrayList<>();
		for (int i = 0; i < ids.size(); i++) {
			strIDs.add(threeDig(ids.get(i)));
		}
		String idString = "";
		for (int i = 0; i < strIDs.size(); i++) {
			idString = idString + strIDs.get(i);
		}
				
		this.setLineupID(idString);
				
	}
	

	public QB getQb() {
		return qb;
	}

	public void setQb(QB qb) {
		this.qb = qb;
	}

	public RB getRb1() {
		return rb1;
	}

	public void setRb1(RB rb1) {
		this.rb1 = rb1;
	}
	
	public RB getRb2() {
		return rb2;
	}

	public void setRb2(RB rb2) {
		this.rb2 = rb2;
	}

	public WR getWr1() {
		return wr1;
	}

	public void setWr1(WR wr1) {
		this.wr1 = wr1;
	}

	public WR getWr2() {
		return wr2;
	}

	public void setWr2(WR wr2) {
		this.wr2 = wr2;
	}

	public WR getWr3() {
		return wr3;
	}

	public void setWr3(WR wr3) {
		this.wr3 = wr3;
	}

	public TE getTe() {
		return te;
	}

	public void setTe(TE te) {
		this.te = te;
	}

	public Offense getFlex() {
		return flex;
	}

	public void setFlex(Offense flex) {
		this.flex = flex;
	}

	public DST getDst() {
		return dst;
	}

	public void setDst(DST dst) {
		this.dst = dst;
	}

	public String getLineupID() {
		return lineupID;
	}

	public void setLineupID(String lineupID) {
		this.lineupID = lineupID;
	}

	public int getLineupSalary() {
		return lineupSalary;
	}

	public void setLineupSalary(int lineupSalary) {
		this.lineupSalary = lineupSalary;
	}

	public double getLineupProj() {
		return lineupProj;
	}

	public void setLineupProj(double lineupProj) {
		this.lineupProj = lineupProj;
	}
	
	// method to make string ints all 3 digits (add leading 0s if necessary)
	public static String threeDig(int plrID) {
		if (plrID < 10) {
			return "00".concat(Integer.toString(plrID));
		} else if (plrID < 100) {
			return "0".concat(Integer.toString(plrID));
		} else {
			return Integer.toString(plrID);
		}
	}
	
	@Override
	public int compareTo(Lineup compareTeam) {
		if (compareTeam.getLineupProj() > this.getLineupProj()) {
			return -1;
		} else if (compareTeam.getLineupProj() < this.getLineupProj()) {
			return 1;
		} else {
			return 0;
		}
	}
	
	@Override
	public String toString() {
		return qb + ":" +  rb1 + ":" + rb2
			+ ":" + wr1 + ":" + wr2 + ":" + wr3
			+ ":" + te + ":" + flex	+ ":" + dst;
	}

}
