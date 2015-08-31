package c.brewer;

import java.util.Calendar;
import java.util.Date;

import m.serial.drivers.DS1820;
import c.Log;

public class Brew {

	private ParameterHolder parameters;
	private DS1820 tempSensor;
	private Date brewStart = null;
	private Calendar cal;
	private breakNumber = 1; 
	private static final int startTempOffset = 10;
	private static final int breakTemperatureOffset = 1;
	private static final int mashOutTemp = 18;// TODO fix static values

	public Brew(ParameterHolder parameters) {
		this.parameters = parameters;
		tempSensor = parameters.tempSensor;
		breakNumber =1;
	}

	public boolean checkMinTemp() {
		double temp = tempSensor.GetTemperature(0);
		Log.rootLogger.debug("Temperature: " + temp + "*C Minimal temp: " + parameters.breaks.get(parameters.breakNumber).get_break().getTemp_low()
				+ startTempOffset);
		if (temp < parameters.breaks.get(breakNumber-1).get_break().getTemp_low() + startTempOffset) {
			heat(100);
			mix(10);
			return false;
		} else {
			heat(0);
			mix(0);
		}
		return true;
	}

	public boolean checkBrewTemperature() {
		if (brewStart == null) {
			brewStart = Calendar.getInstance().getTime();
		}
		Calendar cal1 = Calendar.getInstance();
		cal = Calendar.getInstance();
		cal1.setTime(brewStart);
		cal1.add(Calendar.MINUTE, parameters.breaks.get(breakNumber-1).getDuration());
		Log.rootLogger.debug("current time: " + cal.getTime() + " end time: " + cal1.getTime());
		if (cal1.getTime().after(cal.getTime())) {
			checkTemperature(parameters.breaks.get(breakNumber-1).get_break().getTemp_low(), parameters.breaks.get(breakNumber-1).get_break().getTemp_high());
			return false;
		}
		if(breakNumber<parameters.breaks.size()){	
			breakNumer++;
			return false;
		}
		return true;
	}

	public boolean checkMashOut() {
		if (tempSensor.GetTemperature(0) > mashOutTemp) {
			heat(0);
			mix(0);
			openMainValve();
			return true;
		}
		heat(100);
		mix(50);
		return false;
	}

	private void checkTemperature(double min, double max) {
		double temp = tempSensor.GetTemperature(0);
		Log.rootLogger.debug("Getting temperature: "+temp+"*C");
		if (temp < min + breakTemperatureOffset) {
			heat(100);
			mix(50);
		} else if (temp > max) {
			heat(0);
			mix(100);
		} else {
			heat(0);
			mix(0);
		}
	}

	private void openMainValve() {
		Log.rootLogger.debug("Opening main value");
	}

	private void mix(int percentPower) {
		Log.rootLogger.debug("Mixing with power: "+percentPower+"%");
	}

	private void heat(int percentPower) {
		Log.rootLogger.debug("Heating with power: "+percentPower+"%");
	}
}
