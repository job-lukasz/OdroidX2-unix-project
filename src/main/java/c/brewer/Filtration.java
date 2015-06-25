package c.brewer;

import java.util.Calendar;
import java.util.Date;

import c.Log;
import m.serial.drivers.DS1820;

public class Filtration {

	// private ParameterHolder parameters;
	private DS1820 tempSensor;
	private Date filtrationStart = null;
	private Calendar cal;
	private static final int filtrationWaterTemperature = 85;
	private static final int filtrationTransferTime = 1;//TODO fix static values
	private int filtrationQuantity;

	public Filtration(ParameterHolder parameters) {
		// this.parameters = parameters;
		tempSensor = parameters.tempSensor;
		filtrationQuantity = 0;//TODO parameters.
	}

	public boolean checkTransfer() {
		if (filtrationStart == null) {
			filtrationStart = Calendar.getInstance().getTime();
		}
		Calendar cal1 = Calendar.getInstance();
		cal = Calendar.getInstance();
		cal1.setTime(filtrationStart);
		cal1.add(Calendar.MINUTE, filtrationTransferTime);
		Log.rootLogger.debug("current time: " + cal.getTime() +" end time: "+ cal1.getTime());
		if (cal.getTime().after(cal1.getTime())) {
			washOut();
			closeMainValve();
			openFiltrationValve();
			pourWater();
			filtrationStart = null;
			return true;
		}
		return false;
	}

	public boolean checkFiltrationProgress() {
		if (tempSensor.GetTemperature(0) < filtrationWaterTemperature) {
			heat(100);
		} else {
			heat(0);
		}
		if (getFiltrationQuantity() >= filtrationQuantity) {
			closeFiltrationValve();
			openMainValve();
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			closeMainValve();
			startPomping();
			return true;
		}
		return false;
	}

	public boolean checkPumpingProgress() {
		if (getFiltrationQuantity() > 0) {
			return false;
		}
		stopPomping();
		return true;
	}

	private void washOut() {
		Log.rootLogger.debug("Washing out");
		// TODO implement this method
	}

	private void pourWater() {
		Log.rootLogger.debug("Filling main tun with water");
		// TODO Auto-generated method stub
	}

	private void openMainValve() {
		Log.rootLogger.debug("Opening main value");
		// TODO Auto-generated method stub

	}

	private void closeMainValve() {
		Log.rootLogger.debug("Closing main value");
		// TODO Auto-generated method stub

	}

	private void startPomping() {
		Log.rootLogger.debug("Start pomping");
		// TODO Auto-generated method stub

	}

	private void stopPomping() {
		Log.rootLogger.debug("Stop pomping");
		// TODO Auto-generated method stub

	}

	private void heat(int percentPower) {
		Log.rootLogger.debug("Heating with power:"+percentPower+"%");
		// TODO Auto-generated method stub

	}

	private int getFiltrationQuantity() {
		Log.rootLogger.debug("Get filtration tun quantity: " + 0);
		// TODO Auto-generated method stub
		return 0;
	}

	private void openFiltrationValve() {
		Log.rootLogger.debug("Opening filtration value");
		// TODO Auto-generated method stub
	}

	private void closeFiltrationValve() {
		Log.rootLogger.debug("Close filtration value");
		// TODO Auto-generated method stub
	}
}
