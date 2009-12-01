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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.springframework.roo.metadata.MetadataService;
import org.springframework.roo.project.Dependency;
import org.springframework.roo.project.Execution;
import org.springframework.roo.project.ProjectMetadataProvider;
import org.springframework.roo.project.ProjectOperations;

public class MockProjectOperations extends ProjectOperations {

	private int _numberOfCallsToDependencyUpdate = 0;
	private int _numberOfCallsToBuildPluginUpdate = 0;

	public MockProjectOperations(MetadataService metadataService,
			ProjectMetadataProvider projectMetadataProvider) {
		super(metadataService, projectMetadataProvider);
	}

	@Override
	public void dependencyUpdate(Dependency dependency) {
		_numberOfCallsToDependencyUpdate++;
	}

	@Override
	public void buildPluginUpdate(Dependency buildPluginDependency,
			Execution... executions) {
		_numberOfCallsToBuildPluginUpdate++;
		assertEquals(new Dependency("org.mortbay.jetty",
				"maven-jetty-plugin", "6.1.10"), buildPluginDependency);
		assertTrue(executionsAreEqual(new Execution(
				"execute-acceptance-tests", "run"), executions[0]));
	}

	private static boolean executionsAreEqual(Execution execution1,
			Execution execution2) {
		return execution1.getId().equals(execution2.getId())
				&& execution1.getGoals().equals(execution2.getGoals());
	}

	public int getCallsToDependencyUpdate() {
		return _numberOfCallsToDependencyUpdate;
	}

	public int getNumberOfCallsToBuildPluginUpdate() {
		return _numberOfCallsToBuildPluginUpdate;
	}

}