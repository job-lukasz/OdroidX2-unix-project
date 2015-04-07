package spring.mvc.temperature;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import serial.drivers.DS1820;
import spring.mvc.account.AccountRepository;
import spring.mvc.signup.SignupForm;

@Controller
public class TemperatureController {
	@Autowired
	private AccountRepository accountRepository;

	// ten kontroler powinnien być jak najmniejszy!
	@RequestMapping(value = "temperature", method = RequestMethod.GET)
	public String index(Principal principal, Model model) {
		new SignupForm();
		if (principal != null) {
			DS1820 termo = new DS1820();
		    termo.InitCOM("/dev/ttyUSB0");
		    termo.setReadTime(750);
		    termo.InitBus();
			model.addAttribute("temperature", termo.GetTemperature(0));
		    termo.termitCom();
			return "Temperature/temperature";
		}
		return "home/homeNotSignedIn";
	}

//	@RequestMapping(value = "temperature", method = RequestMethod.POST)
//	public String turnDAC(Principal principal, Model model,
//			@ModelAttribute("dacForm") DACParamtersForm dacForm) {
//		if (principal != null) {
//			String[] portNames = SerialPortList.getPortNames();
//			Set<String> avaiblePorts = new HashSet<String>(Arrays.asList(portNames));
//			model.addAttribute("avaiblePorts", avaiblePorts);
//			try {
//				Serial serial = new Serial(dacForm.getPortName());
//				boolean status = serial.setDAC(dacForm.getVoltagePercent());
//				if(status){
//					model.addAttribute("message","na: "+dacForm.getPortName() + " ustawiono wartość " + String.valueOf(dacForm.getVoltagePercent()));
//				}
//				else {
//					model.addAttribute("allert", "Nie udalo sie ustawic wartosci DAC");
//				}
//			} catch (SerialPortException | SerialPortTimeoutException e) {
//				model.addAttribute("allert", e.toString());
//				return "DAC/dac";
//			}
//		}
//		return "/";
//	}
}
