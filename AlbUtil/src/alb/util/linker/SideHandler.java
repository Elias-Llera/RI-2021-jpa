package alb.util.linker;

public interface SideHandler {

	void set(Object owner, String role, Object value);
	void clear(Object owner, String role, Object value);

}
