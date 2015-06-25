package m.gpio;

import java.util.Set;
import java.util.TreeSet;

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

	//private Map<OdroidX2PIN, GPIO_Pin> pins = new HashMap<OdroidX2PIN, GPIO_Pin>();
	//private Map<OdroidX2PIN, GPIO_PWM> pwmPins = new HashMap<OdroidX2PIN, GPIO_PWM>();
	//private boolean initializedPins = false;

//	public void initPins() {
//		if (!initializedPins) {
//			Iterator<Entry<OdroidX2PIN, String>> pinsIterator = StaticValues.pinMap.entrySet().iterator();
//			while (pinsIterator.hasNext()) {
//				Entry<OdroidX2PIN, String> pin = pinsIterator.next();
//				pins.put(pin.getKey(), new GPIO_Pin(pin.getKey()));
//			}
//			initializedPins = true;
//		}
//	}

	public Set<OdroidX2PIN> getAllPinStates() {
		Set<OdroidX2PIN> tmpSet = new TreeSet<OdroidX2PIN>();
		for (OdroidX2PIN gpio_Pin : StaticValues.OdroidX2PIN.values()) {
			tmpSet.add(gpio_Pin);
		}
		return tmpSet;
	}
}
