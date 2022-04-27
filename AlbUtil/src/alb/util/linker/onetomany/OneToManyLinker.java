package alb.util.linker.onetomany;

import alb.util.linker.AssociationSide;
import alb.util.linker.Linker;

public class OneToManyLinker implements Linker {

	private AssociationSide oneSide;
	private AssociationSide manySide;

	public OneToManyLinker() {}
	
	public OneToManyLinker one(String roleOne, Object object) {
		oneSide = AssociationSide.One( roleOne, object );
		return this;
	}

	public OneToManyLinker one(Object object) {
		oneSide = AssociationSide.One( object );
		return this;
	}

	public OneToManyLinker many(String role, Object object) {
		manySide = AssociationSide.Many(role, object);
		return this;
	}

	public OneToManyLinker many(Object object) {
		manySide = AssociationSide.Many( object );
		return this;
	}

	/**
	 * Sets the crossed links, but first the ONE side and then the MANY.
	 * This avoids problems with collections if identity (i.e. hashCode() and
	 * equals()) depends on the link set on the one side.
	 */
	public void link() {
		manySide.pointingTo( oneSide ).setValue();
		oneSide.pointingTo( manySide ).setValue();
	}

	/**
	 * Unsets the links in reverse order as link() does to avoid the identity
	 * problem.
	 */
	public void unlink() {
		oneSide.pointingTo( manySide ).unsetValue();
		manySide.pointingTo( oneSide ).unsetValue();
	}

}
