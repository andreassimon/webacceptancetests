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

import org.springframework.roo.classpath.MutablePhysicalTypeMetadataProvider;
import org.springframework.roo.classpath.PhysicalTypeMetadata;
import org.springframework.roo.metadata.MetadataItem;
import org.springframework.roo.model.JavaType;

public class MockPhysicalTypeMetadataProvider implements
		MutablePhysicalTypeMetadataProvider {

	public void createPhysicalType(PhysicalTypeMetadata toCreate) { }

	public String findIdentifier(JavaType javaType) { return null; }

	public MetadataItem get(String metadataIdentificationString) { return null; }

	public String getProvidesType() { return null; }

}
