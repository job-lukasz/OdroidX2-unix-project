package c.settings;

public class SettingsForm {

	private static String temperaturePortName;
	
	public static String getTemperaturePortName() {
		return temperaturePortName;
	}

	public static void setTemperaturePortName(String portName) {
		temperaturePortName = portName;
	}
}
