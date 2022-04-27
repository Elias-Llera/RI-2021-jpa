package alb.util.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ReflectionUtil {

	private static Map<Integer, Field> fieldCache = new HashMap<>();
	private static Map<Integer, Method> methodCache = new HashMap<>();

	public static <T> T newInstance(Class<T> clazz) {
		try {

			return clazz.getConstructor().newInstance();

		} catch (InstantiationException 
				| IllegalAccessException
				| IllegalArgumentException
				| InvocationTargetException
				| NoSuchMethodException
				| SecurityException e) {
			
			throw new RuntimeException(e);
		}
	}

	public static Field getField(Class<?> clazz, String fieldName) {
		Field field = fieldCache.get(composeKey(clazz, fieldName));
		if (field != null) {
			return field;
		}
		try {
			field = getFieldInHierarchy(clazz, fieldName);
			field.setAccessible(true);
			fieldCache.put(composeKey(clazz, fieldName), field);
		} catch (NoSuchFieldException e) {
			throw new IllegalStateException(e);
		}
		return field;
	}

	private static Field getFieldInHierarchy(Class<?> clazz, String name)
			throws NoSuchFieldException {
		if (clazz == null) {
			throw new NoSuchFieldException(name);
		}
		try {
			return clazz.getDeclaredField(name);
		} catch (NoSuchFieldException e) {
			return getFieldInHierarchy(clazz.getSuperclass(), name);
		}
	}

	private static int composeKey(Class<?> clazz, String string) {
		final int prime = 31;
		int result = 1;
		result = prime * result + clazz.hashCode();
		result = prime * result + string.hashCode();
		return result;
	}

	public static void applyValueToField(Object owner, Field field, Object value)
			throws IllegalArgumentException {

		try {
			if ( ! field.canAccess( owner )) {
				field.setAccessible(true);
			}

			if (field.getType().equals(boolean.class)) {
				field.setBoolean(owner, ((Boolean) value));
			} else if (field.getType().equals(byte.class)) {
				field.setByte(owner, ((Byte) value));
			} else if (field.getType().equals(char.class)) {
				field.setChar(owner, ((Character) value));
			} else if (field.getType().equals(double.class)) {
				field.setDouble(owner, ((Double) value));
			} else if (field.getType().equals(float.class)) {
				field.setFloat(owner, ((Float) value));
			} else if (field.getType().equals(int.class)) {
				field.setInt(owner, ((Integer) value));
			} else if (field.getType().equals(long.class)) {
				field.setLong(owner, ((Long) value));
			} else if (field.getType().equals(short.class)) {
				field.setShort(owner, ((Short) value));
			} else {
				field.set(owner, value);
			}
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	public static Object invokeMethod(Object object, Method method,
			Object... args) throws Throwable {
		Object res = null;
		try {
			if ( ! method.canAccess( object )) {
				method.setAccessible(true);
			}

			res = method.invoke(object, args);

		} catch (Exception e) {
			handleMethodInvocationException(e);
		}
		return res;
	}

	private static void handleMethodInvocationException(Exception exception)
			throws Throwable {

		if (exception instanceof InvocationTargetException) {
			// rethrows original exception thrown by the method
			throw exception.getCause();
		}
		throw new IllegalStateException(exception);
	}

	public static Class<?> loadClass(String className) {
		try {

			return Class.forName(className);

		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	public static List<Field> getAnnotatedFields(Class<?> provider,
			Class<? extends Annotation> annotation) {
		List<Field> res = new LinkedList<>();
		for (Field f : provider.getDeclaredFields()) {
			if (f.isAnnotationPresent(annotation)) {
				res.add(f);
			}
		}
		return res;
	}

	public static Method getMethodOfClass(
			Class<?> clazz, String methodName, Class<?>... parameterTypes) {
		Method method = methodCache.get(composeKey(clazz, methodName));
		if (method != null) {
			return method;
		}
		
		try {
			method = clazz.getMethod(methodName, parameterTypes);
		} catch (Exception ex) {
			handleExceptionForGetMethodOfClass(clazz, methodName, ex);
		}
				
		methodCache.put(composeKey(clazz, methodName), method);
		return method;
	}

	private static void handleExceptionForGetMethodOfClass(
			Class<?> clazz, String methodName, Exception ex) {
		
		throw new IllegalStateException("No method found for "
				+ methodName + " in " + clazz.getSimpleName(),
				ex);
	}

	public static Object getFieldValue(Object obj, Field field) {
		Object value = null;
		try {
			if ( ! field.canAccess( obj ) ) {
				field.setAccessible(true);
			}
			value = field.get(obj);
		} catch (Exception e) {
			handleFieldAccessException(e);
		}
		return value;
	}

	/**
	 * Gets the field value using reflection.
	 * 
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 */
	public static Object getFieldValue(Object obj, String fieldName) {
		Field field = getField(obj.getClass(), fieldName);
		return getFieldValue(obj, field);
	}

	private static void handleFieldAccessException(Exception exception) {
		throw new IllegalStateException(exception);
	}

}
