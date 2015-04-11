package m.gpio;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import m.gpio.StaticValues.Direction;
import m.gpio.StaticValues.OdroidX2PIN;

public class GPIO_Pin implements Comparable<GPIO_Pin>{

	private OdroidX2PIN pinID;
	private Direction direction;
	private boolean value;
	private boolean isSoftPWN;
	private boolean isOpen;
	private String address;

	public GPIO_Pin(OdroidX2PIN odroidX2PIN) {
		pinID = odroidX2PIN;
		setDirection(Direction.out);
		value =false;
		isSoftPWN = false;
		isOpen = false;
		address = StaticValues.pinMap.get(odroidX2PIN);
	}

	public boolean open(Direction direction) {
		exportPin();
		setDirection(direction);
		isOpen = true;
		return true;
	}

	public boolean getValue() {
		if (isPinExported()) {
			if (direction == Direction.out) {
				value = readFromFile("/sys/class/gpio/gpio" + address + "/value").equals("1");
			}
			return value;
		}
		return false;
	}

	public boolean setValue(boolean _value) {
		if (isPinExported()) {
			value =_value;
			if (value) {
				return writeToFile("/sys/class/gpio/gpio" + address + "/value", "1");
			}
			return writeToFile("/sys/class/gpio/gpio" + address + "/value", "0");
		}
		value = false;
		return false;
	}

	public boolean close() {
		if (isPinExported()) {
			return writeToFile("/sys/class/gpio/unexport", address);
		}
		isOpen = false;
		return false;
	}

	private boolean exportPin() {
		if (!isPinExported()) {
			return writeToFile("/sys/class/gpio/export", address);
		}
		return true;
	}

	private boolean setDirection(Direction _direction) {
		direction = _direction;
		if (isPinExported()) {
			return writeToFile("/sys/class/gpio/gpio" + address + "/direction", direction.toString());
		}
		return false;
	}

	private boolean isPinExported() {
		return !readFromFile("/sys/class/gpio/gpio" + address + "/direction").equals("ERROR");
	}

	private boolean writeToFile(String fileName, String value) {
		PrintWriter zapis;
		try {
			zapis = new PrintWriter(fileName);
		} catch (FileNotFoundException e) {
			return false;
		}
		zapis.print(value);
		zapis.close();
		return true;
	}

	private String readFromFile(String fileName) {
		BufferedReader file = null;
		String result = "";
		try {
			file = new BufferedReader(new FileReader(fileName));
			result = file.readLine();
		} catch (FileNotFoundException e) {
			return "ERROR";
		} catch (IOException e) {
			return "ERROR";
		} finally {
			if (file != null) {
				try {
					file.close();
				} catch (IOException e) {
					return "ERROR";
				}
			}
		}
		return result;
	}
	
	@Override
	public int compareTo(GPIO_Pin pin) {
		if (Integer.parseInt(pinID.toString().substring(3)) < Integer.parseInt(pin.pinID.toString().substring(3))) {
			return -1;
		}
		else if (Integer.parseInt(pinID.toString().substring(3)) > Integer.parseInt(pin.pinID.toString().substring(3))){
			return 1;
		}
		return 0;
	}

	public OdroidX2PIN getPinID() {
		return pinID;
	}

	public boolean isSoftPWN() {
		return isSoftPWN;
	}

	public boolean isOpen() {
		return isOpen;
	}

	public String getAddress() {
		return address;
	}

	public Direction getDirection() {
		return direction;
	}
	
}
