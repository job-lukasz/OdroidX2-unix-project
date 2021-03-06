package m.gpio;

import m.gpio.StaticValues.OdroidX2PIN;

public class GPIO_PWM  implements Runnable{
	private long valueLow_microS=0;
	private long valueHigh_microS=0;
	private boolean pwmActivie = false;
	private OdroidX2PIN pwmPin;
	
	public GPIO_PWM(long timeSpan_microS, long hightValue_microS, OdroidX2PIN pwnPin){
		valueLow_microS=timeSpan_microS-hightValue_microS;
		valueHigh_microS=hightValue_microS;
		this.pwmPin = pwnPin;
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
				pwmPin.setValue(true);
				sleep_us(valueHigh_microS);
				pwmPin.setValue(false);
				sleep_us(valueLow_microS);
			}
		}
	}
	
	public void stop(){
		pwmActivie=false;
	}
	
}
