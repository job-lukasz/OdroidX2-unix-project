package c.Brewer;

public class Brew {
	
	ParameterHolder parameters;
	
	public Brew(ParameterHolder parameters){
		this.parameters=parameters;
	}
	public boolean checkMinTemp() {
		return false;
	}

	public boolean checkBrewTemperature() {
		return false;
	}

	public boolean checkMashOut() {
		return false;
	}
}
