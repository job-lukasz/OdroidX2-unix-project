package c.Brewer;

public class Hop {

	ParameterHolder parameters;
	
	public Hop(ParameterHolder parameters){
		this.parameters=parameters;
	}
	
	public boolean checkBoiling() {
		return false;
	}
	
	public boolean checkHop() {
		return false;
	}
	
	public boolean checkCooling() {
		return false;
	}
}
