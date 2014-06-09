package serial;

import jssc.SerialPort;
import jssc.SerialPortException;

/**
 * Jssc class adapter
 * 
 * @author Lukasz Job
 * 
 */
public class Serial {
	private static final String startCode = "ADCSTART";
	private static final int bauadRate = SerialPort.BAUDRATE_115200;
	private static final int dataBits = SerialPort.DATABITS_8;
	private static final int stopBits = SerialPort.STOPBITS_1;
	private static final int parity = SerialPort.PARITY_NONE;	
	
	private SerialPort serialPort;
	
	public Serial(String portName) {
		serialPort = new SerialPort(portName);
	}

	public byte[] startMeasure(int bytesNumber) throws SerialPortException {
		byte[] buffer = null;
		serialPort.openPort();// Open serial port
		serialPort.setParams(bauadRate, dataBits, stopBits, parity);
		serialPort.writeBytes(startCode.getBytes());
		buffer = serialPort.readBytes(bytesNumber);
		serialPort.closePort();// Close serial port
		return buffer;
	}
}