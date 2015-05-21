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
import c.beerSources.Malt;
import c.beerSources.MaltRepository;

@Controller
@Secured("ROLE_USER")
public class BrewController {

	private BrewRepository brewRepository;
	private MaltRepository maltRepository;
	
	@Autowired
	public BrewController(BrewRepository brewRepository, MaltRepository maltRepository) {
		this.brewRepository = brewRepository;
		this.maltRepository = maltRepository;
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

	@RequestMapping(value = "brews/deleteBrew", method = RequestMethod.POST)
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
			Brewing brew = new Brewing(name,type,description);
			brew.setBrewingId(id);
			Log.rootLogger.debug("Save brewing: id: " + id +" name: "+ name + " type: "+ type + " description: "+ description);
			brewRepository.save(brew);
			return true;
		}
		return false;
	}
	
	@RequestMapping(value = "brews/details", method = RequestMethod.GET)
	public String showBrew(Principal principal, Model model, @RequestParam Long id) {
		if (principal != null) {
			Brewing brew = brewRepository.getBrewing(id);
			model.addAttribute("brew", brew);
			return "brews/details";
		}
		return "home/homeNotSignedIn";
	}
	
	@RequestMapping(value = "brews/addMalt", method = RequestMethod.POST)
	public @ResponseBody boolean addMalt(Principal principal, @RequestParam Long id, @RequestParam Long maltId, @RequestParam double quantity) {
		if (principal != null) {
			Malt malt = maltRepository.getMalt(maltId);
			BrewMalt brewMalt = new BrewMalt(malt,quantity);
			brewMalt = brewRepository.save(brewMalt);
			brewRepository.addMalt(id, brewMalt);
			return true;
		}
		return false;
	}
}
