package alb.util.log;

public class LogLevel {
	
	public static final int OFF    = 10;
	
	public static final int ERROR  = 5;
	public static final int WARN   = 4;
	public static final int INFO   = 3;
	public static final int DEBUG  = 2;
	public static final int TRACE  = 1;

	public static final int ALL    = 0;
	
	public static String toString(int level) {

		switch( level) {
		case ALL: 	return "ALL";
		case TRACE: return "TRACE";
		case DEBUG: return "DEBUG";
		case INFO: 	return "INFO";
		case WARN: 	return "WARN";
		case ERROR: return "ERROR";
		case OFF: 	return "OFF";
		}
		
		throw new IllegalStateException("LogLevel.toString() not supported value");
	}

	public static void assertValidValue(int level) {
		if (level < ALL || level > OFF) {
			throw new IllegalArgumentException("Invaled LogLevel " + level);
		}
	}

}
