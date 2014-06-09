package spring.mvc.leds;

import java.security.Principal;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import spring.mvc.account.AccountRepository;
import spring.mvc.signup.SignupForm;

@Controller
public class LedsController {
	@Autowired
	private AccountRepository accountRepository;

	// ten kontroler powinnien być jak najmniejszy!
	@RequestMapping(value = "leds", method = RequestMethod.GET)
	public String index(Principal principal, Model model) {
		new SignupForm();
		if (principal != null) {
			Set<gpio.PinState> pins = gpio.GPIO.INSTANCE.getAllPinStates();
			model.addAttribute("pins", pins);
			model.addAttribute("ledsToggleForm", new LedsToggleForm());
			model.addAttribute("name", principal.getName());
			return "leds/leds";
		}

		return "home/homeNotSignedIn";
	}

	@RequestMapping(value = "leds", method = RequestMethod.POST)
	public String toggleLeds(Principal principal, Model model, @ModelAttribute("ledsToggleForm") LedsToggleForm ledsForm) {
		if (principal != null) {
			if(ledsForm.getPinValue()){
				gpio.GPIO.INSTANCE.setHigh(ledsForm.getPinID());
			}
			else{
				gpio.GPIO.INSTANCE.setLow(ledsForm.getPinID());	
			}
			model.addAttribute("message","na pinie: " +ledsForm.getPinID().toString() + " ustawiono wartość " + !ledsForm.getPinValue());
			Set<gpio.PinState> pins = gpio.GPIO.INSTANCE.getAllPinStates();
			model.addAttribute("pins", pins);
			model.addAttribute("ledsToggleForm", new LedsToggleForm());
			model.addAttribute("name", principal.getName());
			return "leds/leds";
		}
		return "/";
	}
}
