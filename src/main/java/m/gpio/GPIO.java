package m.gpio;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import m.gpio.StaticValues.Direction;
import m.gpio.StaticValues.OdroidX2PIN;

/*
 __________
 |1		2 |
 |3		4 |
 |5		6 |	
 |7		8 |
 |9		10|
 |11		12|
 |13		14|
 |15		16|
 |17*   *18|
 |19*   *20|
 |21*   *22|
 23*   *24|
 25*   *26|
 27*   *28|
 29*   *30|
 |31*	32|
 |33*   *34|
 |35*   *36|
 |37*   *38|
 |39*   *40|
 |41*   *42|
 |43*   *44|
 |45*	46|
 |47 	48|
 |49		50|
 ___________
 */
//GPIO Singleton

public enum GPIO{
	INSTANCE;

	private Map<OdroidX2PIN, GPIO_Pin> pins = new HashMap<OdroidX2PIN, GPIO_Pin>();
	private Map<OdroidX2PIN, GPIO_PWM> pwmPins = new HashMap<OdroidX2PIN, GPIO_PWM>();
	private boolean initializedPins = false;

	public void initPins() {
		if (!initializedPins) {
			Iterator<Entry<OdroidX2PIN, String>> pinsIterator = StaticValues.pinMap.entrySet().iterator();
			while (pinsIterator.hasNext()) {
				Entry<OdroidX2PIN, String> pin = pinsIterator.next();
				pins.put(pin.getKey(), new GPIO_Pin(pin.getKey()));
			}
			initializedPins = true;
		}
	}

	public Set<GPIO_Pin> getAllPinStates() {
		Set<GPIO_Pin> tmpSet = new TreeSet<GPIO_Pin>();
		
		Iterator<Entry<OdroidX2PIN, GPIO_Pin>> pinsIterator = pins.entrySet().iterator();
		while (pinsIterator.hasNext()) {
			Entry<OdroidX2PIN, GPIO_Pin> pin = pinsIterator.next();
			tmpSet.add(pin.getValue());
		}
		return tmpSet;
	}

	public void setHigh(OdroidX2PIN odroidPin) {
		openPin(odroidPin);
		pins.get(odroidPin).setValue(true);
	}

	public void setLow(OdroidX2PIN odroidPin) {
		openPin(odroidPin);
		pins.get(odroidPin).setValue(false);
	}
	
	public boolean toggle(OdroidX2PIN odroidPin){
		openPin(odroidPin);
		GPIO_Pin pin = pins.get(odroidPin);
		pin.setValue(!pin.getValue());
		return pin.getValue();
	}
	
	public void setPWM(OdroidX2PIN odroidPin, long high_microS, long freq_microS) {
		openPin(odroidPin);
		if (pwmPins.containsKey(odroidPin)) {
			pwmPins.get(odroidPin).stop();
			pwmPins.remove(odroidPin);
		}
		pwmPins.put(odroidPin, new GPIO_PWM(freq_microS, high_microS, odroidPin));
		Thread pwmThread = new Thread(pwmPins.get(odroidPin));
		pwmThread.start();
	}

	public void closeAllPins() {
		closeAllPWMPins();
		Iterator<Entry<OdroidX2PIN, GPIO_Pin>> pinsIterator = pins.entrySet().iterator();
		while (pinsIterator.hasNext()) {
			Entry<OdroidX2PIN, GPIO_Pin> temp = pinsIterator.next();
			if (temp.getValue().isOpen()) {
				temp.getValue().close();
			}
		}
		pins.clear();
	}

	private void closeAllPWMPins() {
		Iterator<Entry<OdroidX2PIN, GPIO_PWM>> iterator = pwmPins.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<OdroidX2PIN, GPIO_PWM> temp = iterator.next();
			temp.getValue().stop();
		}
		pwmPins.clear();
	}
//TODO READ FROM PIN
	private void openPin(OdroidX2PIN odroidPin) {
		if (pins.containsKey(odroidPin)) {
			if (pins.get(odroidPin).isOpen()) {
				if (pins.get(odroidPin).getDirection() != Direction.out) {
					pins.get(odroidPin).close();
					pins.get(odroidPin).open(Direction.out);
				}
				if (pwmPins.containsKey(odroidPin)) {
					pwmPins.get(odroidPin).stop();
					pwmPins.remove(odroidPin);
				}
			} else {
				pins.get(odroidPin).open(Direction.out);
			}

		}

	}

}
