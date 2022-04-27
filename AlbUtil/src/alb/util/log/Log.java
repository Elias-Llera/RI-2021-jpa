package alb.util.log;

import alb.util.log.adapter.ConsoleLogger;


public class Log {

	private static Logger logger;

	private static Logger createInstance() {
		return new ConsoleLogger();
	}

	public static void setLogLevel(int level) {
		instance().setLogLevel( level );
	}

	public static void addSourceClass(String clazz) {
		instance().addSourceClass( clazz );
	}

	public static void error(String msg, Object... args) {
		instance().error(msg, args);
	}

	public static void warn(String msg, Object... args) {
		instance().warn(msg, args);
	}

	public static void info(String msg, Object... args) {
		instance().info(msg, args);
	}
	
	public static void debug(String msg, Object... args) {
		instance().debug(msg, args);
	}
	
	public static void trace(String msg, Object... args) {
		instance().trace(msg, args);
	}

	public static void error(Throwable e) {
		instance().error(e);
	}

	public static void warn(Throwable e) {
		instance().warn(e);
	}

	public static void info(Throwable e) {
		instance().info(e);
	}
	
	public static void debug(Throwable e) {
		instance().debug(e);
	}
	
	public static void trace(Throwable e) {
		instance().trace(e);
	}
	
	private static Logger instance() {
		if (logger == null) {
			logger = createInstance();
		}
		return logger;
	}

}
