package c.settings;

import c.Log;
import m.gpio.StaticValues.OdroidX2PIN;
import m.settings.Settings;

public class SettingsForm {

	private String temperaturePortName;
	private OdroidX2PIN firstHeaterPin;
	private OdroidX2PIN secondHeaterPin;
	
	private OdroidX2PIN mainValvePin;
	private OdroidX2PIN cleanWaterValvePin;
	private OdroidX2PIN runningWaterValvePin;
	private OdroidX2PIN wortValvePin;
	
	private OdroidX2PIN wortPompPin;
	
	private OdroidX2PIN firstMainCapacityLevel;
	private OdroidX2PIN secondMainCapacityLevel;
	private OdroidX2PIN thirdMainCapacityLevel;
	private OdroidX2PIN fourthMainCapacityLevel;
	private OdroidX2PIN firstCleanCapacityLevel;
	private OdroidX2PIN secondCleanCapacityLevel;
	private OdroidX2PIN thirdCleanCapacityLevel;
	private OdroidX2PIN fourthCleanCapacityLevel;
	

	public SettingsForm(){
		temperaturePortName = Settings.getTemperaturePortName();
		firstHeaterPin = Settings.getFirstHeaterPin();
		secondHeaterPin = Settings.getSecondHeaterPin();
		
		mainValvePin = Settings.getMainValvePin();
		cleanWaterValvePin = Settings.getCleanWaterValvePin();
		runningWaterValvePin = Settings.getRunningWaterValvePin();
		wortValvePin = Settings.getWortValvePin();
		
		wortPompPin = Settings.getWortPompPin();
		
		firstMainCapacityLevel = Settings.getFirstMainCapacityLevel();
		secondMainCapacityLevel = Settings.getSecondMainCapacityLevel();
		thirdMainCapacityLevel = Settings.getThirdMainCapacityLevel();
		fourthMainCapacityLevel = Settings.getFourthMainCapacityLevel();
		firstCleanCapacityLevel = Settings.getFirstCleanCapacityLevel();
		secondCleanCapacityLevel = Settings.getSecondCleanCapacityLevel();
		thirdCleanCapacityLevel = Settings.getThirdCleanCapacityLevel();
		fourthCleanCapacityLevel = Settings.getFourthCleanCapacityLevel();
	}
	
	public OdroidX2PIN getRunningWaterValvePin() {
		return runningWaterValvePin;
	}

	public void setRunningWaterValvePin(OdroidX2PIN runningWaterValvePin) {
		this.runningWaterValvePin = runningWaterValvePin;
		Log.rootLogger.debug("Set runningWaterValvePin ");
	}

	public OdroidX2PIN getWortPompPin() {
		return wortPompPin;
	}

	public void setWortPompPin(OdroidX2PIN wortPompPin) {
		this.wortPompPin = wortPompPin;
	}

	public OdroidX2PIN getFirstMainCapacityLevel() {
		return firstMainCapacityLevel;
	}

	public void setFirstMainCapacityLevel(OdroidX2PIN firstMainCapacityLevel) {
		this.firstMainCapacityLevel = firstMainCapacityLevel;
	}

	public OdroidX2PIN getSecondMainCapacityLevel() {
		return secondMainCapacityLevel;
	}

	public void setSecondMainCapacityLevel(OdroidX2PIN secondMainCapacityLevel) {
		this.secondMainCapacityLevel = secondMainCapacityLevel;
	}

	public OdroidX2PIN getThirdMainCapacityLevel() {
		return thirdMainCapacityLevel;
	}

	public void setThirdMainCapacityLevel(OdroidX2PIN thirdMainCapacityLevel) {
		this.thirdMainCapacityLevel = thirdMainCapacityLevel;
	}

	public OdroidX2PIN getFourthMainCapacityLevel() {
		return fourthMainCapacityLevel;
	}

	public void setFourthMainCapacityLevel(OdroidX2PIN fourthMainCapacityLevel) {
		this.fourthMainCapacityLevel = fourthMainCapacityLevel;
	}

	public OdroidX2PIN getFirstCleanCapacityLevel() {
		return firstCleanCapacityLevel;
	}

	public void setFirstCleanCapacityLevel(OdroidX2PIN firstCleanCapacityLevel) {
		this.firstCleanCapacityLevel = firstCleanCapacityLevel;
	}

	public OdroidX2PIN getSecondCleanCapacityLevel() {
		return secondCleanCapacityLevel;
	}

	public void setSecondCleanCapacityLevel(OdroidX2PIN secondCleanCapacityLevel) {
		this.secondCleanCapacityLevel = secondCleanCapacityLevel;
	}

	public OdroidX2PIN getThirdCleanCapacityLevel() {
		return thirdCleanCapacityLevel;
	}

	public void setThirdCleanCapacityLevel(OdroidX2PIN thirdCleanCapacityLevel) {
		this.thirdCleanCapacityLevel = thirdCleanCapacityLevel;
	}

	public OdroidX2PIN getFourthCleanCapacityLevel() {
		return fourthCleanCapacityLevel;
	}

	public void setFourthCleanCapacityLevel(OdroidX2PIN fourthCleanCapacityLevel) {
		this.fourthCleanCapacityLevel = fourthCleanCapacityLevel;
	}

	public OdroidX2PIN getFirstHeaterPin() {
		return firstHeaterPin;
	}
	public void setFirstHeaterPin(OdroidX2PIN firstHeaterPin) {
		this.firstHeaterPin = firstHeaterPin;
	}
	
	public OdroidX2PIN getSecondHeaterPin() {
		return secondHeaterPin;
	}
	
	public void setSecondHeaterPin(OdroidX2PIN secondHeaterPin) {
		this.secondHeaterPin = secondHeaterPin;
	}
	
	public OdroidX2PIN getMainValvePin() {
		return mainValvePin;
	}
	
	public void setMainValvePin(OdroidX2PIN mainValvePin) {
		this.mainValvePin = mainValvePin;
	}
	
	public OdroidX2PIN getCleanWaterValvePin() {
		return cleanWaterValvePin;
	}
	
	public void setCleanWaterValvePin(OdroidX2PIN cleanWaterValvePin) {
		this.cleanWaterValvePin = cleanWaterValvePin;
	}
	
	public OdroidX2PIN getWortValvePin() {
		return wortValvePin;
	}
	
	public void setWortValvePin(OdroidX2PIN wortValvePin) {
		this.wortValvePin = wortValvePin;
	}
	
	public String getTemperaturePortName() {
		return temperaturePortName;
	}

	public void setTemperaturePortName(String portName) {
		this.temperaturePortName = portName;
	}
}
