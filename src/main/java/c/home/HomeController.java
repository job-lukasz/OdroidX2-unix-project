package c.home;

import java.security.Principal;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import c.account.AccountRepository;
import c.signup.SignupForm;
@Controller
public class HomeController {
	@Autowired
	private AccountRepository accountRepository;
	
	//ten kontroler powinnien być jak najmniejszy!
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Principal principal, Model model) {
		new SignupForm();
		if(principal != null){
			Set<m.gpio.PinState> pins = m.gpio.GPIO.INSTANCE.getAllPinStates();
			model.addAttribute("pins",pins);
			model.addAttribute("name",principal.getName());
			return "home/homeSignedIn";
		}

		return "home/homeNotSignedIn";
	}
}
