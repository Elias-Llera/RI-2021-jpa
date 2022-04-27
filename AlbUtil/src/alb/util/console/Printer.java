package alb.util.console;

import java.io.PrintStream;

/**
 * Métodos de utilidad para escribir cosas en pantalla de forma controlada.
 * Aquí irían todas las decoraciones pertinentes
 * 
 * @author alb
 */
public class Printer {
	private static PrintStream con = System.out;
	
	public static void printHeading(String string) {
		con.println(string);
	}

	/**
	 * Avisa de error lógico en la ejecución, muy probablemente por 
	 * equivocaciín del usuario o por circunstancias que han cambiado 
	 * durante el "think time" del usuario (control optimista etc.)
	 * 
	 * @param e
	 */
	public static void printBusinessException(Exception e) {
		con.println("An error has happened processing your request:");
		con.println("\t- " + e.getLocalizedMessage());
	}

	/**
	 * Avisa de error irrecuperable
	 * @param string
	 * @param e
	 */
	public static void printRuntimeException(RuntimeException e) {
		con.println("Ha ocurrido un error interno no recuperable, " +
				"el programa debe terminar.\n" +
				"[A continuación se muestra una traza del error]\n" +
				"[la traza no sería visible por el usuario cuando la aplicación "
				+ "esté en producción]\n" + 
				"[se mandaría al log del sistema]");

		e.printStackTrace();
	}

	public static void print(String msg) {
		con.println(msg);
	}

	public static void printException(String string, Exception e) {
		con.println(string);
		con.println("\t- " + e.getLocalizedMessage());
	}

}
