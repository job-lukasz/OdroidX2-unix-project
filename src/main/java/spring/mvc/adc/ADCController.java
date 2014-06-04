package spring.mvc.adc;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import spring.mvc.account.AccountRepository;
import spring.mvc.signup.SignupForm;

@Controller
public class ADCController {
	@Autowired
	private AccountRepository accountRepository;

	// ten kontroler powinnien być jak najmniejszy!
	@RequestMapping(value = "adc", method = RequestMethod.GET)
	public String index(Principal principal, Model model) {
		new SignupForm();
		if (principal != null) {
			model.addAttribute("name", principal.getName());
			return "ADC/adc";
		}
		return "home/homeNotSignedIn";
	}

	@RequestMapping(value = "adc", method = RequestMethod.POST)
	public String turnADC(Principal principal, Model model) {
		if (principal != null) {

		}
		return "/";
	}
}
