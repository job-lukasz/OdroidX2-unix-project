package c;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;

public class Log {
	public static Logger rootLogger = (Logger) LoggerFactory.getLogger("mainLogger");
}
