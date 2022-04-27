package alb.util.linker.onetoone;

import alb.util.linker.AssociationSide;
import alb.util.linker.Linker;

public class OneToOneLinker implements Linker {

	private AssociationSide[] sides = new AssociationSide[2];
	private int idx = 0;
	private int idSidePos = -1; // Array position of idSide, if any

	public OneToOneLinker() {}
	
	public OneToOneLinker one(String roleOne, Object object) {
		sides[ idx++ ] = AssociationSide.One( roleOne, object );
		return this;
	}

	public OneToOneLinker one(Object object) {
		sides[ idx++ ] = AssociationSide.One( object );
		return this;
	}

	public OneToOneLinker id(String role, Object object) {
		one(role, object);
		idSidePos = idx-1; // Array position is the previous position
		return this;
	}

	/**
	 * Sets the crossed links, but first the ONE side and then the MANY.
	 * This avoids problems with collections if identity (i.e. hashCode() and
	 * equals()) depends on the link set on the one side.
	 */
	public void link() {
		AssociationSide oneSide = sides[ getIdSideOrFirst() ];
		AssociationSide otherSide = sides[ getOtherSide() ];
		
		otherSide.pointingTo( oneSide ).setValue(); 
		oneSide.pointingTo( otherSide ).setValue();
	}

	/**
	 * Unsets the links in reverse order as link() does to avoid the identity
	 * problem.
	 */
	public void unlink() {
		AssociationSide oneSide = sides[ getIdSideOrFirst() ];
		AssociationSide otherSide = sides[ getOtherSide() ];
		
		oneSide.pointingTo( otherSide ).unsetValue();
		otherSide.pointingTo( oneSide ).unsetValue(); 
	}

	private int getOtherSide() {
		return idSidePos == -1 ? 1 : (1 - idSidePos);
	}

	private int getIdSideOrFirst() {
		return idSidePos == -1 ? 0 : idSidePos;
	}

}
