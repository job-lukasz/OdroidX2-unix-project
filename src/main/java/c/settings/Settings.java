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

import c.account.AccountRepository;
import c.config.ApplicationConfig;
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
			model.addAttribute("avaiblePorts", getAvailblePort());
			model.addAttribute("name", principal.getName());
			model.addAttribute("settingsForm", new SettingsForm());
			return "Settings/settings";
		}
		return "home/homeNotSignedIn";
	}

	@RequestMapping(value = "settings", method = RequestMethod.POST)
	public String saveSettings(Principal principal, Model model,@ModelAttribute("settingsForm") SettingsForm settingsForm) {
		if (principal != null) {
			m.settings.Settings.setTemperaturePortSensor(settingsForm.getTemperaturePortName());
			ApplicationConfig.rootLogger.debug("Set temperature port name: "+settingsForm.getTemperaturePortName());
			model.addAttribute("avaiblePorts", getAvailblePort());
			model.addAttribute("name", principal.getName());
			model.addAttribute("settingsForm", new SettingsForm());
			return "Settings/settings";
		}
		return "home/homeNotSignedIn";
	}
}
