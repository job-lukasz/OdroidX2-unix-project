package spring.mvc.dac;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import spring.mvc.account.AccountRepository;
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
			model.addAttribute("name", principal.getName());
			return "DAC/dac";
		}
		return "home/homeNotSignedIn";
	}

	@RequestMapping(value = "dac", method = RequestMethod.POST)
	public String turnDAC(Principal principal, Model model) {
		if (principal != null) {

		}
		return "/";
	}
}