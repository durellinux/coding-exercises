package com.gianlucadurelli.coding.realworld;

import org.assertj.core.api.Assertions;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;

public class MadgeRemoveUnusedTest {

	@Test
	@Ignore
	public void sampleCase() throws IOException {
		String depdendencyFile = "PATH_TO_MADGE_DEPENDENCY_FILE";
		String entryPointFile = "PATH_TO_A_FILE_CONTAINING_LIST_OF_ENTRY_POINTS_OF_THE_APP";
		String inversifyDependencies = "PATH_TO_A_FILE_CONTAINING_ANY_OTHER_DEPENDENCIES_NOT_DETECTED_BY_MADGE";
		MadgeRemoveUnused solver = new MadgeRemoveUnused();
		Assertions.assertThat(solver.computeNotUsed(depdendencyFile, entryPointFile, inversifyDependencies));
	}

}