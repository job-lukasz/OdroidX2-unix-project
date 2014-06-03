package spring.mvc.home;

import java.security.Principal;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import spring.mvc.account.AccountRepository;
import spring.mvc.signup.SignupForm;
@Controller
public class HomeController {
	@Autowired
	private AccountRepository accountRepository;
	
	//ten kontroler powinnien być jak najmniejszy!
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Principal principal, Model model) {
		new SignupForm();
		if(principal != null){
			Set<gpio.PinState> pins = gpio.GPIO.INSTANCE.getAllPinStates();
			model.addAttribute("pinsState",pins);
			model.addAttribute("name",principal.getName());
			return "home/homeSignedIn";
		}

		return "home/homeNotSignedIn";
	}
}
