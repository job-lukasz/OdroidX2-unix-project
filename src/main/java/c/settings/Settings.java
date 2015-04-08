package c.settings;

import java.security.Principal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import jssc.SerialPortException;
import jssc.SerialPortList;
import jssc.SerialPortTimeoutException;
import m.serial.Serial;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import c.account.AccountRepository;
import c.measurement.Measurement;
import c.measurement.MeasurementRepository;
import c.signup.SignupForm;

@Controller
public class Settings {
	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private MeasurementRepository measureRepository;
	
	// ten kontroler powinnien być jak najmniejszy!
	@RequestMapping(value = "settings", method = RequestMethod.GET)
	public String index(Principal principal, Model model) {
		if (principal != null) {
			String[] portNames = SerialPortList.getPortNames();
			Set<String> avaiblePorts = new HashSet<String>(Arrays.asList(portNames)); 
			model.addAttribute("avaiblePorts", avaiblePorts);
			model.addAttribute("name", principal.getName());
			model.addAttribute("adcForm", new SettingsForm());
			return "Settings/settings";
		}
		return "home/homeNotSignedIn";
	}

	@RequestMapping(value = "settings", method = RequestMethod.POST)
	public String saveSettings(Principal principal, Model model,@ModelAttribute("settingsForm") SettingsForm settingsForm) {
		if (principal != null) {
			String[] portNames = SerialPortList.getPortNames();
			Set<String> avaiblePorts = new HashSet<String>(Arrays.asList(portNames));
			model.addAttribute("avaiblePorts", avaiblePorts);
				//byte[] buffer;
				//Serial serial = new Serial(adcForm.getPortName());
				//buffer = serial.startMeasure(adcForm.getBytesNumber());
				//Measurement measure = new Measurement(buffer, accountRepository.findByEmail(principal.getName()));
				//measureRepository.save(measure);
			return "settings/save";
		}
		return "home/homeNotSignedIn";
	}
}
