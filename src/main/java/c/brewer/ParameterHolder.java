package c.brewer;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import m.serial.drivers.DS1820;
import m.settings.Settings;

import org.springframework.beans.factory.annotation.Autowired;

import c.Log;
import c.brew.BrewBreak;
import c.brew.BrewHop;
import c.brew.BrewRepository;
import c.brew.Brewing;

public class ParameterHolder {
	
	@Autowired
	private BrewRepository brewRepository;
	
	public Date startTime;
	public int breakNumber = 0;
	public List<BrewBreak> breaks;
	public List<BrewHop> hops;
	public int hopDuration;
	public DS1820 tempSensor;
	//public Set<BrewBreak> Addons;
	
	public ParameterHolder(Brewing brewing){
		Calendar cal = Calendar.getInstance();
		startTime = cal.getTime();
		hopDuration = brewing.getHopDuration();
		breaks = new ArrayList<BrewBreak>(brewing.getBreaks());
		hops = new ArrayList<BrewHop>(brewing.getHops());
		java.util.Collections.sort(breaks);
		java.util.Collections.sort(hops);
		tempSensor = new DS1820();
		tempSensor.InitCOM(Settings.getInstance().getTemperaturePortName());
	    tempSensor.setReadTime(750);
	    tempSensor.InitBus();
	    Log.rootLogger.debug("Temperature after init: "+tempSensor.GetTemperature(0));
	}

	public void destroy() {
		tempSensor.termitCom();
		
	}


}
