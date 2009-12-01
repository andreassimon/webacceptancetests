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

import org.springframework.roo.support.util.XmlUtils;
import org.w3c.dom.Element;

public class PluginDependency {

	POMWrapper _pomWrapper;
	Element _domElement;

	public PluginDependency(POMWrapper pomWrapper, Element domElement) {
		super();
		_pomWrapper = pomWrapper;
		_domElement = domElement;
	}

	public Element getDomElement() {
		return _domElement;
	}

	void addOrUpdateExecution(Execution execution) {
		Element executionsElement = findOrCreateExecutionsElement();
		Element executionElement = findOrCreateExecutionElement(
				executionsElement, execution);
		updateExecutionProperties(executionElement, execution);
	}

	private Element findOrCreateExecutionElement(Element executionsElement,
			Execution execution) {
		Element executionElement = findExecutionElement(executionsElement,
				execution);
		if (executionElement == null) {
			executionElement = _pomWrapper.addChildElementWithName(getDomElement(), "execution");
			executionsElement.appendChild(executionElement);
		}
		return executionElement;
	}

	private Element findExecutionElement(Element executionsElement,
			Execution execution) {
		return XmlUtils.findFirstElement(String.format(
				"execution[id/text()=\"%s\"]", execution.getId()),
				executionsElement);
	}

	private Element findOrCreateExecutionsElement() {
		return _pomWrapper.getExistingOrNewChild(_domElement, "executions");
	}

	private void updateExecutionProperties(Element executionElement,
			com.viadee.acceptancetests.roo.addon.Execution execution) {
		removeAllChildNodes(executionElement);

		_pomWrapper.addChildToParentWithNameAndTextContent(executionElement,
				"id", execution.getId());

		_pomWrapper.addChildToParentWithNameAndTextContent(executionElement,
				"phase", execution.getPhase());

		Element goalsElement = _pomWrapper.createElement("goals");
		for (String goal : execution.getGoals()) {
			_pomWrapper.addChildToParentWithNameAndTextContent(goalsElement,
					"goal", goal);
		}
		executionElement.appendChild(goalsElement);
		
		execution.serializeExecutionConfiguration(executionElement, this);
	}

	private void removeAllChildNodes(Element element) {
		while(element.hasChildNodes()) {
			element.removeChild(element.getFirstChild());
		}
	}

	public void setConfiguration(Configuration configuration) {
		Element configurationElement = findOrCreateConfigurationElement();
		removeAllChildNodes(configurationElement);
		for(Configuration.Parameter parameter : configuration.getParameters()) {
			parameter.addParameterElementToElement(configurationElement, _pomWrapper);
		}
	}

	private Element findOrCreateConfigurationElement() {
		return _pomWrapper.getExistingOrNewChild(_domElement, "configuration");
	}
	
}
