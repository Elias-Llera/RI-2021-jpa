package alb.util.linker.handler;

import java.lang.reflect.Field;

import alb.util.linker.SideHandler;
import alb.util.reflection.ReflectionUtil;

public class OneSideHandler implements SideHandler {

	@Override
	public void set(Object owner, String role, Object value) {
		Field f = ReflectionUtil.getField(owner.getClass(), role);
		ReflectionUtil.applyValueToField(owner, f, value);
	}

	@Override
	public void clear(Object owner, String role, Object value) {
		Field f = ReflectionUtil.getField(owner.getClass(), role);
		ReflectionUtil.applyValueToField(owner, f, null);
	}

}
