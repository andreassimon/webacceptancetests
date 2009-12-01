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

import org.springframework.roo.metadata.MetadataService;
import org.springframework.roo.model.JavaPackage;
import org.springframework.roo.project.ProjectMetadata;
import org.springframework.roo.support.lifecycle.ScopeDevelopment;

/**
 * Provides helper methods for the classes of the addon.
 *  
 * @author Andreas Simon
 */
@ScopeDevelopment
public class AcceptanceTestsUtils {

	private MetadataService _metadataService;

	public AcceptanceTestsUtils(MetadataService metadataService) {
		super();
		_metadataService = metadataService;
	}

	protected String acceptancetestsPackageName() {
		return topLevelPackage().getFullyQualifiedPackageName()
				+ ".acceptancetests";
	}

	protected JavaPackage topLevelPackage() {
		return projectMetadata().getTopLevelPackage();
	}

	protected ProjectMetadata projectMetadata() {
		return (ProjectMetadata) _metadataService
				.get(ProjectMetadata.getProjectIdentifier());
	}

}
