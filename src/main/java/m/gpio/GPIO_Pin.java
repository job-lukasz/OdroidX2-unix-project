package m.gpio;

import m.gpio.StaticValues.OdroidX2PIN;

public class GPIO_Pin implements Comparable<GPIO_Pin>{


	public void closeAllPins() {
		OdroidX2PIN values[] = OdroidX2PIN.values();
		for(int i=0; i<values.length; i++){
			values[i].close();
		}
	}

	@Override
	public int compareTo(GPIO_Pin arg0) {
		// TODO Auto-generated method stub
		return 0;
	}	
}
