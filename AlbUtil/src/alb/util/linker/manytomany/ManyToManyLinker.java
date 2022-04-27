package alb.util.linker.manytomany;

import alb.util.linker.AssociationSide;

public class ManyToManyLinker {
	private AssociationSide[] sides = new AssociationSide[2];
	private int idx = 0;

	public ManyToManyLinker() {}
	
	public ManyToManyLinker many(String role, Object object) {
		sides[ idx++ ] = AssociationSide.Many(role, object);
		return this;
	}

	public ManyToManyLinker many(Object object) {
		sides[ idx++ ] = AssociationSide.Many( object );
		return this;
	}

	/**
	 * Sets the crossed links
	 */
	public void link() {
		AssociationSide oneSide = sides[ 0 ];
		AssociationSide otherSide = sides[ 1 ];
		
		otherSide.pointingTo( oneSide ).setValue();
		oneSide.pointingTo( otherSide ).setValue();
	}

	/**
	 * Unsets the links in reverse order as link() does to avoid the identity
	 * problem.
	 */
	public void unlink() {
		AssociationSide oneSide = sides[ 0 ];
		AssociationSide otherSide = sides[ 1 ];
		
		oneSide.pointingTo( otherSide ).unsetValue();
		otherSide.pointingTo( oneSide ).unsetValue();
	}

}
