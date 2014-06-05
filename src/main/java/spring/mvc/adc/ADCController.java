package spring.mvc.adc;

import java.security.Principal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import serial.Serial;
import serial.jssc.SerialPortException;
import serial.jssc.SerialPortList;
import spring.mvc.account.AccountRepository;
import spring.mvc.measurement.Measurement;
import spring.mvc.measurement.MeasurementRepository;
import spring.mvc.signup.SignupForm;

@Controller
public class ADCController {
	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private MeasurementRepository measureRepository;
	
	// ten kontroler powinnien być jak najmniejszy!
	@RequestMapping(value = "adc", method = RequestMethod.GET)
	public String index(Principal principal, Model model) {
		new SignupForm();
		if (principal != null) {
			String[] portNames = SerialPortList.getPortNames();
			Set<String> avaiblePorts = new HashSet<String>(Arrays.asList(portNames)); 
			model.addAttribute("avaiblePorts", avaiblePorts);
			model.addAttribute("name", principal.getName());
			model.addAttribute("adcForm", new ADCParamtersForm());
			return "ADC/adc";
		}
		return "home/homeNotSignedIn";
	}

	@RequestMapping(value = "adc", method = RequestMethod.POST)
	public String turnADC(Principal principal, Model model,@ModelAttribute("adcForm") ADCParamtersForm adcForm) {
		if (principal != null) {
			try {
				byte[] buffer;
				Serial serial = new Serial(adcForm.getPortName());
				buffer = serial.startMeasure(adcForm.getBytesNumber());
				Measurement measure = new Measurement(buffer, accountRepository.findByEmail(principal.getName()));
			    measureRepository.save(measure);
			} catch (SerialPortException e) {
				model.addAttribute("allert",e.toString());
				return "ADC/adc";
			}
			return "ADC/measureAdd";
		    
		}
		return "home/homeNotSignedIn";
	}
}
