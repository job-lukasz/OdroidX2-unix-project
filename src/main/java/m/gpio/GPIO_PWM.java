package m.gpio;

import m.gpio.StaticValues.OdroidX2PIN;

public class GPIO_PWM extends GPIO_Pin implements Runnable{
	private long valueLow_microS=0;
	private long valueHigh_microS=0;
	private boolean pwmActivie = false;
	
	public GPIO_PWM(long timeSpan_microS, long hightValue_microS, OdroidX2PIN pwnPin){
		super(pwnPin);
		valueLow_microS=timeSpan_microS-hightValue_microS;
		valueHigh_microS=hightValue_microS;
	}
	
	private void sleep_us(long usSleep) {
		long start = System.nanoTime() + usSleep*1000;
		long end = 0;
		do {
			end = System.nanoTime();
		} while (start >= end);
	}

	public void run() {
		if(valueLow_microS>0){
			Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
			pwmActivie=true;
			while(pwmActivie){
				setValue(true);
				sleep_us(valueHigh_microS);
				setValue(false);
				sleep_us(valueLow_microS);
			}
		}
	}
	
	public void stop(){
		pwmActivie=false;
	}
	
}
