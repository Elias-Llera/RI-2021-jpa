package alb.util.log.adapter;

import alb.util.console.Console;
import alb.util.log.Logger;

public class ConsoleLogger extends BaseLogger implements Logger {

	@Override
	protected void print(String line) {
		Console.println( line );
	}


}
