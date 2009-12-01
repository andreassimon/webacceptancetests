//  Copyright 2009 viadee Unternehmensberatung GmbH / Andreas Simon
//	
//  Licensed under the Apache License, Version 2.0 (the "License");
//  you may not use this file except in compliance with the License.
//  You may obtain a copy of the License at
//	
//    http://www.apache.org/licenses/LICENSE-2.0
//	
//  Unless required by applicable law or agreed to in writing, software
//  distributed under the License is distributed on an "AS IS" BASIS,
//  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//  See the License for the specific language governing permissions and
//  limitations under the License.
package com.viadee.acceptancetests.roo.addon;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.springframework.roo.addon.maven.MavenCommands;

public class AcceptanceTestsCommandsTest {

	private AcceptanceTestsCommands _acceptanceTestsCommands;
	private MavenCommands _mavenCommands;
	private AcceptanceTestsOperations _acceptanceTestsOperations;

	@Before
	public void initAcceptanceTestsCommands() {
		_acceptanceTestsCommands = new AcceptanceTestsCommands(acceptanceTestOperations(), mavenCommands());
	}

	private AcceptanceTestsOperations acceptanceTestOperations() {
		if (_acceptanceTestsOperations == null) {
			_acceptanceTestsOperations = mock(AcceptanceTestsOperations.class);
		}
		return _acceptanceTestsOperations;
	}

	@Test
	public void performAcceptancetestsShouldInvokeIntegrationTestPhaseOfMavenBuild()
			throws IOException {
		_acceptanceTestsCommands.performAcceptancetests();
		verify(mavenCommands()).mvn("-Dmaven.test.skip integration-test");
	}

	private MavenCommands mavenCommands() {
		if (_mavenCommands == null) {
			_mavenCommands = mock(MavenCommands.class);
		}
		return _mavenCommands;
	}

}
