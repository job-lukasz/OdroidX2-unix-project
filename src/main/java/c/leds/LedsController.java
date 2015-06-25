package c.leds;

import java.security.Principal;
import java.util.Set;

import m.gpio.StaticValues.OdroidX2PIN;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import c.account.AccountRepository;
import c.signup.SignupForm;

@Controller
public class LedsController {
	@Autowired
	private AccountRepository accountRepository;

	// ten kontroler powinnien być jak najmniejszy!
	@RequestMapping(value = "leds", method = RequestMethod.GET)
	public String index(Principal principal, Model model) {
		new SignupForm();
		if (principal != null) {
			Set<OdroidX2PIN> pins = m.gpio.GPIO.INSTANCE.getAllPinStates();
			model.addAttribute("pins", pins);
			model.addAttribute("ledsToggleForm", new LedsToggleForm());
			model.addAttribute("name", principal.getName());
			return "leds/leds";
		}

		return "home/homeNotSignedIn";
	}

//	@RequestMapping(value = "leds", method = RequestMethod.POST)
//	public String toggleLeds(Principal principal, Model model, @ModelAttribute("ledsToggleForm") LedsToggleForm ledsForm) {
//		if (principal != null) {
//			if(ledsForm.getPinValue()){
//				gpio.GPIO.INSTANCE.setHigh(ledsForm.getPinID());
//			}
//			else{
//				gpio.GPIO.INSTANCE.setLow(ledsForm.getPinID());	
//			}
//			model.addAttribute("message","na pinie: " +ledsForm.getPinID().toString() + " ustawiono wartość " + ledsForm.getPinValue());
//			Set<gpio.PinState> pins = gpio.GPIO.INSTANCE.getAllPinStates();
//			model.addAttribute("pins", pins);
//			model.addAttribute("ledsToggleForm", new LedsToggleForm());
//			model.addAttribute("name", principal.getName());
//			return "leds/leds";
//		}
//		return "/";
//	}
	
	@RequestMapping(value = "togglePin", method = RequestMethod.POST)
	public @ResponseBody boolean toggleLed(Principal principal, @RequestParam OdroidX2PIN pinID) {
		if (principal != null) {
			boolean value = pinID.toggle();
			return value;
		}
		return false;
	}
}
