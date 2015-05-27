package c.Brewer;

import java.util.Calendar;
import java.util.Date;

public class ParameterHolder {


	public ParameterHolder(){
		Calendar cal = Calendar.getInstance();
		startTime = cal.getTime();
	}

	public Date startTime;
}
