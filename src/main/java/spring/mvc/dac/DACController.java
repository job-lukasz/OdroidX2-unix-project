package spring.mvc.dac;

import java.security.Principal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import jssc.SerialPortException;
import jssc.SerialPortList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import serial.Serial;
import spring.mvc.account.AccountRepository;
import spring.mvc.adc.ADCParamtersForm;
import spring.mvc.measurement.Measurement;
import spring.mvc.signup.SignupForm;

@Controller
public class DACController {
	@Autowired
	private AccountRepository accountRepository;

	// ten kontroler powinnien być jak najmniejszy!
	@RequestMapping(value = "dac", method = RequestMethod.GET)
	public String index(Principal principal, Model model) {
		new SignupForm();
		if (principal != null) {
			String[] portNames = SerialPortList.getPortNames();
			Set<String> avaiblePorts = new HashSet<String>(Arrays.asList(portNames)); 
			model.addAttribute("avaiblePorts", avaiblePorts);
			model.addAttribute("name", principal.getName());
			model.addAttribute("dacForm", new DACParamtersForm());
			return "DAC/dac";
		}
		return "home/homeNotSignedIn";
	}

	@RequestMapping(value = "dac", method = RequestMethod.POST)
	public String turnDAC(Principal principal, Model model,@ModelAttribute("dacForm") DACParamtersForm dacForm) {
		if (principal != null) {
			try {
				byte[] buffer;
				Serial serial = new Serial(dacForm.getPortName());
				buffer = serial.startMeasure(dacForm.getBytesNumber());
				Measurement measure = new Measurement(buffer, accountRepository.findByEmail(principal.getName()));
			    measureRepository.save(measure);
			} catch (SerialPortException e) {
				model.addAttribute("allert",e.toString());
				return "ADC/adc";
			}
		}
		return "/";
	}
}
