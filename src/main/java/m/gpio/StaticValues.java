package m.gpio;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class StaticValues {
	public enum OdroidX2PIN {
		PIN1("220"), PIN2("12"), PIN3("44"), PIN4("209"), PIN5("45"), PIN6("20"), PIN7("19"), PIN8("18"), PIN9("203"), PIN10("21"), PIN11("23"), PIN13("38"), PIN14(
				"13"), PIN15("22"), PIN16("10"), PIN17("112"), PIN18("115"), PIN19("93"), PIN20("100"), PIN21("108"), PIN22("91"), PIN23("90"), PIN24("99"), PIN25(
				"111"), PIN26("103"), PIN27("88"), PIN28("98"), PIN29("89"), PIN30("114"), PIN31("87"), PIN33("94"), PIN34("105"), PIN35("97"), PIN36("102"), PIN37(
				"107"), PIN38("110"), PIN39("101"), PIN40("117"), PIN41("92"), PIN42("96"), PIN43("116"), PIN44("106"), PIN45("109");

		public String pinID;
		private Direction direction;
		private boolean value;
		private boolean isSoftPWN;
		private boolean isOpen;
		private String address;
		
		OdroidX2PIN(String pinID) {
			this.pinID = pinID;
		}

		public void setHigh() {
			openPin();
			setValue(true);
		}

		public void setLow() {
			openPin();
			setValue(false);
		}

		public boolean toggle() {
			openPin();
			setValue(!getValue());
			return getValue();
		}

		// TODO READ FROM PIN
		private void openPin() {
			if (isOpen()) {
				if (getDirection() != Direction.out) {
					close();
					open(Direction.out);
				}
			} else {
				open(Direction.out);
			}
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
				value = _value;
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

//		@Override
//		public int compareTo(OdroidX2PIN pin) {
//			if (Integer.parseInt(pinID.toString().substring(3)) < Integer.parseInt(pin.pinID.toString().substring(3))) {
//				return -1;
//			} else if (Integer.parseInt(pinID.toString().substring(3)) > Integer.parseInt(pin.pinID.toString().substring(3))) {
//				return 1;
//			}
//			return 0;
//		}

		public String getPinID() {
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
		
//		public void setPWM(long high_microS, long freq_microS) {
//			openPin();
//			Thread pwmThread = new Thread(pwmPins.get(odroidPin));
//			pwmThread.start();
//		}
	};

	public static enum Direction {
		in, out
	}
}
