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
/**
 * 
 */
package com.viadee.acceptancetests.roo.addon;

import org.springframework.roo.process.manager.FileManager;
import org.springframework.roo.process.manager.MutableFile;
import org.springframework.roo.project.Dependency;
import org.springframework.roo.project.Path;
import org.springframework.roo.project.PathResolver;
import org.springframework.roo.support.util.XmlUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class POMWrapper {
	public static final String PRE_INTEGRATION_TEST = "pre-integration-test";
	public static final String INTEGRATION_TEST = "integration-test";
	public static final String POST_INTEGRATION_TEST = "post-integration-test";

	private MutableFile _mutablePomFile;
	private String _pomIdentifier;
	private Document _document;

	public POMWrapper(PathResolver pathResolver, FileManager fileManager) {
		_pomIdentifier = pathResolver.getIdentifier(Path.ROOT, "/pom.xml");

		if (!fileManager.exists(_pomIdentifier)) {
			throw new IllegalStateException(String.format(
					"Could not open POM '%s'", _pomIdentifier));
		}

		_mutablePomFile = fileManager.updateFile(_pomIdentifier);
		_document = parsePOM(_mutablePomFile);
	}

	private Document parsePOM(MutableFile mutablePomFile) {
		Document document;
		try {
			document = XmlUtils.getDocumentBuilder().parse(mutablePomFile.getInputStream());
		} catch (Exception ex) {
			throw new IllegalStateException(String.format(
					"Could not open POM '%s'", _pomIdentifier), ex);
		}
		return document;
	}

	public void serialize() {
		XmlUtils.writeXml(_mutablePomFile.getOutputStream(), _document);
	}

	public Element getRootElement() {
		return (Element) _document.getFirstChild();
	}

	public Element createElement(String tagName) {
		return _document.createElement(tagName);
	}

	public Element findFirstElementByXPath(String xPath) {
		return XmlUtils.findFirstElement(xPath, getRootElement());
	}

	public PluginDependency findOrCreatePluginDependency(Dependency dependency) {
		Element pluginDependencyElement = findPluginDependencyElement(dependency);
		if (pluginDependencyElement == null) {
			return new PluginDependency(this,
					insertNewPluginDependencyElement(dependency));
		} else {
			return checkOrSetVersionId(pluginDependencyElement, dependency);
		}
	}

	private PluginDependency checkOrSetVersionId(
			Element pluginDependencyElement, Dependency dependency) {
		Element versionElement = XmlUtils.findFirstElement("version",
				pluginDependencyElement);
		if (versionElement == null) {
			addChildToParentWithNameAndTextContent(pluginDependencyElement,
					"version", dependency.getVersionId());
			return new PluginDependency(this, pluginDependencyElement);
		} else {
			if (versionElement.getTextContent().equals(
					dependency.getVersionId())) {
				return new PluginDependency(this, pluginDependencyElement);
			} else {
				throw new IllegalArgumentException(
						"There is a Plugin with a different version installed!");
			}
		}
	}

	private Element insertNewPluginDependencyElement(Dependency dependency) {
		Element pluginDependencyElement = newPluginDependencyElement(dependency);
		Element pluginsElement = findFirstElementByXPath("/project/build/plugins");
		pluginsElement.appendChild(pluginDependencyElement);
		return pluginDependencyElement;
	}

	private Element findPluginDependencyElement(Dependency dependency) {
		return findFirstElementByXPath(String
				.format(
						"/project/build/plugins/plugin[groupId/text()=\"%s\" and artifactId/text()=\"%s\"]",
						dependency.getGroupId(), dependency.getArtifactId()));
	}

	private Element newPluginDependencyElement(Dependency dependency) {
		Element pluginElement = createElement("plugin");
		
		addChildToParentWithNameAndTextContent(pluginElement, "groupId", dependency.getGroupId()
				.getFullyQualifiedPackageName());
		addChildToParentWithNameAndTextContent(pluginElement, "artifactId", dependency.getArtifactId().getSymbolName());
		addChildToParentWithNameAndTextContent(pluginElement, "version", dependency.getVersionId());
		
		return pluginElement;
	}

	Element addChildToParentWithNameAndTextContent(Element parent, String tagName,
			String textContent) {
		Element newChild = addChildElementWithName(parent, tagName);
		newChild.setTextContent(textContent);
		return newChild;
	}

	Element addChildElementWithName(Element parent, String tagName) {
		Element goalElement = createElement(tagName);
		parent.appendChild(goalElement);
		return goalElement;
	}

	Element getExistingOrNewChild(Element parent, String tagName) {
		Element executionsElement = XmlUtils.findFirstElementByName(
				tagName, parent);
		if (executionsElement == null) {
			executionsElement = addChildElementWithName(parent, tagName);
		}
		return executionsElement;
	}

}