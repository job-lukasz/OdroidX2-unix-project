package c.Brewer;

public class Filtration {
	
	ParameterHolder parameters;
	
	public Filtration(ParameterHolder parameters){
		this.parameters=parameters;
	}
	
	public boolean checkTransfer() {
		return false;
	}

	public boolean checkFiltrationProgress() {
		return false;
	}
	
	public boolean checkPumpingProgress() {
		return false;
	}
}
