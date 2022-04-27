package alb.util.linker;

import alb.util.assertion.ArgumentChecks;
import alb.util.linker.handler.ManySideHandler;
import alb.util.linker.handler.NonNavigableSideHandler;
import alb.util.linker.handler.OneSideHandler;

public class AssociationSide {
	private String role;
	private Object value;
	private Multiplicity multiplicity;
	private SideHandler handler;
	
	private AssociationSide inverse;
	
	public static AssociationSide One(String role, Object value) {
		return new AssociationSide(role, value, 
				Multiplicity.ONE,
				new OneSideHandler() );
	}
	
	public static AssociationSide One(Object value) {
		return new AssociationSide(null, value, 
				Multiplicity.ONE,
				new NonNavigableSideHandler() );
	}
	
	public static AssociationSide Many(Object value) {
		return new AssociationSide(null, value, 
				Multiplicity.MANY,
				new NonNavigableSideHandler() );
	}
	
	public static AssociationSide Many(String role, Object value) {
		return new AssociationSide(role, value, 
				Multiplicity.MANY,
				new ManySideHandler() );
	}
	
	private AssociationSide(String role, Object value, 
			Multiplicity multiplicity, SideHandler handler) {
		ArgumentChecks.isNotNull( value );
		this.role = role;
		this.value = value;
		this.multiplicity = multiplicity;
		this.handler = handler;
	}
	
	public Multiplicity getMultiplicity() {
		return multiplicity;
	}

	public void setValue() {
		inverse.getHandler().set(value, inverse.role, inverse.value);
	}		

	private SideHandler getHandler() {
		return handler;
	}

	public void unsetValue() {
		inverse.getHandler().clear(value, inverse.role, inverse.value);
	}

	public AssociationSide pointingTo(AssociationSide inverse) {
		this.inverse = inverse;
		return this;
	}

	@Override
	public String toString() {
		return "AssociationSide [role=" + role + ", value=" + value 
				+ ", multiplicity=" + multiplicity 
				+ ", handler=" + handler 
				+ ", inverse=" + inverse + "]";
	}

}