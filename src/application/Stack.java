package application;

import java.util.ArrayList;

public class Stack {
	
	private static RB rb1;
	private static int rb1Num = 0;
	
	private static RB rb2;
	private static int rb2Num = 0;
	
	private static TE te;
	private static int teNum = 0;
	
	private static WR wr1;
	private static int wr1Num = 0;
	
	private static WR wr2;
	private static int wr2Num = 0;
	
	private static WR wr3;
	private static int wr3Num = 0;
	
	public Stack() {
		setRb1(null);
		setRb1Num(0);
		setRb2(null);
		setRb2Num(0);
		setTe(null);
		setTeNum(0);
		setWr1(null);
		setWr1Num(0);
		setWr2(null);
		setWr2Num(0);
		setWr3(null);
		setWr3Num(0);
	}
	
	public static Stack setStackPlayers(ArrayList<Offense> list) {
		Stack stack = new Stack();
		
		for (Offense player : list) {
			
			switch(player.getPos()) {
				case ("RB"):
					if (rb1 == null) {
						rb1 = (RB) player;
						break;
					}
					else if (rb2 == null) {
						rb2 = (RB) player;
						break;
					}
					else {
						break;
					}
				case ("TE"):
					if (te == null) {
						te = (TE) player;
						break;
					} 
					else {
						break;
					}
				case ("WR"):
					if (wr1 == null) {
						wr1 = (WR) player;
						break;
					} 
					else if (wr2 == null) {
						wr2 = (WR) player;
						break;
					}
					else if (wr3 == null) {
						wr3 = (WR) player;
						break;
					}
					else {
						break;
					}
				default:
					break;
						
			}
			
			
			
			
		}
		
		
		
		return stack;
	}
	
	public static RB getRb1() {
		return rb1;
	}
	public static void setRb1(RB rb1) {
		Stack.rb1 = rb1;
	}
	public static RB getRb2() {
		return rb2;
	}
	public static void setRb2(RB rb2) {
		Stack.rb2 = rb2;
	}
	public static TE getTe() {
		return te;
	}
	public static void setTe(TE te) {
		Stack.te = te;
	}
	public static WR getWr1() {
		return wr1;
	}
	public static void setWr1(WR wr1) {
		Stack.wr1 = wr1;
	}
	public static WR getWr2() {
		return wr2;
	}
	public static void setWr2(WR wr2) {
		Stack.wr2 = wr2;
	}
	public static WR getWr3() {
		return wr3;
	}
	public static void setWr3(WR wr3) {
		Stack.wr3 = wr3;
	}
	public static int getRb1Num() {
		return rb1Num;
	}
	public static void setRb1Num(int rb1Num) {
		Stack.rb1Num = rb1Num;
	}
	public static int getRb2Num() {
		return rb2Num;
	}
	public static void setRb2Num(int rb2Num) {
		Stack.rb2Num = rb2Num;
	}
	public static int getTeNum() {
		return teNum;
	}
	public static void setTeNum(int teNum) {
		Stack.teNum = teNum;
	}
	public static int getWr1Num() {
		return wr1Num;
	}
	public static void setWr1Num(int wr1Num) {
		Stack.wr1Num = wr1Num;
	}
	public static int getWr2Num() {
		return wr2Num;
	}
	public static void setWr2Num(int wr2Num) {
		Stack.wr2Num = wr2Num;
	}
	public static int getWr3Num() {
		return wr3Num;
	}
	public static void setWr3Num(int wr3Num) {
		Stack.wr3Num = wr3Num;
	}

}
