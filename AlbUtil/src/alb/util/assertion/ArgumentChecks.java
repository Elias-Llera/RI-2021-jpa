package alb.util.assertion;

public abstract class ArgumentChecks {

	public static void isNotNull(final Object obj) {
		isNotNull( obj, " Cannot be null " );
	}

	public static void isNotNull(final Object obj, String msg) {
		isTrue( obj != null, msg );
	}

	public static void isNull(final Object obj) {
		isNull( obj, " Must be null " );
	}

	public static void isNull(final Object obj, final String msg) {
		isTrue( obj == null, msg );
	}

	public static void isFalse(boolean test) {
		isFalse(test, "Condition must be false");
	}

	public static void isFalse(boolean test, String msg) {
		isTrue(test == false, msg);
	}

	public static void isNotEmpty(final String str) {
		isNotEmpty( str, "The string cannot be null nor empty" );
	}

	public static void isNotEmpty(final String str, final String msg) {
		isTrue( str != null && str.isBlank() == false, msg );
	}

	public static void isTrue(final boolean test) {
		isTrue(test, "Condition must be true");
	}

	public static void isTrue(final boolean test, final String msg) {
		if (test == false) {
			throwException(msg);
		}
	}

	protected static void throwException(final String msg) {
		throw new IllegalArgumentException( msg );
	}

}
