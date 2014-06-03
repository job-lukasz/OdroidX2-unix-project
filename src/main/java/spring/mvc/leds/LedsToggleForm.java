package spring.mvc.leds;

import gpio.StaticValues.OdroidX2PIN;

public class LedsToggleForm {

	private OdroidX2PIN pinID;
	private boolean pinValue;
	
	public OdroidX2PIN getPinID() {
		return pinID;
	}

	public void setPinID(OdroidX2PIN pinID) {
		this.pinID = pinID;
	}

	public boolean getPinValue() {
		return pinValue;
	}

	public void setPinValue(boolean pinValue) {
		this.pinValue = pinValue;
	}
}
