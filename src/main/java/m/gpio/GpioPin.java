package m.gpio;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import m.gpio.StaticValues.*;

public class GpioPin {

	PinState pinState;

	public GpioPin(OdroidX2PIN odroidX2PIN) {
		pinState = new PinState();
		pinState.setPinID(odroidX2PIN);
	}

	public boolean open(Direction direction) {
		exportPin();
		setDirection(direction);
		pinState.setOpen(true);
		return true;
	}

	public boolean getValue() {
		if (isPinExported()) {
			if (pinState.getDirection() == Direction.out) {
				pinState.setValue(readFromFile("/sys/class/gpio/gpio" + pinState.getAddress() + "/value").equals("1"));
			}
			return pinState.isValue();
		}
		return false;
	}

	public boolean setValue(boolean value) {
		if (isPinExported()) {
			pinState.setValue(value);
			if (value) {
				return writeToFile("/sys/class/gpio/gpio" + pinState.getAddress() + "/value", "1");
			}
			return writeToFile("/sys/class/gpio/gpio" + pinState.getAddress() + "/value", "0");
		}
		pinState.setValue(false);
		return false;
	}

	public boolean close() {
		if (isPinExported()) {
			return writeToFile("/sys/class/gpio/unexport", pinState.getAddress());
		}
		pinState.setOpen(false);
		return false;
	}

	private boolean exportPin() {
		if (!isPinExported()) {
			return writeToFile("/sys/class/gpio/export", pinState.getAddress());
		}
		return true;
	}

	private boolean setDirection(Direction direction) {
		pinState.setDirection(direction);
		if (isPinExported()) {
			return writeToFile("/sys/class/gpio/gpio" + pinState.getAddress() + "/direction", direction.toString());
		}
		return false;
	}

	private boolean isPinExported() {
		return !readFromFile("/sys/class/gpio/gpio" + pinState.getAddress() + "/direction").equals("ERROR");
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
}
