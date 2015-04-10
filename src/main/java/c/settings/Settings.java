package c.settings;

import java.security.Principal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import jssc.SerialPortList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import c.Log;
import c.account.AccountRepository;
import c.measurement.MeasurementRepository;

@Controller
public class Settings {
	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private MeasurementRepository measureRepository;
	private Set<String> getAvailblePort(){
		String[] portNames = SerialPortList.getPortNames();
		return new HashSet<String>(Arrays.asList(portNames));
	}
	
	@RequestMapping(value = "settings", method = RequestMethod.GET)
	public String index(Principal principal, Model model) {
		if (principal != null) {
			getSettingsModel(principal, model);
			return "Settings/settings";
		}
		return "home/homeNotSignedIn";
	}

	@RequestMapping(value = "settings", method = RequestMethod.POST)
	public String saveSettings(Principal principal, Model model,@ModelAttribute("settingsForm") SettingsForm settingsForm) {
		if (principal != null) {
			
			m.settings.Settings.setTemperaturePortName(settingsForm.getTemperaturePortName());
			m.settings.Settings.setFirstHeaterPin(settingsForm.getFirstHeaterPin());
			m.settings.Settings.setSecondHeaterPin(settingsForm.getSecondHeaterPin());
			m.settings.Settings.setCleanWaterValvePin(settingsForm.getCleanWaterValvePin());
			m.settings.Settings.setMainValvePin(settingsForm.getMainValvePin());
			m.settings.Settings.setRunningWaterValvePin(settingsForm.getRunningWaterValvePin());
			m.settings.Settings.setWortPompPin(settingsForm.getWortPompPin());
			m.settings.Settings.setWortValvePin(settingsForm.getWortValvePin());
			m.settings.Settings.setFirstCleanCapacityLevel(settingsForm.getFirstCleanCapacityLevel());
			m.settings.Settings.setSecondCleanCapacityLevel(settingsForm.getSecondCleanCapacityLevel());
			m.settings.Settings.setThirdCleanCapacityLevel(settingsForm.getThirdCleanCapacityLevel());
			m.settings.Settings.setFourthCleanCapacityLevel(settingsForm.getFourthCleanCapacityLevel());
			m.settings.Settings.setFirstMainCapacityLevel(settingsForm.getFirstMainCapacityLevel());
			m.settings.Settings.setSecondMainCapacityLevel(settingsForm.getSecondMainCapacityLevel());
			m.settings.Settings.setThirdMainCapacityLevel(settingsForm.getThirdMainCapacityLevel());
			m.settings.Settings.setFourthMainCapacityLevel(settingsForm.getFourthMainCapacityLevel());
			
			Log.rootLogger.debug("Set temperature port name: "+settingsForm.getTemperaturePortName());
			getSettingsModel(principal, model);
			return "Settings/settings";
		}
		return "home/homeNotSignedIn";
	}
	
	private void getSettingsModel(Principal principal, Model model) {
		model.addAttribute("avaiblePorts", getAvailblePort());
		model.addAttribute("name", principal.getName());
		model.addAttribute("settingsForm", new SettingsForm());
		Set<m.gpio.PinState> pins = m.gpio.GPIO.INSTANCE.getAllPinStates();
		model.addAttribute("pins", pins);
	}
}
