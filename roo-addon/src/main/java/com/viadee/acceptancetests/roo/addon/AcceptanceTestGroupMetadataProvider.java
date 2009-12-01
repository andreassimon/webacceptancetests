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

import org.springframework.roo.classpath.PhysicalTypeIdentifier;
import org.springframework.roo.classpath.PhysicalTypeMetadata;
import org.springframework.roo.classpath.itd.AbstractItdMetadataProvider;
import org.springframework.roo.classpath.itd.ItdProviderRole;
import org.springframework.roo.classpath.itd.ItdTypeDetailsProvidingMetadataItem;
import org.springframework.roo.metadata.MetadataDependencyRegistry;
import org.springframework.roo.metadata.MetadataService;
import org.springframework.roo.model.JavaType;
import org.springframework.roo.process.manager.FileManager;
import org.springframework.roo.project.Path;
import org.springframework.roo.support.lifecycle.ScopeDevelopment;

/**
 * Provides {@link AcceptanceTestGroupMetadata}.
 * 
 * @author Andreas Simon
 * @since 1.0
 * 
 */
@ScopeDevelopment
public final class AcceptanceTestGroupMetadataProvider extends
		AbstractItdMetadataProvider {

	private AcceptanceTestsUtils _acceptanceTestsUtils;

	public AcceptanceTestGroupMetadataProvider(MetadataService metadataService,
			MetadataDependencyRegistry metadataDependencyRegistry,
			FileManager fileManager, AcceptanceTestsUtils acceptanceTestsUtils) {
		super(metadataService, metadataDependencyRegistry, fileManager);
		addProviderRole(ItdProviderRole.ACCESSOR_MUTATOR);
		addMetadataTrigger(new JavaType(RooAcceptanceTestGroup.class.getName()));
		_acceptanceTestsUtils = acceptanceTestsUtils;
	}

	protected ItdTypeDetailsProvidingMetadataItem getMetadata(
			String metadataIdentificationString, JavaType aspectName,
			PhysicalTypeMetadata governorPhysicalTypeMetadata,
			String itdFilename) {
		return new AcceptanceTestGroupMetadata(metadataIdentificationString,
				aspectName, governorPhysicalTypeMetadata, _acceptanceTestsUtils);
	}

	public String getItdUniquenessFilenameSuffix() {
		return "AcceptanceTestGroup";
	}

	protected String getGovernorPhysicalTypeIdentifier(
			String metadataIdentificationString) {
		JavaType javaType = AcceptanceTestGroupMetadata
				.getJavaType(metadataIdentificationString);
		Path path = AcceptanceTestGroupMetadata.getPath(metadataIdentificationString);
		String physicalTypeIdentifier = PhysicalTypeIdentifier
				.createIdentifier(javaType, path);
		return physicalTypeIdentifier;
	}

	protected String createLocalIdentifier(JavaType javaType, Path path) {
		return AcceptanceTestGroupMetadata.createIdentifier(javaType, path);
	}

	public String getProvidesType() {
		return AcceptanceTestGroupMetadata.getMetadataIdentiferType();
	}

}
