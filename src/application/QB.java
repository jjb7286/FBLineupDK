package application;

public class QB extends Offense {
	
	// locked boolean
	private boolean locked;
	
	// string representation of locked
	private String lockedString;
	
	// no arg constructor
	public QB() {
		super();
		this.setLocked(false);
		this.setLockedString("");
	}
	

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
		if (locked == true) {
			setLockedString("LOCKED");
		} else {
			setLockedString("");
		}
	}

	public String getLockedString() {
		return lockedString;
	}

	public void setLockedString(String lockedString) {
		this.lockedString = lockedString;
	}
	
	

}
