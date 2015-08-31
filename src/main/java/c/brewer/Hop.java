package c.brewer;

import java.util.Calendar;
import java.util.Date;

import c.Log;
import m.serial.drivers.DS1820;

public class Hop {

	ParameterHolder parameters;
	private DS1820 tempSensor;
	private Date hopStart = null;
	private Calendar cal;
	private int hopNumber = 1;
	private final static int boilingTemp = 18;
	public Hop(ParameterHolder parameters) {
		this.parameters = parameters;
		tempSensor = parameters.tempSensor;
		hopNumber = 1;
	}

	public boolean checkBoiling() {
		if (tempSensor.GetTemperature(0) > boilingTemp ) {
			heat(50);
			return true;
		}
		heat(100);
		return false;
	}

	public boolean checkHop() {
		if (hopStart == null) {
			hopStart = Calendar.getInstance().getTime();
		}
		cal = Calendar.getInstance();
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(hopStart);
		cal1.add(Calendar.MINUTE, parameters.hops.get(hopNumber-1).getStartMinute());
		if (cal1.getTime().after(cal.getTime())) {
			addHop(hopNumber-1);
			hopNumber++;
		}
		if (tempSensor.GetTemperature(0) < 90) {
			heat(100);
		} else if (tempSensor.GetTemperature(0) > boilingTemp) {
			heat(50);
		}

		cal1.setTime(hopStart);
		cal1.add(Calendar.MINUTE, parameters.hopDuration);
		Log.rootLogger.debug("current time: " + cal.getTime() +" end time: "+ cal1.getTime());
		if (cal.getTime().after(cal1.getTime())) {
			heat(0);
			openCoolingValve();
			return true;
		}
		
		return false;
	}

	public boolean checkCooling() {
		if(getMainQuantity()==0){
			closeCoolingValve();
			return true;
		}
		return false;
	}

	private int getMainQuantity() {
		Log.rootLogger.debug("Get main tun quantity: " + 0);
		// TODO Auto-generated method stub
		return 0;
	}

	private void closeCoolingValve() {
		Log.rootLogger.debug("Closing cooling valve");
		// TODO Auto-generated method stub	
	}

	private void openCoolingValve() {
		Log.rootLogger.debug("Opening cooling valve");
		// TODO Auto-generated method stub
	}

	private void addHop(int hopNumber) {
		Log.rootLogger.debug("Add hop with number: "+hopNumber);
		// TODO Auto-generated method stub
	}

	private void heat(int percentPower) {
		Log.rootLogger.debug("Heating with power: "+percentPower);
		// TODO Auto-generated method stub
	}
}
