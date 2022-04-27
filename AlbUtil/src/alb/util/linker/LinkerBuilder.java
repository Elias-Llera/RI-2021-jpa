package alb.util.linker;

import alb.util.linker.associationclass.AssociationClassLinker;
import alb.util.linker.associationclass.IdToManyBranch;
import alb.util.linker.associationclass.IdToOneBranch;
import alb.util.linker.manytomany.ManyToManyLinker;
import alb.util.linker.onetomany.OneToManyLinker;
import alb.util.linker.onetoone.OneToOneLinker;

public class LinkerBuilder {
	
	public static OneToManyLinker oneToMany() {
		return new OneToManyLinker();
	}

	public static OneToOneLinker oneToOne() {
		return new OneToOneLinker();
	}

	public static ManyToManyLinker manyToMany() {
		return new ManyToManyLinker();
	}

	public static AssociationClassLinker associationClass() {
		return new AssociationClassLinker();
	}

	public static IdToManyBranch idToMany() {
		return new IdToManyBranch();
	}

	public static IdToOneBranch idToOne() {
		return new IdToOneBranch();
	}
}
