package application;

public abstract class Offense extends Player {
	
	// injury status string
	private String injStatus;
	
	// red zone opportunity string
	private String rzOpp;
	
	// red zone performance
	private String rzPerf;
	
	// boolean for if score was customized
	private boolean customized;
	
	
	
	
	// no arg constructor
	public Offense() {
		super();
		this.setInjStatus("");
		this.setRzOpp("");
		this.setRzPerf("");
		this.setCustomized(false);
		this.setLockedPerc("");
	}

	public String getInjStatus() {
		return injStatus;
	}

	public void setInjStatus(String injStatus) {
		this.injStatus = injStatus;
	}
	
	public String getRzOpp() {
		return rzOpp;
	}

	public void setRzOpp(String rzOpp) {
		this.rzOpp = rzOpp;
	}

	public String getRzPerf() {
		return rzPerf;
	}

	public void setRzPerf(String rzPerf) {
		this.rzPerf = rzPerf;
	}

	public boolean isCustomized() {
		return customized;
	}

	public void setCustomized(boolean customized) {
		this.customized = customized;
	}


}
