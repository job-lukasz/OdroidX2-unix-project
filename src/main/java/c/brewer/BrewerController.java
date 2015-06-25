package c.brewer;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import c.Log;
import c.brew.BrewRepository;
import c.brew.Brewing;

@Controller
@Secured("ROLE_USER")
public class BrewerController {

	private BrewRepository brewRepository;
//	private MaltRepository maltRepository;
//	private BreakRepository breakRepository;
//	private HopRepository hopRepository;
//	private AddonsRepository addonRepository;
//	private AddonUsingTimeRepository addonUsingTimeRepository;
//	private YeastRepository yeastRepository;
//	private static SimpleDateFormat dateParser = new SimpleDateFormat("yyyy.MM.dd");
	private static long brewID = -1;
	private static Thread brewerThread = null;
	private static Brewer brewer = null;
	
	@Autowired
	public BrewerController(BrewRepository brewRepository) {
		this.brewRepository = brewRepository;
//		this.maltRepository = maltRepository;
//		this.breakRepository = breakRepository;
//		this.hopRepository = hopRepository;
//		this.addonRepository = addonRepository;
//		this.addonUsingTimeRepository = addonUsingTimeRepository;
//		this.yeastRepository = yeastRepository;
	}

	@RequestMapping(value = "brewing", method = RequestMethod.GET)
	public String showBrew(Principal principal, Model model) {
		if (principal != null) {
			if(brewID==-1){
				return "brews/index";
			}
			Brewing brew = brewRepository.getBrewing(brewID);
			model.addAttribute("brew", brew);
			return "brewer/index";
		}
		return "home/homeNotSignedIn";
	}
	
	@RequestMapping(value = "toBrew", method = RequestMethod.GET)
	public String showBrew(Principal principal, Model model, @RequestParam Long id) {
		if (principal != null) {
			if(brewID==-1||brewerThread==null||!brewerThread.isAlive()){
				brewID=id;
				Log.rootLogger.debug("Start brewing");
				Brewing brewing = brewRepository.getBrewing(brewID);
				brewer = new Brewer(brewing);
				brewerThread = new Thread(brewer);
				brewerThread.start();
			}
			return showBrew(principal, model);
		}
		return "home/homeNotSignedIn";
	}
}
