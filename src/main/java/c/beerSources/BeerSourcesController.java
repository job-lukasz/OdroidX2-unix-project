package c.beerSources;

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
public class BeerSourcesController {

    private HopRepository hopRepository;
    private MaltRepository maltRepository;
    private AddonsRepository addonsRepository;

    @Autowired
	    public BeerSourcesController(HopRepository hopRepository, MaltRepository maltRepository, AddonsRepository addonsRepository) {
	        this.hopRepository = hopRepository;
	        this.maltRepository = maltRepository;
	        this.addonsRepository = addonsRepository;
	    }
    
	@RequestMapping(value = "sources", method = RequestMethod.GET)
	public String sources(Principal principal, Model model) {
		if (principal != null) {
			List<Hop> hops = hopRepository.getAllHops();
			List<Malt> malts = maltRepository.getAllMalts();
			List<Addons> addons = addonsRepository.getAllAddonss();
			model.addAttribute("hops", hops);
			model.addAttribute("malts", malts);
			model.addAttribute("addons",addons);
			model.addAttribute(new BeerSourcesForm());
			return "sources/index";
		}
		return "home/homeNotSignedIn";
	}
	
	@RequestMapping(value = "sources/saveMalt", method = RequestMethod.POST)
	public @ResponseBody boolean saveMalt(Principal principal, @RequestParam Long id, @RequestParam String name, @RequestParam double info, @RequestParam String description) {
		if (principal != null) {
			Log.rootLogger.debug("Save malt: id: " + id +", name: "+name+", info: "+info+", description: "+description);
			Malt malt = new Malt(name, info);
			malt.setDescription(description);
			malt.setId(id);
			maltRepository.save(malt);
			return true;
		}
		return false;
	}
	@RequestMapping(value = "sources/deleteMalt", method = RequestMethod.POST)
	public @ResponseBody boolean deleteMalt(Principal principal, @RequestParam Long id) {
		if (principal != null) {
			Log.rootLogger.debug("Delete malt: id: " + id);
			maltRepository.delete(id);
			return true;
		}
		return false;
	}
	@RequestMapping(value = "sources/saveHop", method = RequestMethod.POST)
	public @ResponseBody boolean saveHop(Principal principal, @RequestParam Long id, @RequestParam String name, @RequestParam double info, @RequestParam String description) {
		if (principal != null) {
			Log.rootLogger.debug("Save hop: id: " + id +", name: "+name+", info: "+info+", description: "+description);
			Hop hop = new Hop(name, info);
			hop.setDescription(description);
			hop.setId(id);
			hopRepository.save(hop);
			return true;
		}
		return false;
	}
	@RequestMapping(value = "sources/deleteHop", method = RequestMethod.POST)
	public @ResponseBody boolean deleteHop(Principal principal, @RequestParam Long id) {
		if (principal != null) {
			Log.rootLogger.debug("Delete hop: id: " + id);
			hopRepository.delete(id);
			return true;
		}
		return false;
	}
	@RequestMapping(value = "sources/saveAddon", method = RequestMethod.POST)
	public @ResponseBody boolean saveAddon(Principal principal, @RequestParam Long id, @RequestParam String name, @RequestParam String info, @RequestParam String description) {
		if (principal != null) {
			Log.rootLogger.debug("Save hop: id: " + id +", name: "+name+", info: "+info+", description: "+description);
			Addons addons = new Addons(name, info);
			addons.setDescription(description);
			addons.setId(id);
			addonsRepository.save(addons);
			return true;
		}
		return false;
	}
	
	@RequestMapping(value = "sources/deleteAddon", method = RequestMethod.POST)
	public @ResponseBody boolean deleteAddon(Principal principal, @RequestParam Long id) {
		if (principal != null) {
			Log.rootLogger.debug("Delete addon: id: " + id);
			addonsRepository.delete(id);
			return true;
		}
		return false;
	}
}
