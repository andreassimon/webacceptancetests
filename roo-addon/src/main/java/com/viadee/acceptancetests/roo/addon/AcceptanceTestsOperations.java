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

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.roo.classpath.PhysicalTypeCategory;
import org.springframework.roo.classpath.PhysicalTypeIdentifier;
import org.springframework.roo.classpath.PhysicalTypeMetadata;
import org.springframework.roo.classpath.details.ClassOrInterfaceTypeDetails;
import org.springframework.roo.classpath.details.DefaultClassOrInterfaceTypeDetails;
import org.springframework.roo.classpath.details.DefaultMethodMetadata;
import org.springframework.roo.classpath.details.MethodMetadata;
import org.springframework.roo.classpath.details.MutableClassOrInterfaceTypeDetails;
import org.springframework.roo.classpath.details.annotations.AnnotationAttributeValue;
import org.springframework.roo.classpath.details.annotations.AnnotationMetadata;
import org.springframework.roo.classpath.details.annotations.DefaultAnnotationMetadata;
import org.springframework.roo.classpath.operations.ClasspathOperations;
import org.springframework.roo.metadata.MetadataService;
import org.springframework.roo.model.JavaSymbolName;
import org.springframework.roo.model.JavaType;
import org.springframework.roo.process.manager.FileManager;
import org.springframework.roo.project.Dependency;
import org.springframework.roo.project.Path;
import org.springframework.roo.project.PathResolver;
import org.springframework.roo.project.ProjectOperations;
import org.springframework.roo.support.lifecycle.ScopeDevelopment;
import org.springframework.roo.support.util.Assert;
import org.springframework.roo.support.util.TemplateUtils;
import org.springframework.roo.support.util.XmlUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.viadee.acceptancetests.roo.addon.Configuration.Parameter;

/**
 * @author Andreas Simon
 * 
 */
@ScopeDevelopment
public class AcceptanceTestsOperations {

	Logger logger = Logger.getLogger(AcceptanceTestsOperations.class.getName());

	private FileManager _fileManager;
	private PathResolver _pathResolver;
	private MetadataService _metadataService;
	private ProjectOperations _projectOperations;

	private ClasspathOperations _classpathOperations;
	private AcceptanceTestsUtils _acceptanceTestsUtils;

	public AcceptanceTestsOperations(FileManager fileManager,
			PathResolver pathResolver, MetadataService metadataService,
			ProjectOperations projectOperations,
			ClasspathOperations classpathOperations, AcceptanceTestsUtils acceptanceTestsUtils) {
		Assert.notNull(fileManager, "File manager required");
		Assert.notNull(pathResolver, "Path resolver required");
		Assert.notNull(metadataService, "Metadata service required");
		Assert.notNull(projectOperations, "Project operations required");
		Assert.notNull(classpathOperations, "Classpath operations required");
		_fileManager = fileManager;
		_pathResolver = pathResolver;
		_metadataService = metadataService;
		_projectOperations = projectOperations;
		_classpathOperations = classpathOperations;
		_acceptanceTestsUtils = acceptanceTestsUtils;
	}

	public void newAcceptanceTest(String story, String storyGroup)
			throws IOException {
		updateDependencies();
		bindAcceptanceTestsIntoMavenLifecycle();
		insertStoryIntoStoryGroup(story, storyGroup);
	}

	private void updateDependencies() {
		for (Element dependency : loadDependencies()) {
			_projectOperations.dependencyUpdate(new Dependency(dependency));
		}
	}

	protected List<Element> loadDependencies() {
		InputStream templateInputStream = TemplateUtils.getTemplate(getClass(),
				"dependencies.xml");
		Assert.notNull(templateInputStream,
				"Could not acquire dependencies.xml file");
		Document dependencyDoc;
		try {
			dependencyDoc = XmlUtils.getDocumentBuilder().parse(
					templateInputStream);
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	
		Element dependenciesElement = (Element) dependencyDoc.getFirstChild();
	
		return XmlUtils
				.findElements("/dependencies/acceptanceTests/dependency",
						dependenciesElement);
	}

	private void bindAcceptanceTestsIntoMavenLifecycle() {
		POMWrapper pomWrapper = new POMWrapper(_pathResolver, _fileManager);

		addOrUpdateJettyPlugin(pomWrapper);
		addOrUpdateSeleniumPlugin(pomWrapper);
		addOrUpdateSurefirePlugin(pomWrapper);

		pomWrapper.serialize();
	}

	private void addOrUpdateSurefirePlugin(POMWrapper pomWrapper) {
		PluginDependency surefirePluginDependency = pomWrapper
				.findOrCreatePluginDependency(new Dependency(
						"org.apache.maven.plugins", "maven-surefire-plugin",
						"2.4.3"));

		Configuration surefireConfiguration = new Configuration(new Parameter(
				"junitArtifactName", "org.junit:com.springsource.org.junit"),
				new Parameter("excludes",
						new Parameter("exclude", "**/*_Roo_*"), new Parameter(
								"exclude", "**/acceptancetests/*")));
		surefirePluginDependency.setConfiguration(surefireConfiguration);

		Configuration acceptancetestConfiguration = new Configuration(
				new Parameter("excludes", new Parameter("exclude", "none")),
				new Parameter("includes", new Parameter("include",
						"**/acceptancetests/*")), new Parameter(
						"reportsDirectory",
						"${project.build.directory}/acceptancetest-reports"));
		surefirePluginDependency.addOrUpdateExecution(new Execution(
				"execute-acceptancetests", POMWrapper.INTEGRATION_TEST,
				acceptancetestConfiguration, "test"));
	}

	private void addOrUpdateSeleniumPlugin(POMWrapper pomWrapper) {
		PluginDependency seleniumPluginDependency = pomWrapper
				.findOrCreatePluginDependency(new Dependency(
						"org.codehaus.mojo", "selenium-maven-plugin", "1.0"));
		Configuration startSeleniumConfiguration = new Configuration();
		startSeleniumConfiguration.addParameter(new Parameter("background",
				true));
		startSeleniumConfiguration
				.addParameter(new Parameter("logOutput", true));
		startSeleniumConfiguration.addParameter(new Parameter("multiWindow",
				true));

		seleniumPluginDependency.addOrUpdateExecution(new Execution(
				"start-selenium", POMWrapper.PRE_INTEGRATION_TEST,
				startSeleniumConfiguration, "start-server"));

		seleniumPluginDependency.addOrUpdateExecution(new Execution(
				"stop-selenium", POMWrapper.PRE_INTEGRATION_TEST, null,
				"stop-server"));
	}

	private void addOrUpdateJettyPlugin(POMWrapper pomWrapper) {
		PluginDependency jettyPluginDependency = pomWrapper
				.findOrCreatePluginDependency(new Dependency(
						"org.mortbay.jetty", "maven-jetty-plugin", "6.1.10"));
		Configuration containerConfiguration = new Configuration(new Parameter(
				"scanIntervalSeconds", 10), new Parameter("stopKey", "foo"),
				new Parameter("stopPort", "9999"));
		jettyPluginDependency.setConfiguration(containerConfiguration);

		Configuration startConfiguration = new Configuration(
			new Parameter("scanIntervalSeconds", 0),
			new Parameter("daemon", "true"));

		jettyPluginDependency.addOrUpdateExecution(
			new Execution(
				"start-jetty",
				POMWrapper.PRE_INTEGRATION_TEST,
				startConfiguration,
				"run"));
		
		jettyPluginDependency.addOrUpdateExecution(
			new Execution(
				"stop-jetty",
				POMWrapper.POST_INTEGRATION_TEST,
				null,
				"stop"));
	}

	private List<AnnotationMetadata> annotations(JavaType... annotationTypes) {
		List<AnnotationMetadata> result = new ArrayList<AnnotationMetadata>();
		for(JavaType annotationType : annotationTypes) {
			result.add(new DefaultAnnotationMetadata(annotationType, new ArrayList<AnnotationAttributeValue<?>>()));
		}
		return result;
	}

	private void insertStoryIntoStoryGroup(String story, String storyGroup)
			throws IOException {
		JavaType fullyQualifiedTestName = new JavaType(
				_acceptanceTestsUtils.acceptancetestsPackageName() + "." + storyGroup + "Stories");
		String storyGroupIdentifier = PhysicalTypeIdentifier.createIdentifier(
				fullyQualifiedTestName, Path.SRC_TEST_JAVA);

		PhysicalTypeMetadata storyGroupMetadata = (PhysicalTypeMetadata) _metadataService
				.get(storyGroupIdentifier);
		if (storyGroupMetadata == null) {
			List<MethodMetadata> methods = new ArrayList<MethodMetadata>();

			methods.add(newTestMethod(storyGroupIdentifier, story));

			List<AnnotationMetadata> annotations = annotations(new JavaType("com.viadee.acceptancetests.roo.addon.RooAcceptanceTestGroup"));
			ClassOrInterfaceTypeDetails details = new DefaultClassOrInterfaceTypeDetails(
					storyGroupIdentifier, fullyQualifiedTestName,
					Modifier.PUBLIC, PhysicalTypeCategory.CLASS, null, null,
					methods, null, null, null, annotations, null);
			_classpathOperations.generateClassFile(details);
		} else {
			MutableClassOrInterfaceTypeDetails storyGroupDetails = (MutableClassOrInterfaceTypeDetails) storyGroupMetadata
					.getPhysicalTypeDetails();

			List<? extends MethodMetadata> methods = storyGroupDetails.getDeclaredMethods();
			for(MethodMetadata methodMetadata : methods)  {
				if(methodMetadata.getMethodName().getSymbolName().equals(methodNameFromStory(story))) {
					throw new IllegalStateException(String.format("There already is an acceptance test with the story \"%s\" in the group \"%s\"", methodNameFromStory(story), storyGroup));
				}
			}
			
			storyGroupDetails.addMethod(newTestMethod(storyGroupIdentifier,	story));
		}
	}

	private MethodMetadata newTestMethod(String storyGroupIdentifier,
			String story) {
		List<AnnotationMetadata> methodAnnotations = new ArrayList<AnnotationMetadata>();
		methodAnnotations
				.add(new DefaultAnnotationMetadata(new JavaType(
						"org.junit.Test"),
						new ArrayList<AnnotationAttributeValue<?>>()));

		MethodMetadata method = new DefaultMethodMetadata(storyGroupIdentifier,
				Modifier.PUBLIC, new JavaSymbolName(methodNameFromStory(story)),
				VOID_PRIMITIVE, null, null, methodAnnotations, null, String.format("org.junit.Assert.fail(\"Implement the acceptance test for story \\\"%s\\\" here.\");", story));
		return method;
	}

	private String methodNameFromStory(String story) {
		String[] words = story.split(" ");
		StringBuilder methodName = new StringBuilder();
		methodName.append(Character.toLowerCase(words[0].charAt(0)));
		for (String word : words) {
			methodName.append(Character.toUpperCase(word.charAt(0)));
			methodName.append(word.substring(1));
		}
		methodName.deleteCharAt(1);
		return methodName.toString();
	}

}
