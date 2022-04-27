package alb.util.linker.associationclass;

import alb.util.linker.AssociationSide;
import alb.util.linker.Linker;

public class AssociationClassLinker implements Linker {
	
	private AssociationClassBranch[] branches = new AssociationClassBranch[2];
	private int idx = 0;

	public AssociationClassLinker branch(AssociationClassBranch branch) {
		branches[ idx++ ] = branch;
		return this;
	}

	@Override
	public void link() {
		setOtherSide( branches[0] ); // pointing to the id side
		setOtherSide( branches[1] );

		setIdSide( branches[0] );  // pointing to the other side
		setIdSide( branches[1] );
	}

	@Override
	public void unlink() {
		unsetIdSide( branches[0] );
		unsetIdSide( branches[1] );

		unsetOtherSide( branches[0] );
		unsetOtherSide( branches[1] );
	}

	private void setIdSide(AssociationClassBranch branch) {
		AssociationSide idSide = branch.getIdSide();
		AssociationSide otherSide = branch.getOtherSide();
		
		idSide.pointingTo( otherSide ).setValue();
	}

	private void setOtherSide(AssociationClassBranch branch) {
		AssociationSide idSide = branch.getIdSide();
		AssociationSide otherSide = branch.getOtherSide();
		
		otherSide.pointingTo( idSide ).setValue();
	}

	private void unsetIdSide(AssociationClassBranch branch) {
		AssociationSide idSide = branch.getIdSide();
		AssociationSide otherSide = branch.getOtherSide();
		
		idSide.pointingTo( otherSide ).unsetValue();
	}

	private void unsetOtherSide(AssociationClassBranch branch) {
		AssociationSide idSide = branch.getIdSide();
		AssociationSide otherSide = branch.getOtherSide();
		
		otherSide.pointingTo( idSide ).unsetValue();
	}

}
