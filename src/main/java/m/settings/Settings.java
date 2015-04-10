package m.settings;

import m.gpio.StaticValues.OdroidX2PIN;

public class Settings {
	
	private static String temperaturePortName;
	private static OdroidX2PIN firstHeaterPin;
	private static OdroidX2PIN secondHeaterPin;
	
	private static OdroidX2PIN mainValvePin;
	private static OdroidX2PIN cleanWaterValvePin;
	private static OdroidX2PIN runningWaterValvePin;
	private static OdroidX2PIN wortValvePin;
	
	private static OdroidX2PIN wortPompPin;
	
	private static OdroidX2PIN firstMainCapacityLevel;
	private static OdroidX2PIN secondMainCapacityLevel;
	private static OdroidX2PIN thirdMainCapacityLevel;
	private static OdroidX2PIN fourthMainCapacityLevel;

	private static OdroidX2PIN firstCleanCapacityLevel;
	private static OdroidX2PIN secondCleanCapacityLevel;
	private static OdroidX2PIN thirdCleanCapacityLevel;
	private static OdroidX2PIN fourthCleanCapacityLevel;
	
	public static String getTemperaturePortName() {
		return temperaturePortName;
	}
	public static void setTemperaturePortName(String temperaturePortName) {
		Settings.temperaturePortName = temperaturePortName;
	}
	public static OdroidX2PIN getFirstHeaterPin() {
		return firstHeaterPin;
	}
	public static void setFirstHeaterPin(OdroidX2PIN firstHeaterPin) {
		Settings.firstHeaterPin = firstHeaterPin;
	}
	public static OdroidX2PIN getSecondHeaterPin() {
		return secondHeaterPin;
	}
	public static void setSecondHeaterPin(OdroidX2PIN secondHeaterPin) {
		Settings.secondHeaterPin = secondHeaterPin;
	}
	public static OdroidX2PIN getMainValvePin() {
		return mainValvePin;
	}
	public static void setMainValvePin(OdroidX2PIN mainValvePin) {
		Settings.mainValvePin = mainValvePin;
	}
	public static OdroidX2PIN getCleanWaterValvePin() {
		return cleanWaterValvePin;
	}
	public static void setCleanWaterValvePin(OdroidX2PIN cleanWaterValvePin) {
		Settings.cleanWaterValvePin = cleanWaterValvePin;
	}
	public static OdroidX2PIN getRunningWaterValvePin() {
		return runningWaterValvePin;
	}
	public static void setRunningWaterValvePin(OdroidX2PIN runningWaterValvePin) {
		Settings.runningWaterValvePin = runningWaterValvePin;
	}
	public static OdroidX2PIN getWortValvePin() {
		return wortValvePin;
	}
	public static void setWortValvePin(OdroidX2PIN wortValvePin) {
		Settings.wortValvePin = wortValvePin;
	}
	public static OdroidX2PIN getWortPompPin() {
		return wortPompPin;
	}
	public static void setWortPompPin(OdroidX2PIN wortPompPin) {
		Settings.wortPompPin = wortPompPin;
	}
	public static OdroidX2PIN getFirstMainCapacityLevel() {
		return firstMainCapacityLevel;
	}
	public static void setFirstMainCapacityLevel(OdroidX2PIN firstMainCapacityLevel) {
		Settings.firstMainCapacityLevel = firstMainCapacityLevel;
	}
	public static OdroidX2PIN getSecondMainCapacityLevel() {
		return secondMainCapacityLevel;
	}
	public static void setSecondMainCapacityLevel(OdroidX2PIN secondMainCapacityLevel) {
		Settings.secondMainCapacityLevel = secondMainCapacityLevel;
	}
	public static OdroidX2PIN getThirdMainCapacityLevel() {
		return thirdMainCapacityLevel;
	}
	public static void setThirdMainCapacityLevel(OdroidX2PIN thirdMainCapacityLevel) {
		Settings.thirdMainCapacityLevel = thirdMainCapacityLevel;
	}
	public static OdroidX2PIN getFourthMainCapacityLevel() {
		return fourthMainCapacityLevel;
	}
	public static void setFourthMainCapacityLevel(OdroidX2PIN fourthMainCapacityLevel) {
		Settings.fourthMainCapacityLevel = fourthMainCapacityLevel;
	}
	public static OdroidX2PIN getFirstCleanCapacityLevel() {
		return firstCleanCapacityLevel;
	}
	public static void setFirstCleanCapacityLevel(OdroidX2PIN firstCleanCapacityLevel) {
		Settings.firstCleanCapacityLevel = firstCleanCapacityLevel;
	}
	public static OdroidX2PIN getSecondCleanCapacityLevel() {
		return secondCleanCapacityLevel;
	}
	public static void setSecondCleanCapacityLevel(OdroidX2PIN secondCleanCapacityLevel) {
		Settings.secondCleanCapacityLevel = secondCleanCapacityLevel;
	}
	public static OdroidX2PIN getThirdCleanCapacityLevel() {
		return thirdCleanCapacityLevel;
	}
	public static void setThirdCleanCapacityLevel(OdroidX2PIN thirdCleanCapacityLevel) {
		Settings.thirdCleanCapacityLevel = thirdCleanCapacityLevel;
	}
	public static OdroidX2PIN getFourthCleanCapacityLevel() {
		return fourthCleanCapacityLevel;
	}
	public static void setFourthCleanCapacityLevel(OdroidX2PIN fourthCleanCapacityLevel) {
		Settings.fourthCleanCapacityLevel = fourthCleanCapacityLevel;
	}	
}
