package m.settings;

public class Settings {
	private static String temperaturePortSensor;
	public static String getTemperaturePortSensor() {
		return temperaturePortSensor;
	}

	public static void setTemperaturePortSensor(String temperaturePortSensor) {
		Settings.temperaturePortSensor = temperaturePortSensor;
	}
}
