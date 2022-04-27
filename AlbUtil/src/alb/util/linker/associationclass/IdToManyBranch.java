package alb.util.linker.associationclass;

import alb.util.linker.AssociationSide;

public class IdToManyBranch implements AssociationClassBranch {
	
	private AssociationSide idSide;
	private AssociationSide manySide;

	public IdToManyBranch id(String role, Object value) {
		idSide = AssociationSide.One(role, value);
		return this;
	}

	public IdToManyBranch many(String role, Object value) {
		manySide = AssociationSide.Many(role, value);
		return this;
	}

	public IdToManyBranch many(Object value) {
		manySide = AssociationSide.Many(value);
		return this;
	}

	@Override
	public AssociationSide getIdSide() {
		return idSide;
	}

	@Override
	public AssociationSide getOtherSide() {
		return manySide;
	}

	@Override
	public String toString() {
		return "IdToManyBranch [idSide=" + idSide + ", manySide=" + manySide + "]";
	}

}
