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

import static org.springframework.roo.model.JavaType.VOID_PRIMITIVE;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import org.springframework.roo.classpath.PhysicalTypeIdentifierNamingUtils;
import org.springframework.roo.classpath.PhysicalTypeMetadata;
import org.springframework.roo.classpath.details.DefaultMethodMetadata;
import org.springframework.roo.classpath.details.annotations.AnnotatedJavaType;
import org.springframework.roo.classpath.details.annotations.AnnotationAttributeValue;
import org.springframework.roo.classpath.details.annotations.AnnotationMetadata;
import org.springframework.roo.classpath.details.annotations.DefaultAnnotationMetadata;
import org.springframework.roo.classpath.itd.AbstractItdTypeDetailsProvidingMetadataItem;
import org.springframework.roo.metadata.MetadataIdentificationUtils;
import org.springframework.roo.model.DataType;
import org.springframework.roo.model.JavaSymbolName;
import org.springframework.roo.model.JavaType;
import org.springframework.roo.project.Path;
import org.springframework.roo.support.style.ToStringCreator;
import org.springframework.roo.support.util.Assert;

/**
 * Metadata for {@link RooAcceptanceTestGroup}.
 * 
 * @author Andreas Simon
 * @since 1.0
 *
 */
public class AcceptanceTestGroupMetadata extends AbstractItdTypeDetailsProvidingMetadataItem {

	private static final String PROVIDES_TYPE_STRING = AcceptanceTestGroupMetadata.class.getName();
	private static final String PROVIDES_TYPE = MetadataIdentificationUtils.create(PROVIDES_TYPE_STRING);

	private static final JavaType ABSTRACT_APPLICATION_DRIVER = new JavaType(
			"de.viadee.lib.acceptancetests.api.AbstractApplicationDriver");
	private static final JavaType ABSTRACT_BUTTON = new JavaType(
			"de.viadee.lib.acceptancetests.api.AbstractButton");
	private static final JavaType ABSTRACT_CONDITION = new JavaType(
			"de.viadee.lib.acceptancetests.api.AbstractCondition");
	private static final JavaType ABSTRACT_CRITERION = new JavaType(
			"de.viadee.lib.acceptancetests.api.AbstractCriterion");
	private static final JavaType ABSTRACT_LINK = new JavaType(
			"de.viadee.lib.acceptancetests.api.AbstractLink");
	private static final JavaType ABSTRACT_TEXTFIELD = new JavaType(
			"de.viadee.lib.acceptancetests.api.AbstractTextfield");
	private static final JavaType ABSTRACT_TABLE = new JavaType(
			"de.viadee.lib.acceptancetests.api.AbstractTable");
	private static final JavaType STRING = new JavaType("java.lang.String", 0,
			null, null, null);
	private static final JavaType VARARG_OF_STRING = new JavaType(
			"java.lang.String", 1, DataType.TYPE, null, null);
	private static final JavaType CLICKABLE = new JavaType(
			"de.viadee.lib.acceptancetests.api.Clickable");

	private static final AnnotatedJavaType ABSTRACT_CONDITION_WITHOUT_ANNOTATIONS = new AnnotatedJavaType(
			ABSTRACT_CONDITION, null);
	private static final AnnotatedJavaType ABSTRACT_TEXTFIELD_WITHOUT_ANNOTATIONS = new AnnotatedJavaType(
			ABSTRACT_TEXTFIELD, null);
	private static final AnnotatedJavaType VARARG_OF_STRING_WITHOUT_ANNOTATIONS = new AnnotatedJavaType(
			VARARG_OF_STRING, null);
	private static final AnnotatedJavaType CLICKABLE_WITHOUT_ANNOTATIONS = new AnnotatedJavaType(
			CLICKABLE, null);
	private static final AnnotatedJavaType STRING_WITHOUT_ANNOTATIONS = new AnnotatedJavaType(
			STRING, null);
	
	private AcceptanceTestsUtils _acceptanceTestsUtils;
	
	public AcceptanceTestGroupMetadata(String identifier, JavaType aspectName, PhysicalTypeMetadata governorPhysicalTypeMetadata, AcceptanceTestsUtils acceptanceTestsUtils) {
		super(identifier, aspectName, governorPhysicalTypeMetadata);
		Assert.isTrue(isValid(identifier), "Metadata identification string '" + identifier + "' does not appear to be a valid");
		Assert.notNull(acceptanceTestsUtils, "Acceptance test utils required");
		_acceptanceTestsUtils = acceptanceTestsUtils;
		
		if (!isValid()) {
			return;
		}

		builder.addMethod(new DefaultMethodMetadata(identifier,
				Modifier.FINAL, new JavaSymbolName(
				"applicationDriver"), ABSTRACT_APPLICATION_DRIVER, parameters(), parameterNames(),
				null, null, "return new de.viadee.lib.acceptancetests.impl.selenium.SeleniumApplicationDriver(applicationBaseUrl());"));

		builder.addMethod(new DefaultMethodMetadata(identifier,
			Modifier.FINAL, new JavaSymbolName(
			"setUpApplicationDriver"), VOID_PRIMITIVE, parameters(), parameterNames(),
			annotations(new JavaType("org.junit.Before")), throwsException(), "applicationDriver().setUp();"));

		builder.addMethod(new DefaultMethodMetadata(identifier,
			Modifier.FINAL, new JavaSymbolName(
			"tearDownApplicationDriver"), VOID_PRIMITIVE, parameters(), parameterNames(),
			annotations(new JavaType("org.junit.After")), throwsException(), "applicationDriver().tearDown();"));

		builder.addMethod(newAcceptanceTestGroupMethod(
			identifier,
			STRING,
			"applicationBaseUrl",
			parameters(),
			parameterNames(),
			String.format("return \"http://localhost:8080/%s/\";", applicationContextName())));

		builder.addMethod(newAcceptanceTestGroupMethod(
			identifier,
			VOID_PRIMITIVE,
			"assertThat",
			parameters(ABSTRACT_CONDITION_WITHOUT_ANNOTATIONS),
			parameterNames("abstractCondition"),
			"org.junit.Assert.assertTrue(abstractCondition.failureMessage(), abstractCondition.value());"));

		builder.addMethod(newAcceptanceTestGroupMethod(
			identifier,
			ABSTRACT_BUTTON,
			"buttonWithCaption",
			parameters(STRING_WITHOUT_ANNOTATIONS),
			parameterNames("caption"),
			"return applicationDriver().buttonWithCaption(caption);"));

		builder.addMethod(newAcceptanceTestGroupMethod(
			identifier,
			ABSTRACT_BUTTON,
			"buttonWithAltText",
			parameters(STRING_WITHOUT_ANNOTATIONS),
			parameterNames("altText"),
			"return applicationDriver().buttonWithAltText(altText);"));

		builder.addMethod(newAcceptanceTestGroupMethod(
			identifier,
			VOID_PRIMITIVE,
			"typeTextIntoTextfield",
			parameters(STRING_WITHOUT_ANNOTATIONS, ABSTRACT_TEXTFIELD_WITHOUT_ANNOTATIONS),
			parameterNames("text", "textfield"),
			"applicationDriver().typeTextIntoTextfield(text, textfield);"));

		builder.addMethod(newAcceptanceTestGroupMethod(
			identifier,
			ABSTRACT_TEXTFIELD,
			"textfieldWithLabel",
			parameters(STRING_WITHOUT_ANNOTATIONS),
			parameterNames("label"),
			"return applicationDriver().textfieldWithLabel(label);"));

		builder.addMethod(newAcceptanceTestGroupMethod(
			identifier,
			VOID_PRIMITIVE,
			"clickClickableAndWaitForNextPage",
			parameters(CLICKABLE_WITHOUT_ANNOTATIONS),
			parameterNames("clickable"),
			"applicationDriver().clickClickableAndWaitForNextPage(clickable);"));

		builder.addMethod(newAcceptanceTestGroupMethod(
			identifier,
			ABSTRACT_LINK,
			"linkWithCaption",
			parameters(STRING_WITHOUT_ANNOTATIONS),
			parameterNames("caption"),
			"return applicationDriver().linkWithCaption(caption);"));

		builder.addMethod(newAcceptanceTestGroupMethod(
			identifier,
			VOID_PRIMITIVE,
			"open",
			parameters(STRING_WITHOUT_ANNOTATIONS),
			parameterNames("url"),
			"applicationDriver().open(url);"));

		builder.addMethod(newAcceptanceTestGroupMethod(
			identifier,
			VOID_PRIMITIVE,
			"assertTextIsPresent",
			parameters(STRING_WITHOUT_ANNOTATIONS),
			parameterNames("text"),
			"org.junit.Assert.assertTrue(String.format(\"The text \\\"%s\\\" could not be found!\", text), applicationDriver().isTextPresent(text));"));

		StringBuilder waitForHalfASecondBody = new StringBuilder();
		waitForHalfASecondBody.append("try {\r");
		waitForHalfASecondBody.append("  Thread.sleep(500);\r");
		waitForHalfASecondBody.append("} catch (InterruptedException e) {\r");
		waitForHalfASecondBody.append("  // Do nothing\r");
		waitForHalfASecondBody.append("}\r");

		builder.addMethod(newAcceptanceTestGroupMethod(
			identifier,
			VOID_PRIMITIVE,
			"waitForHalfASecond",
			parameters(),
			parameterNames(),
			waitForHalfASecondBody.toString()));

		builder.addMethod(newAcceptanceTestGroupMethod(
			identifier,
			ABSTRACT_CRITERION,
			"entryInColumnEqualsValue",
			parameters(STRING_WITHOUT_ANNOTATIONS, STRING_WITHOUT_ANNOTATIONS),
			parameterNames("columnName", "columnEntry"),
			"return applicationDriver().entryInColumnEqualsValue(columnName, columnEntry);"));

		builder.addMethod(newAcceptanceTestGroupMethod(
			identifier,
			ABSTRACT_TABLE,
			"tableWithColumns",
			parameters(VARARG_OF_STRING_WITHOUT_ANNOTATIONS),
			parameterNames("columnHeaders"),
			"return applicationDriver().tableWithColumns(columnHeaders);"));

		// Create a representation of the desired output ITD
		itdTypeDetails = builder.build();
	}
	
	private String applicationContextName() {
		return _acceptanceTestsUtils.projectMetadata().getProjectName();
	}

	private static DefaultMethodMetadata newAcceptanceTestGroupMethod(
			String identifier,
			JavaType returnType,
			String methodName,
			List<AnnotatedJavaType> parameters,
			List<JavaSymbolName> parameterNames,
			String body) {
		return new DefaultMethodMetadata(identifier, Modifier.FINAL,
				new JavaSymbolName(methodName), returnType, parameters,
				parameterNames,	null, null, body);
	}

	private static List<AnnotationMetadata> annotations(JavaType... annotationTypes) {
		List<AnnotationMetadata> result = new ArrayList<AnnotationMetadata>();
		for(JavaType annotationType : annotationTypes) {
			result.add(new DefaultAnnotationMetadata(annotationType, new ArrayList<AnnotationAttributeValue<?>>()));
		}
		return result;
	}

	private static List<JavaType> throwsException() {
		List<JavaType> result = new ArrayList<JavaType>();
		result.add(new JavaType("java.lang.Exception"));
		return result;
	}

	private static List<JavaSymbolName> parameterNames(String... parameterNames) {
		List<JavaSymbolName> result = new ArrayList<JavaSymbolName>();
		for (String parameterName : parameterNames) {
			result.add(new JavaSymbolName(parameterName));
		}
		return result;
	}

	private static List<AnnotatedJavaType> parameters(
			AnnotatedJavaType... annotatedParameterTypes) {
		List<AnnotatedJavaType> parameters = new ArrayList<AnnotatedJavaType>();
		for (AnnotatedJavaType annotatedParameterType : annotatedParameterTypes) {
			parameters.add(annotatedParameterType);
		}
		return parameters;
	}

	public String toString() {
		ToStringCreator tsc = new ToStringCreator(this);
		tsc.append("identifier", getId());
		tsc.append("valid", valid);
		tsc.append("aspectName", aspectName);
		tsc.append("destinationType", destination);
		tsc.append("governor", governorPhysicalTypeMetadata.getId());
		tsc.append("itdTypeDetails", itdTypeDetails);
		return tsc.toString();
	}

	public static final String getMetadataIdentiferType() {
		return PROVIDES_TYPE;
	}
	
	public static final String createIdentifier(JavaType javaType, Path path) {
		return PhysicalTypeIdentifierNamingUtils.createIdentifier(PROVIDES_TYPE_STRING, javaType, path);
	}

	public static final JavaType getJavaType(String metadataIdentificationString) {
		return PhysicalTypeIdentifierNamingUtils.getJavaType(PROVIDES_TYPE_STRING, metadataIdentificationString);
	}

	public static final Path getPath(String metadataIdentificationString) {
		return PhysicalTypeIdentifierNamingUtils.getPath(PROVIDES_TYPE_STRING, metadataIdentificationString);
	}

	public static boolean isValid(String metadataIdentificationString) {
		return PhysicalTypeIdentifierNamingUtils.isValid(PROVIDES_TYPE_STRING, metadataIdentificationString);
	}
	
}
