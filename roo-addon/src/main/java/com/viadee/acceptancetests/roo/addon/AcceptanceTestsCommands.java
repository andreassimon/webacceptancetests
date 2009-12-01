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

import java.io.IOException;

import org.springframework.roo.addon.maven.MavenCommands;
import org.springframework.roo.shell.CliCommand;
import org.springframework.roo.shell.CliOption;
import org.springframework.roo.shell.CommandMarker;
import org.springframework.roo.support.lifecycle.ScopeDevelopmentShell;
import org.springframework.roo.support.util.Assert;

/**
 * @author Andreas Simon
 * 
 */
@ScopeDevelopmentShell
public class AcceptanceTestsCommands implements CommandMarker {

	private AcceptanceTestsOperations _acceptanceTestOperations;
	private MavenCommands _mavenCommands;

	public AcceptanceTestsCommands(AcceptanceTestsOperations acceptanceTestOperations, MavenCommands mavenCommands) {
		Assert.notNull(acceptanceTestOperations, "acceptance test required");
		_acceptanceTestOperations = acceptanceTestOperations;
		_mavenCommands = mavenCommands;
	}

	@CliCommand(value = "acceptancetest", help = "Create a new acceptance test")
	public void newAcceptanceTest(
			@CliOption(key = { "story" }, mandatory = true, help = "The description of the new acceptance test, beginning with \"Should ...\"") String story,
			@CliOption(key = { "storyGroup" }, mandatory = false, unspecifiedDefaultValue="General", help = "The group that the new acceptance test belongs to.") StoryGroup storyGroup)
			throws IOException {
		_acceptanceTestOperations.newAcceptanceTest(story, storyGroup.toString());
	}

	@CliCommand(value = "perform acceptancetests", help = "Execute all acceptancetests.")
	public void performAcceptancetests() throws IOException {
		_mavenCommands.mvn("-Dmaven.test.skip integration-test");
	}

}
