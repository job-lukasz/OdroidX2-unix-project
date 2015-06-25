package c.brewer;

import c.Log;
import c.brew.Brewing;
import m.settings.Settings;


public class Brewer implements Runnable {

	public enum BrewingState {
		Brew, Filtration, Hop, Finish
	}

	public enum StateProgress {
		Init, Main, End
	}
	public BrewingState brewState;
	public StateProgress stateProgress;
	
	private boolean isBrewing = false;
	private ParameterHolder parameters;
	private Hop hopController;
	private Filtration filtrationController;
	private Brew brewController;
	
	public Brewer(Brewing brewing){
		parameters = new ParameterHolder(brewing);
		hopController = new Hop(parameters);
		filtrationController = new Filtration(parameters);
		brewController = new Brew(parameters);
		Settings.getInstance().getFirstCleanCapacityLevel();
	}
	

	@Override
	public void run() {
		isBrewing = true;
		brewState = BrewingState.Brew;
		stateProgress = StateProgress.Init;
		while (isBrewing) {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				isBrewing = false;
				e.printStackTrace();
				break;
			}
			checkBrewingState();
		}
		parameters.destroy();
		Log.rootLogger.debug("End brewing");
	}

	private void checkBrewingState() {
		switch (brewState) {
		case Brew:
			checkBrewState();
			break;
		case Filtration:
			checkFiltrationState();
			break;
		case Hop:
			checkHopState();
			break;
		case Finish:
			isBrewing = false;
			Log.rootLogger.debug("Brew state finish");
			break;
		}
	}

	private void checkBrewState() {
		switch (stateProgress) {
		case Init:
			Log.rootLogger.debug("Brew state init");
			if (brewController.checkMinTemp()) {
				stateProgress = StateProgress.Main;
			}
			break;
		case Main:
			Log.rootLogger.debug("Brew state main");
			if (brewController.checkBrewTemperature()) {
				stateProgress = StateProgress.End;
			}
			break;
		case End:
			Log.rootLogger.debug("Brew state end");
			if (brewController.checkMashOut()) {
				stateProgress = StateProgress.Init;
				brewState = BrewingState.Filtration;
			}
			break;
		}
	}

	private void checkFiltrationState() {
		switch (stateProgress) {
		case Init:
			Log.rootLogger.debug("Filtration state init");
			if (filtrationController.checkTransfer()) {
				stateProgress = StateProgress.Main;
			}
			break;
		case Main:
			Log.rootLogger.debug("Filtration state main");
			if (filtrationController.checkFiltrationProgress()) {
				stateProgress = StateProgress.End;
			}
			break;
		case End:
			Log.rootLogger.debug("Filtration state end");
			if(filtrationController.checkPumpingProgress()){
				stateProgress = StateProgress.Init;
				brewState=BrewingState.Hop;
			}
			break;
		}
	}

	private void checkHopState() {
		switch (stateProgress) {
		case Init:
			Log.rootLogger.debug("Hop state init");
			if(hopController.checkBoiling()){
				stateProgress = StateProgress.Main;
			}
			break;
		case Main:
			Log.rootLogger.debug("Hop state main");
			if(hopController.checkHop()){
				stateProgress = StateProgress.End;
			}
			break;
		case End:
			Log.rootLogger.debug("Hop state end");
			if(hopController.checkCooling()){
				stateProgress = StateProgress.Init;
				brewState = BrewingState.Finish;
			}
			break;
		}
	}
}
