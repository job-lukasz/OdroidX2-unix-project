package m.settings;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import m.gpio.StaticValues.OdroidX2PIN;
import c.Log;

public class Settings {
	public static Settings instance = null;

	public Settings() {
	}

	public static Settings getInstance() {
		if (instance == null) {
			instance = new Settings();
		}
		return instance;
	}

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

	public static Settings readSettingsFromFile() {
		try {
			Log.rootLogger.debug("Try to read settings from xml");
			XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream("settings.xml")));
			Settings obj = (Settings) decoder.readObject();
			decoder.close();
			instance = obj;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Settings.getInstance();
	}

	public void saveSettingsToFile() {
		XMLEncoder encoder;
		try {
			Log.rootLogger.debug("Try to save settings to xml");
			encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream("settings.xml")));
			encoder.writeObject(Settings.getInstance());
			encoder.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public String getTemperaturePortName() {
		return temperaturePortName;
	}

	public void setTemperaturePortName(String temperaturePortName) {
		this.temperaturePortName = temperaturePortName;
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

	public OdroidX2PIN getRunningWaterValvePin() {
		return runningWaterValvePin;
	}

	public void setRunningWaterValvePin(OdroidX2PIN runningWaterValvePin) {
		this.runningWaterValvePin = runningWaterValvePin;
	}

	public OdroidX2PIN getWortValvePin() {
		return wortValvePin;
	}

	public void setWortValvePin(OdroidX2PIN wortValvePin) {
		this.wortValvePin = wortValvePin;
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
}
