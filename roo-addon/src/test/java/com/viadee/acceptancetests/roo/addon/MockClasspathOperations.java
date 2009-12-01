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

import java.util.List;

import org.springframework.roo.classpath.MutablePhysicalTypeMetadataProvider;
import org.springframework.roo.classpath.PhysicalTypeCategory;
import org.springframework.roo.classpath.details.ClassOrInterfaceTypeDetails;
import org.springframework.roo.classpath.details.ConstructorMetadata;
import org.springframework.roo.classpath.details.FieldMetadata;
import org.springframework.roo.classpath.details.MethodMetadata;
import org.springframework.roo.classpath.details.MutableClassOrInterfaceTypeDetails;
import org.springframework.roo.classpath.details.annotations.AnnotationMetadata;
import org.springframework.roo.classpath.operations.ClasspathOperations;
import org.springframework.roo.metadata.MetadataService;
import org.springframework.roo.model.JavaSymbolName;
import org.springframework.roo.model.JavaType;
import org.springframework.roo.process.manager.FileManager;
import org.springframework.roo.project.ProjectOperations;

public class MockClasspathOperations extends ClasspathOperations {

	public MockClasspathOperations(ProjectOperations projectOperations,
			MutablePhysicalTypeMetadataProvider physicalTypeMetadataProvider,
			MetadataService metadataService, FileManager fileManager) {
		super(projectOperations, physicalTypeMetadataProvider, metadataService,
				fileManager);
	}

	@Override
	public ClassOrInterfaceTypeDetails getClassOrInterface(
			final JavaType requiredClassOrInterface) {
		return new MutableClassOrInterfaceTypeDetails() {
			
			public JavaType getName() {
				return requiredClassOrInterface;
			}
			
			public PhysicalTypeCategory getPhysicalTypeCategory() { return null; }
			
			public List<JavaType> getImplementsTypes() { return null; }
			
			public List<JavaType> getExtendsTypes() { return null; }
			
			public List<? extends MethodMetadata> getDeclaredMethods() { return null; }
			
			public List<? extends FieldMetadata> getDeclaredFields() { return null; }
			
			public List<? extends ConstructorMetadata> getDeclaredConstructors() { return null; }
			
			public ClassOrInterfaceTypeDetails getSuperclass() { return null; }
			
			public int getModifier() { return 0; }
			
			public List<JavaSymbolName> getEnumConstants() { return null; }
			
			public String getDeclaredByMetadataId() { return null; }
			
			public void removeTypeAnnotation(JavaType annotationType) { }
			
			public void removeField(JavaSymbolName fieldName) { }
			
			public List<? extends AnnotationMetadata> getTypeAnnotations() { return null; }
			
			public void addTypeAnnotation(AnnotationMetadata annotation) { }
			
			public void addMethod(MethodMetadata methodMetadata) { }
			
			public void addField(FieldMetadata fieldMetadata) { }
			
			public void addEnumConstant(JavaSymbolName name) { }
		};
	}

}
