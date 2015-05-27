package c.Brewer;


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
	
	public Brewer(){
		parameters = new ParameterHolder();
		hopController = new Hop(parameters);
		filtrationController = new Filtration(parameters);
		brewController = new Brew(parameters);
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
			break;
		}
	}

	private void checkBrewState() {
		switch (stateProgress) {
		case Init:
			if (brewController.checkMinTemp()) {
				stateProgress = StateProgress.Main;
			}
			;
			break;
		case Main:
			if (brewController.checkBrewTemperature()) {
				stateProgress = StateProgress.End;
			}
			break;
		case End:
			if (brewController.checkMashOut()) {
				stateProgress = StateProgress.Main;
				brewState = BrewingState.Filtration;
			}
			break;
		}
	}

	private void checkFiltrationState() {
		switch (stateProgress) {
		case Init:
			if (filtrationController.checkTransfer()) {
				stateProgress = StateProgress.Main;
			}
			break;
		case Main:
			if (filtrationController.checkFiltrationProgress()) {
				stateProgress = StateProgress.End;
			}
			break;
		case End:
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
			if(hopController.checkBoiling()){
				stateProgress = StateProgress.Main;
			}
			break;
		case Main:
			if(hopController.checkHop()){
				stateProgress = StateProgress.End;
			}
			break;
		case End:
			if(hopController.checkCooling()){
				stateProgress = StateProgress.Init;
				brewState = BrewingState.Finish;
			}
			break;
		}
	}
}
