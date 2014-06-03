package gpio;

import gpio.GpioPin.Direction;
import gpio.StaticValues.OdroidX2PIN;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.persistence.TemporalType;

import static gpio.StaticValues.*;

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

	public final static Map<OdroidX2PIN, String> pinMap;
	static {
		Map<OdroidX2PIN, String> tempMap = new HashMap<OdroidX2PIN, String>();
		tempMap.put(OdroidX2PIN.PIN1, "220");
		tempMap.put(OdroidX2PIN.PIN2, "12");
		tempMap.put(OdroidX2PIN.PIN3, "44");
		tempMap.put(OdroidX2PIN.PIN4, "209");
		tempMap.put(OdroidX2PIN.PIN5, "45");
		tempMap.put(OdroidX2PIN.PIN6, "20");
		tempMap.put(OdroidX2PIN.PIN7, "19");
		tempMap.put(OdroidX2PIN.PIN8, "18");
		tempMap.put(OdroidX2PIN.PIN9, "203");
		tempMap.put(OdroidX2PIN.PIN10, "21");
		tempMap.put(OdroidX2PIN.PIN11, "23");
		tempMap.put(OdroidX2PIN.PIN13, "38");
		tempMap.put(OdroidX2PIN.PIN14, "13");
		tempMap.put(OdroidX2PIN.PIN15, "22");
		tempMap.put(OdroidX2PIN.PIN16, "10");
		tempMap.put(OdroidX2PIN.PIN17, "112");
		tempMap.put(OdroidX2PIN.PIN18, "115");
		tempMap.put(OdroidX2PIN.PIN19, "93");
		tempMap.put(OdroidX2PIN.PIN20, "100");
		tempMap.put(OdroidX2PIN.PIN21, "108");
		tempMap.put(OdroidX2PIN.PIN22, "91");
		tempMap.put(OdroidX2PIN.PIN23, "90");
		tempMap.put(OdroidX2PIN.PIN24, "99");
		tempMap.put(OdroidX2PIN.PIN25, "111");
		tempMap.put(OdroidX2PIN.PIN26, "103");
		tempMap.put(OdroidX2PIN.PIN27, "88");
		tempMap.put(OdroidX2PIN.PIN28, "98");
		tempMap.put(OdroidX2PIN.PIN29, "89");
		tempMap.put(OdroidX2PIN.PIN30, "114");
		tempMap.put(OdroidX2PIN.PIN31, "87");
		tempMap.put(OdroidX2PIN.PIN33, "94");
		tempMap.put(OdroidX2PIN.PIN34, "105");
		tempMap.put(OdroidX2PIN.PIN35, "97");
		tempMap.put(OdroidX2PIN.PIN36, "102");
		tempMap.put(OdroidX2PIN.PIN37, "107");
		tempMap.put(OdroidX2PIN.PIN38, "110");
		tempMap.put(OdroidX2PIN.PIN39, "101");
		tempMap.put(OdroidX2PIN.PIN40, "117");
		tempMap.put(OdroidX2PIN.PIN41, "92");
		tempMap.put(OdroidX2PIN.PIN42, "96");
		tempMap.put(OdroidX2PIN.PIN43, "116");
		tempMap.put(OdroidX2PIN.PIN44, "106");
		tempMap.put(OdroidX2PIN.PIN45, "109");
		pinMap = Collections.unmodifiableMap(tempMap);
	}
	private Map<OdroidX2PIN, GpioPin> openedPins = new HashMap<OdroidX2PIN, GpioPin>();
	private Map<OdroidX2PIN, GPIO_PWM> pwmPins = new HashMap<OdroidX2PIN, GPIO_PWM>();

	public class PinState {
		public OdroidX2PIN pinID;
		public GpioPin.Direction direction;
		public boolean value;
		public boolean isSoftPWN;
		public boolean isOpen;
	};

	public Set<PinState> getAllPinStates() {
		Set<PinState> tmpSet = new HashSet<PinState>();
		Iterator<Entry<OdroidX2PIN, String>> pinsIterator = pinMap.entrySet()
				.iterator();
		while (pinsIterator.hasNext()) {
			Entry<OdroidX2PIN, String> pinName = pinsIterator.next();
			PinState tmpState = new PinState();
			if (openedPins.containsKey(pinName.getKey())) {
				tmpState.isOpen = true;
				GpioPin tempPin = openedPins.get(pinName.getKey());
				tmpState.direction = tempPin.getDir();
				tmpState.value = tempPin.getValue();
				if (pwmPins.containsKey(pinName.getKey())) {
					tmpState.isSoftPWN = true;
				} else {
					tmpState.isSoftPWN = false;
				}
				tmpState.pinID = pinName.getKey();
			} else {
				tmpState.direction = Direction.out;
				tmpState.value = false;
				tmpState.isSoftPWN = false;
				tmpState.isOpen = false;
				tmpState.pinID = pinName.getKey();
			}
			tmpSet.add(tmpState);
		}
		return tmpSet;
	}

	public void setHigh(OdroidX2PIN odroidPin) {
		openPin(odroidPin);
		openedPins.get(odroidPin).setValue(true);
	}

	public void setLow(OdroidX2PIN odroidPin) {
		openPin(odroidPin);
		openedPins.get(odroidPin).setValue(false);
	}

	public void setPWM(OdroidX2PIN odroidPin, long high_microS, long freq_microS) {
		openPin(odroidPin);
		pwmPins.put(odroidPin, new GPIO_PWM(freq_microS, high_microS,
				openedPins.get(odroidPin)));
		Thread pwmThread = new Thread(pwmPins.get(odroidPin));
		pwmThread.start();
	}

	public void closeAllPins() {
		Iterator<Entry<OdroidX2PIN, GPIO_PWM>> iterator = pwmPins.entrySet()
				.iterator();
		while (iterator.hasNext()) {
			Entry<OdroidX2PIN, GPIO_PWM> temp = iterator.next();
			temp.getValue().stop();
		}
		Iterator<Entry<OdroidX2PIN, GpioPin>> pinsIterator = openedPins
				.entrySet().iterator();
		while (pinsIterator.hasNext()) {
			Entry<OdroidX2PIN, GpioPin> temp = pinsIterator.next();
			temp.getValue().close();
		}
		pwmPins.clear();
		openedPins.clear();
	}

	private void openPin(OdroidX2PIN odroidPin) {
		if (!openedPins.containsKey(odroidPin)) {
			openedPins.put(odroidPin, new GpioPin(pinMap.get(odroidPin)));
			openedPins.get(odroidPin).open(GpioPin.Direction.out);
		} else if (openedPins.get(odroidPin).getDir() != GpioPin.Direction.out) {
			if (pwmPins.containsKey(odroidPin)) {
				pwmPins.get(odroidPin).stop();
				pwmPins.remove(odroidPin);
				System.out.println("zamykam");
			}
			openedPins.get(odroidPin).close();
			openedPins.get(odroidPin).open(GpioPin.Direction.out);
		}
	}
}
