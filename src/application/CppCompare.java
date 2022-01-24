package application;

import java.util.Comparator;


// class to compare players by cost per point
public class CppCompare implements Comparator<Player>{

	@Override
	public int compare(Player o1, Player o2) {
		// TODO Auto-generated method stub
		if (o1.getCpp() < o2.getCpp()) {
			return -1;
		}
		if (o1.getCpp() > o2.getCpp()) {
			return 1;
		}
		else {
			return 1;
		}
	}

}
