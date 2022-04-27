package alb.util.linker.handler;

import alb.util.linker.SideHandler;

public class NonNavigableSideHandler implements SideHandler {

	@Override
	public void set(Object owner, String role, Object value) {
		// Nothing to do, this is a null handler
	}

	@Override
	public void clear(Object owner, String role, Object value) {
		// Nothing to do, this is a null handler
	}

}
