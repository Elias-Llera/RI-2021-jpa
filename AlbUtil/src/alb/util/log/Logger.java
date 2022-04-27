package alb.util.log;

public interface Logger {

	void setLogLevel(int level);
	void addSourceClass(String clazz);

	void error(Throwable e);
	void warn(Throwable e);
	void info(Throwable e);
	void debug(Throwable e);
	void trace(Throwable e);
	
	void error(String msg, Object... args);
	void warn(String msg, Object... args);
	void info(String msg, Object... args);
	void debug(String msg, Object... args);
	void trace(String msg, Object... args);

}
