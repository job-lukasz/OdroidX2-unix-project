package c.brew;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import c.Log;

@Controller
@Secured("ROLE_USER")
public class BrewController {

	private BrewRepository brewRepository;

	@Autowired
	public BrewController(BrewRepository brewRepository) {
		this.brewRepository = brewRepository;
	}

	@RequestMapping(value = "brews", method = RequestMethod.GET)
	public String sources(Principal principal, Model model) {
		if (principal != null) {
			List<Brewing> brews = brewRepository.getAllBrewings();
			model.addAttribute("brews", brews);
			return "brews/index";
		}
		return "home/homeNotSignedIn";
	}

	@RequestMapping(value = "brews/deleteBrewing", method = RequestMethod.POST)
	public @ResponseBody boolean deleteBrew(Principal principal, @RequestParam Long id) {
		if (principal != null) {
			Log.rootLogger.debug("Delete brewing: id: " + id);
			brewRepository.deleteBrewing(id);
			return true;
		}
		return false;
	}
	@RequestMapping(value = "brews/saveBrew", method = RequestMethod.POST)
	public @ResponseBody boolean saveBrew(Principal principal, @RequestParam Long id, @RequestParam String name, @RequestParam String type, @RequestParam String description) {
		if (principal != null) {
			Log.rootLogger.debug("Delete brewing: id: " + id);
			brewRepository.deleteBrewing(id);
			return true;
		}
		return false;
	}
}
