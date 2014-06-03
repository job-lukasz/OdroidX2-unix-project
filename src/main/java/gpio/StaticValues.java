package gpio;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class StaticValues{
	public static enum OdroidX2PIN{PIN17, PIN18, 
		 PIN19, PIN20,
		 PIN21, PIN22,
		 PIN23, PIN24,
		 PIN25, PIN26,
		 PIN27, PIN28,
		 PIN29, PIN30,
		 PIN31,
		 PIN33, PIN34,
		 PIN35, PIN36,
		 PIN37, PIN38,
		 PIN39, PIN40,
		 PIN41, PIN42,
		 PIN43, PIN44,
		 PIN45};
	public static enum Direction {
			in, out
		}
	
	public final static Map<OdroidX2PIN, String> pinMap;
	static {
		Map<OdroidX2PIN, String> tempMap = new HashMap<OdroidX2PIN, String>();
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
}
