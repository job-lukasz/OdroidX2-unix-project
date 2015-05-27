package c.brew;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import c.beerSources.AddonUsingTime;
import c.beerSources.AddonUsingTimeRepository;
import c.beerSources.Addons;
import c.beerSources.AddonsRepository;
import c.beerSources.Break;
import c.beerSources.BreakRepository;
import c.beerSources.Hop;
import c.beerSources.HopRepository;
import c.beerSources.Malt;
import c.beerSources.MaltRepository;
import c.beerSources.Yeast;
import c.beerSources.YeastRepository;

@Controller
@Secured("ROLE_USER")
public class BrewController {

	private BrewRepository brewRepository;
	private MaltRepository maltRepository;
	private BreakRepository breakRepository;
	private HopRepository hopRepository;
	private AddonsRepository addonRepository;
	private AddonUsingTimeRepository addonUsingTimeRepository;
	private YeastRepository yeastRepository;
	private static SimpleDateFormat dateParser = new SimpleDateFormat("yyyy.MM.dd");
	
	@Autowired
	public BrewController(YeastRepository yeastRepository, BrewRepository brewRepository, MaltRepository maltRepository, BreakRepository breakRepository, HopRepository hopRepository, AddonsRepository addonRepository,AddonUsingTimeRepository addonUsingTimeRepository) {
		this.brewRepository = brewRepository;
		this.maltRepository = maltRepository;
		this.breakRepository = breakRepository;
		this.hopRepository = hopRepository;
		this.addonRepository = addonRepository;
		this.addonUsingTimeRepository = addonUsingTimeRepository;
		this.yeastRepository = yeastRepository;
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
	
	@RequestMapping(value = "brews/saveMalt", method = RequestMethod.POST)
	public @ResponseBody boolean saveMalt(Principal principal, @RequestParam Long id, @RequestParam Long maltId, @RequestParam double quantity) {
		if (principal != null) {
			Malt malt = maltRepository.getMalt(maltId);
			BrewMalt brewMalt = new BrewMalt(malt,quantity);
			brewMalt.setId(id);
			brewMalt = brewRepository.save(brewMalt);
			return true;
		}
		return false;
	}
	
	@RequestMapping(value = "brews/deleteMalt", method = RequestMethod.POST)
	public @ResponseBody
	boolean deleteMalt(Principal principal, @RequestParam Long id, @RequestParam Long maltId) {
		if (principal != null) {
			Log.rootLogger.debug("Delete brewmalt - id: " + id);
			brewRepository.deleteMalt(id,maltId);
			return true;
		}
		return false;
	}
	
	@RequestMapping(value = "brews/addBreak", method = RequestMethod.POST)
	public @ResponseBody boolean addBreak(Principal principal, @RequestParam Long id, @RequestParam Long breakId, @RequestParam int startMinute, @RequestParam int duration) {
		if (principal != null) {
			Break _break = breakRepository.getBreak(breakId);
			BrewBreak brewBreak = new BrewBreak(_break, startMinute,duration);
			brewBreak = brewRepository.save(brewBreak);
			brewRepository.addBreak(id, brewBreak);
			return true;
		}
		return false;
	}
	
	@RequestMapping(value = "brews/saveBreak", method = RequestMethod.POST)
	public @ResponseBody boolean saveBreak(Principal principal, @RequestParam Long id, @RequestParam Long breakId, @RequestParam int startMinute, @RequestParam int duration ) {
		if (principal != null) {
			Break _break = breakRepository.getBreak(breakId);
			BrewBreak brewBreak = new BrewBreak(_break, startMinute,duration);
			brewBreak.setId(id);
			brewBreak = brewRepository.save(brewBreak);
			return true;
		}
		return false;
	}
	
	@RequestMapping(value = "brews/deleteBreak", method = RequestMethod.POST)
	public @ResponseBody boolean deleteBreak(Principal principal, @RequestParam Long id, @RequestParam Long breakId) {
		if (principal != null) {
			Log.rootLogger.debug("Delete brewbreak - id: " + id);
			brewRepository.deleteBreak(id,breakId);
			return true;
		}
		return false;
	}
	
	@RequestMapping(value = "brews/addHop", method = RequestMethod.POST)
	public @ResponseBody boolean addHop(Principal principal, @RequestParam Long id, @RequestParam Long hopId, @RequestParam int startMinute, @RequestParam double quantity) {
		if (principal != null) {
			Hop hop = hopRepository.getHop(hopId);
			BrewHop brewHop = new BrewHop(hop, startMinute,quantity);
			brewHop = brewRepository.save(brewHop);
			brewRepository.addHop(id, brewHop);
			return true;
		}
		return false;
	}
	
	@RequestMapping(value = "brews/saveHop", method = RequestMethod.POST)
	public @ResponseBody boolean saveHop(Principal principal, @RequestParam Long id, @RequestParam Long hopId, @RequestParam int startMinute,@RequestParam double quantity) {
		if (principal != null) {
			Hop hop = hopRepository.getHop(hopId);
			BrewHop brewHop = new BrewHop(hop, startMinute,quantity);
			brewHop.setId(id);
			brewHop = brewRepository.save(brewHop);
			return true;
		}
		return false;
	}
	
	@RequestMapping(value = "brews/deleteHop", method = RequestMethod.POST)
	public @ResponseBody boolean deleteHop(Principal principal, @RequestParam Long id, @RequestParam Long hopId) {
		if (principal != null) {
			Log.rootLogger.debug("Delete brewHop- id: " + id);
			brewRepository.deleteHop(id,hopId);
			return true;
		}
		return false;
	}
	
	@RequestMapping(value = "brews/addAddon", method = RequestMethod.POST)
	public @ResponseBody boolean addAddon(Principal principal, @RequestParam Long id, @RequestParam Long addonId, @RequestParam Long addonUsingTimeId, @RequestParam int startMinute, @RequestParam double quantity) {
		if (principal != null) {
			Addons addon = addonRepository.getAddon(addonId);
			AddonUsingTime addonUsingTime = addonUsingTimeRepository.getAddonUsingTime(addonUsingTimeId);
			BrewAddon brewAddon = new BrewAddon(addon, startMinute,quantity,addonUsingTime);
			brewAddon = brewRepository.save(brewAddon);
			brewRepository.addAddon(id,brewAddon);
			return true;
		}
		return false;
	}
	
	@RequestMapping(value = "brews/saveAddon", method = RequestMethod.POST)
	public @ResponseBody boolean saveAddon(Principal principal, @RequestParam Long id, @RequestParam Long addonId, @RequestParam Long addonUsingTimeId, @RequestParam int startMinute,@RequestParam double quantity) {
		if (principal != null) {
			Addons addon = addonRepository.getAddon(addonId);
			AddonUsingTime addonUsingTime = addonUsingTimeRepository.getAddonUsingTime(addonUsingTimeId);
			BrewAddon brewAddon = new BrewAddon(addon, startMinute,quantity,addonUsingTime);
			brewAddon.setId(id);
			brewAddon = brewRepository.save(brewAddon);
			return true;
		}
		return false;
	}
	
	@RequestMapping(value = "brews/deleteAddon", method = RequestMethod.POST)
	public @ResponseBody boolean deleteAddon(Principal principal, @RequestParam Long id, @RequestParam Long addonId) {
		if (principal != null) {
			Log.rootLogger.debug("Delete brewAddon- id: " + id);
			brewRepository.deleteAddon(id,addonId);
			return true;
		}
		return false;
	}
	
	@RequestMapping(value = "brews/editBrew", method = RequestMethod.POST)
	public @ResponseBody boolean editBrew(Principal principal, @RequestParam Long id,@RequestParam String name, @RequestParam String date, @RequestParam String type, @RequestParam double startDensity, @RequestParam double endDensity, @RequestParam String description) {
		if (principal != null) {
			Brewing brew = brewRepository.getBrewing(id);
			brew.setName(name);
			
			try {
				brew.setDate(dateParser.parse(date));
			} catch (ParseException e) {
				return false;
			}
			
			brew.setDescription(description);
			brew.setStartDensity(startDensity);
			brew.setEndDensity(endDensity);
			brew.setType(type);
			brewRepository.save(brew);
			return true;
		}
		return false;
	}
	
	@RequestMapping(value = "brews/editFermentation", method = RequestMethod.POST)
	public @ResponseBody boolean editFermentation(Principal principal, @RequestParam Long id,@RequestParam Long yeastId, @RequestParam String yeastAddDate, @RequestParam String yeastOrigin, @RequestParam double fermentationStartVolume, @RequestParam double fermantationTemperature, @RequestParam String silentFermentationDate, @RequestParam double silentFemrantationTemperature) {
		if (principal != null) {
			Brewing brew = brewRepository.getBrewing(id);
			Yeast yeast = yeastRepository.getYeast(yeastId);
			brewRepository.setYeast(id,yeast);
			brew.setYeastOrigin(yeastOrigin);
			try {
				brew.setYeastAddDate(dateParser.parse(yeastAddDate));
				brew.setSilentFermantationDate(dateParser.parse(silentFermentationDate));
			} catch (ParseException e) {
				return false;
			}
			brew.setFermantationTemperature(fermantationTemperature);
			brew.setFermentationStartVolume(fermentationStartVolume);
			brew.setSilentFemrantationTemperature(silentFemrantationTemperature);
			brewRepository.save(brew);
			return true;
		}
		return false;
	}
	
	@RequestMapping(value = "brews/editBottling", method = RequestMethod.POST)
	public @ResponseBody boolean editBottling(Principal principal, @RequestParam Long id,@RequestParam double endVolume,@RequestParam String refermentationSource, @RequestParam double refermentationSourceVolume, @RequestParam String bottlingDate){
		if (principal != null) {
			Brewing brew = brewRepository.getBrewing(id);
			brew.setEndVolume(endVolume);
			brew.setReferementationSource(refermentationSource);
			brew.setReferemntationSourceVolume(refermentationSourceVolume);
			try {
				brew.setBottlingDate(dateParser.parse(bottlingDate));
			} catch (ParseException e) {
				return false;
			}
			brewRepository.save(brew);
			return true;
		}
		return false;
	}
}
