package spring.mvc.dac;

public class DACParamtersForm {
	private String portName;
	private int voltagePercent;
	public String getPortName() {
		return portName;
	}

	public void setPortName(String portName) {
		this.portName = portName;
	}

	public int getVoltagePercent() {
		return voltagePercent;
	}

	public void setVoltagePercent(int voltagePercent) {
		this.voltagePercent = voltagePercent;
	}
}
