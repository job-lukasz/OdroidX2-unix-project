package m.gpio;


import m.gpio.StaticValues.*;

public class PinState {
	
	private OdroidX2PIN pinID;
	private Direction direction;
	private boolean value;
	private boolean isSoftPWN;
	private boolean open;
	private String address;
	
	public PinState() {
		setPinID(null);
		setDirection(Direction.out);
		setValue(false);
		setSoftPWN(false);
		setOpen(false);
		setAddress(null);
	}

	public OdroidX2PIN getPinID() {
		return pinID;
	}
	public void setPinID(OdroidX2PIN pinID) {
		this.pinID = pinID;
		setAddress(StaticValues.pinMap.get(pinID));
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public boolean isValue() {
		return value;
	}

	public void setValue(boolean value) {
		this.value = value;
	}

	public boolean isSoftPWN() {
		return isSoftPWN;
	}

	public void setSoftPWN(boolean isSoftPWN) {
		this.isSoftPWN = isSoftPWN;
	}

	public boolean getOpen() {
		return open;
	}

	public void setOpen(boolean isOpen) {
		this.open = isOpen;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
};