package m.gpio;

import java.util.Comparator;
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

public enum GPIO {
	INSTANCE;

	private Map<OdroidX2PIN, GpioPin> pins = new HashMap<OdroidX2PIN, GpioPin>();
	private Map<OdroidX2PIN, GPIO_PWM> pwmPins = new HashMap<OdroidX2PIN, GPIO_PWM>();
	private boolean initializedPins = false;

	public void initPins() {
		if (!initializedPins) {
			Iterator<Entry<OdroidX2PIN, String>> pinsIterator = StaticValues.pinMap.entrySet().iterator();
			while (pinsIterator.hasNext()) {
				Entry<OdroidX2PIN, String> pin = pinsIterator.next();
				pins.put(pin.getKey(), new GpioPin(pin.getKey()));
			}
			initializedPins = true;
		}
	}

	public Set<PinState> getAllPinStates() {
		Set<PinState> tmpSet = new TreeSet<PinState>(new Comparator<PinState>() {
			@Override
			public int compare(PinState o1, PinState o2) {
				if (Integer.parseInt(o1.getPinID().toString().substring(3)) < Integer.parseInt(o2.getPinID().toString().substring(3))) {
					return -1;
				}
				else if (Integer.parseInt(o1.getPinID().toString().substring(3)) > Integer.parseInt(o2.getPinID().toString().substring(3))){
					return 1;
				}
				return 0;
			}
		});
		Iterator<Entry<OdroidX2PIN, GpioPin>> pinsIterator = pins.entrySet().iterator();
		while (pinsIterator.hasNext()) {
			Entry<OdroidX2PIN, GpioPin> pin = pinsIterator.next();
			tmpSet.add(pin.getValue().pinState);
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
		GpioPin pin = pins.get(odroidPin);
		pin.setValue(!pin.getValue());
		return pin.getValue();
	}
	public void setPWM(OdroidX2PIN odroidPin, long high_microS, long freq_microS) {
		openPin(odroidPin);
		if (pwmPins.containsKey(odroidPin)) {
			pwmPins.get(odroidPin).stop();
			pwmPins.remove(odroidPin);
		}
		pwmPins.put(odroidPin, new GPIO_PWM(freq_microS, high_microS, pins.get(odroidPin)));
		Thread pwmThread = new Thread(pwmPins.get(odroidPin));
		pwmThread.start();
	}

	public void closeAllPins() {
		closeAllPWMPins();
		Iterator<Entry<OdroidX2PIN, GpioPin>> pinsIterator = pins.entrySet().iterator();
		while (pinsIterator.hasNext()) {
			Entry<OdroidX2PIN, GpioPin> temp = pinsIterator.next();
			if (temp.getValue().pinState.getOpen()) {
				temp.getValue().close();
			}
		}
		pwmPins.clear();
	}

	private void closeAllPWMPins() {
		Iterator<Entry<OdroidX2PIN, GPIO_PWM>> iterator = pwmPins.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<OdroidX2PIN, GPIO_PWM> temp = iterator.next();
			temp.getValue().stop();
		}
		pins.clear();
	}

	private void openPin(OdroidX2PIN odroidPin) {
		if (pins.containsKey(odroidPin)) {
			if (pins.get(odroidPin).pinState.getOpen()) {
				if (pins.get(odroidPin).pinState.getDirection() != Direction.out) {
					pins.get(odroidPin).close();
					pins.get(odroidPin).open(Direction.out);
				}
				if (pwmPins.containsKey(odroidPin)) {
					pwmPins.get(odroidPin).stop();
					pwmPins.remove(odroidPin);
					System.out.println("zamykam");
				}
			} else {
				pins.get(odroidPin).open(Direction.out);
			}

		}

	}

}