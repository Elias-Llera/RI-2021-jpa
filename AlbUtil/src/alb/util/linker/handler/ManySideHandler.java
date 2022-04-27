package alb.util.linker.handler;

import java.lang.reflect.Field;
import java.util.Collection;

import alb.util.linker.SideHandler;
import alb.util.reflection.ReflectionUtil;

public class ManySideHandler implements SideHandler {

	@Override @SuppressWarnings({ "rawtypes", "unchecked" })
	public void set(Object owner, String role, Object value) {
		Field f = ReflectionUtil.getField(owner.getClass(), role);
		Collection c = (Collection) ReflectionUtil.getFieldValue(owner, f);
		c.add( value );
	}

	@Override @SuppressWarnings("rawtypes")
	public void clear(Object owner, String role, Object value) {
		Field f = ReflectionUtil.getField(owner.getClass(), role);
		Collection c = (Collection) ReflectionUtil.getFieldValue(owner, f);
		c.remove( value );
	}		

}
