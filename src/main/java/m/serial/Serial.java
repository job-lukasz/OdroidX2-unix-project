package m.serial;

import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortTimeoutException;

/**
 * Jssc class adapter
 * 
 * @author Lukasz Job
 * 
 */
public class Serial {
	private static final String ADCstartCode = "ADCSTART";
	private static final String DACstartCode = "DACSTART";
	private static final int bauadRate = SerialPort.BAUDRATE_115200;
	private static final int dataBits = SerialPort.DATABITS_8;
	private static final int stopBits = SerialPort.STOPBITS_1;
	private static final int parity = SerialPort.PARITY_NONE;

	private SerialPort serialPort;

	public Serial(String portName) {
		serialPort = new SerialPort(portName);
	}

	public byte[] startMeasure(int bytesNumber) throws SerialPortException,
			SerialPortTimeoutException {
		byte[] buffer = null;
		try {
			serialPort.openPort();// Open serial port
			serialPort.setParams(bauadRate, dataBits, stopBits, parity);
			serialPort.writeBytes(ADCstartCode.getBytes());
			buffer = serialPort.readBytes(bytesNumber, 5000);
		} catch (SerialPortException | SerialPortTimeoutException e) {
			throw e;
		} finally {
			if(serialPort.isOpened()){
				serialPort.closePort();// Close serial port
			}
		}
		return buffer;
	}

	public boolean setDAC(int voltagePercenet) throws SerialPortException,
			SerialPortTimeoutException {
		byte[] buffer = null;
		try {
			serialPort.openPort();// Open serial port
			serialPort.setParams(bauadRate, dataBits, stopBits, parity);
			String startMessage = (DACstartCode + String.valueOf(voltagePercenet));
			serialPort.writeBytes(startMessage.getBytes());
			buffer = serialPort.readBytes(1, 5000);
		} catch (SerialPortException | SerialPortTimeoutException e) {
			throw e;
		} finally {
			if(serialPort.isOpened()){
				serialPort.closePort();// Close serial port
			}
		}
		if (buffer[0] == 1) {
			return true;
		}
		return false;
	}
}