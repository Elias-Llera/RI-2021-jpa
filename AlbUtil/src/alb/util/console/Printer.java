package alb.util.console;

import java.io.PrintStream;

/**
 * M�todos de utilidad para escribir cosas en pantalla de forma controlada.
 * Aqu� ir�an todas las decoraciones pertinentes
 * 
 * @author alb
 */
public class Printer {
	private static PrintStream con = System.out;
	
	public static void printHeading(String string) {
		con.println(string);
	}

	/**
	 * Avisa de error l�gico en la ejecuci�n, muy probablemente por 
	 * equivocaci�n del usuario o por circunstancias que han cambiado 
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
				"[A continuaci�n se muestra una traza del error]\n" +
				"[la traza no ser�a visible por el usuario cuando la aplicaci�n "
				+ "est� en producci�n]\n" + 
				"[se mandar�a al log del sistema]");

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
