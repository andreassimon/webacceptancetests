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

import static org.mockito.Mockito.spy;

import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;

import org.springframework.roo.classpath.details.DefaultPhysicalTypeMetadata;
import org.springframework.roo.classpath.details.MutableClassOrInterfaceTypeDetails;
import org.springframework.roo.metadata.MetadataItem;
import org.springframework.roo.metadata.MetadataProvider;
import org.springframework.roo.metadata.MetadataService;
import org.springframework.roo.model.JavaPackage;
import org.springframework.roo.project.Dependency;
import org.springframework.roo.project.PathResolver;
import org.springframework.roo.project.ProjectMetadata;

/**
 * @author Andreas Simon
 *
 */
public class MockMetadataService implements MetadataService {

	private ProjectMetadata _projectMetadata;
	private MetadataItem _generalStoriesMetadata;
	private boolean _generalStoriesExists;
	private MutableClassOrInterfaceTypeDetails _generalStoriesPhysicalTypeDetails;

	public void setGeneralStoriesExists(boolean generalStoriesExists) {
		_generalStoriesExists = generalStoriesExists;
	}

	public void deregister(String metadataIdentificationString) { }

	public MetadataItem get(String metadataIdentificationString) {
		if ("MID:org.springframework.roo.project.ProjectMetadata#the_project"
				.equals(metadataIdentificationString)) {
			return projectMetadata();
		} else if ("MID:org.springframework.roo.classpath.PhysicalTypeIdentifier#SRC_TEST_JAVA?de.viadee.bibliothek1.acceptancetests.GeneralStories"
				.equals(metadataIdentificationString)) {
			if (_generalStoriesExists) {
				return generalStoriesMetadata();
			} else {
				return null;
			}
		} else if ("MID:org.springframework.roo.classpath.PhysicalTypeIdentifier#SRC_TEST_JAVA?de.viadee.bibliothek1.acceptancetests.SpecifiedStories"
				.equals(metadataIdentificationString)) {
			// return specifiedStoriesMetadata();
			return null;
		} else if ("MID:org.springframework.roo.classpath.PhysicalTypeIdentifier#SRC_TEST_JAVA?de.viadee.bibliothek1.acceptancetests.AbstractAcceptanceTest"
				.equals(metadataIdentificationString)) {
			// return specifiedStoriesMetadata();
			return null;
		}
		throw new IllegalArgumentException(String.format(
				"There is no value for the given Metadata ID: \"%s\"",
				metadataIdentificationString));
	}

	private MetadataItem generalStoriesMetadata() {
		if (_generalStoriesMetadata == null) {
			String physicalLocationCanonicalPath = "none";
			String metadataIdentificationString = "MID:org.springframework.roo.classpath.PhysicalTypeIdentifier#SRC_TEST_JAVA?de.viadee.bibliothek1.acceptancetests.GeneralStories";
			_generalStoriesMetadata = new DefaultPhysicalTypeMetadata(
					metadataIdentificationString,
					physicalLocationCanonicalPath,
					generalStoriesPhysicalTypeDetails());
		}
		return _generalStoriesMetadata;
	}

	public MutableClassOrInterfaceTypeDetails generalStoriesPhysicalTypeDetails() {
		if (_generalStoriesPhysicalTypeDetails == null) {
			_generalStoriesPhysicalTypeDetails = spy(new GeneralStoriesPhysicalTypeDetails());
		}
		return _generalStoriesPhysicalTypeDetails;
	}

	private MetadataItem projectMetadata() {
		if (_projectMetadata == null) {
			PathResolver pathResolver = new MockPathResolver();
			Set<Dependency> buildPluginDependencies = new HashSet<Dependency>();
			Set<Dependency> dependencies = new HashSet<Dependency>();
			_projectMetadata = new ProjectMetadata(new JavaPackage(
					"de.viadee.bibliothek1"), "Bibliothek", dependencies,
					buildPluginDependencies, pathResolver);
		}
		return _projectMetadata;
	}

	public MetadataItem get(String metadataIdentificationString, boolean evictCache) { return null; }

	public MetadataProvider getRegisteredProvider(String metadataIdentificationString) { return null; }

	public SortedSet<MetadataProvider> getRegisteredProviders() { return null; }

	public void register(MetadataProvider provider) { }

	public void notify(String upstreamDependency, String downstreamDependency) { }

	public void evict(String metadataIdentificationString) { }

	public void evictAll() { }

}