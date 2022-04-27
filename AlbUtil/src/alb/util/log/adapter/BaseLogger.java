package alb.util.log.adapter;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import alb.util.log.LogLevel;
import alb.util.log.Logger;

public abstract class BaseLogger implements Logger {
	private int logLevel = LogLevel.WARN;
	private String pattern = "%s - [%s] - %s(): %s";
	private SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss.SSS");
	private List<String> validClassSources = new ArrayList<String>();

	@Override
	public void debug(Throwable e) {
		if (logLevel > LogLevel.DEBUG) return;
		log(LogLevel.DEBUG, e);
	}

	@Override
	public void error(Throwable e) {
		if (logLevel > LogLevel.ERROR) return;
		log(LogLevel.ERROR, e);
	}

	@Override
	public void info(Throwable e) {
		if (logLevel > LogLevel.INFO) return;
		log(LogLevel.INFO, e);
	}

	@Override
	public void trace(Throwable e) {
		if (logLevel > LogLevel.TRACE) return;
		log(LogLevel.TRACE, e);
	}

	@Override
	public void warn(Throwable e) {
		if (logLevel > LogLevel.WARN) return;
		log(LogLevel.WARN, e);
	}

	@Override
	public void debug(String msg, Object... args) {
		if (logLevel > LogLevel.DEBUG) return;
		log(LogLevel.DEBUG, String.format(msg, args));
	}

	@Override
	public void error(String msg, Object... args) {
		if (logLevel > LogLevel.ERROR) return;
		log(LogLevel.ERROR, String.format(msg, args));
	}

	@Override
	public void info(String msg, Object... args) {
		if (logLevel > LogLevel.INFO) return;
		log(LogLevel.INFO, String.format(msg, args));
	}

	@Override
	public void trace(String msg, Object... args) {
		if (logLevel > LogLevel.TRACE) return;
		log(LogLevel.TRACE, String.format(msg, args));
	}

	@Override
	public void warn(String msg, Object... args) {
		if (logLevel > LogLevel.WARN) return;
		log(LogLevel.WARN, String.format(msg, args));
	}

	@Override
	public void setLogLevel(int level) {
		LogLevel.assertValidValue( level );
		logLevel = level;
	}

	@Override
	public void addSourceClass(String clazz) {
		validClassSources.add( clazz );
	}

	protected void log(int level, Throwable e) {
		log(level, stackTraceAsString(e) );
	}

	protected String stackTraceAsString(Throwable e) {
		StringWriter sw = new StringWriter();
		try (PrintWriter pw = new PrintWriter( sw )) {
			e.printStackTrace( pw );
		}
		return sw.toString();
	}

	protected void log(int level, String msg) {
		String clazz = getLoggingClassInfo();
		if ( ! isValidClassSource( clazz )) return;
		
		String date = dateFormatter.format( new Date() );
		String levelStr = LogLevel.toString(level);
		
		print( String.format( pattern, date, levelStr, clazz, msg) );
	}
	
	private boolean isValidClassSource(String clazz) {
		if (validClassSources.size() == 0) return true;
		
		for(String classSource : validClassSources) {
			if ( clazz.startsWith( classSource ) ) return true;
		}
		return false;
	}

	protected abstract void print(String line);

	/**
	 * Returns info about the class and method of the logging class (the object's
	 * class which is calling this Log.xxx() method) 
	 * 
	 * @return
	 */
	private String getLoggingClassInfo() {
		StackTraceElement[] stack = Thread.currentThread().getStackTrace();
		StackTraceElement loggingClass = findLoggingClass( stack );
		return loggingClass.getClassName() + "." + loggingClass.getMethodName();
	}
	
	/**
	 * Returns the StackTraceElement for the stack entry corresponding to the 
	 * object's method that is calling the Log.xxx() method.
	 * 
	 * Finds it by exploring the call stack from top until it finds
	 * the first class that is outside the package of this logging framework.
	 * 
	 * @param stack
	 * @return the StackTraceElement
	 */
	private StackTraceElement findLoggingClass(StackTraceElement[] stack) {
		String thisPackagePrefix = "alb.util.log";
		for(int i = 1 /* starts with ONE */; i < stack.length; i++) {
			StackTraceElement ste = stack[i];
			if (! ste.getClassName().startsWith( thisPackagePrefix )) {
				return ste;
			}
		}
		return null;
	}

}
