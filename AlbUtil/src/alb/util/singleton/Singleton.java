package alb.util.singleton;

/**
 * An implementation of the singleton pattern.
 * The Builder interface might be implemented as a lambda.
 * @author alb
 *
 * @param <T>
 */
public class Singleton<T> {
	
	@FunctionalInterface
	public interface Builder<T> {
		T build();
	}

	private T instance = null;
	
	public static <T> Singleton<T> empty() {
		return new Singleton<>();
	}

	public T orElse(Builder<T> builder) {
		if (instance == null) {
			instance = builder.build(); 
		}
		return instance;
	}

}