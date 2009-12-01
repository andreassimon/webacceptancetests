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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.roo.support.util.Assert;
import org.w3c.dom.Element;

import com.viadee.acceptancetests.roo.addon.Configuration.Parameter;

/**
 * @author Andreas Simon
 *
 */
public class Execution {
	private final String _id;
	private final String _phase;
	private final Configuration _configuration;
	private final List<String> _goals;

	public Execution(String id, String phase, Configuration configuration, String... goals) {
		Assert.notNull(id, "execution id must be specified");
		Assert.notEmpty(goals, "at least one goal must be specified");
		_id = id;
		_phase = phase;
		_configuration = configuration;
		_goals = Collections.unmodifiableList(Arrays.asList(goals));
	}

	public String getId() {
		return _id;
	}

	public String getPhase() {
		return _phase;
	}
	
	public Configuration getConfiguration() {
		return _configuration;
	}

	public List<String> getGoals() {
		return _goals;
	}

	void serializeExecutionConfiguration(Element executionElement, PluginDependency pluginDependency) {
		Configuration executionConfiguration = getConfiguration();
		if(executionConfiguration != null) {
			Element configurationElement = pluginDependency._pomWrapper.addChildElementWithName(executionElement, "configuration");
			for(Parameter parameter: executionConfiguration.getParameters()) {
				parameter.addParameterElementToElement(configurationElement, pluginDependency._pomWrapper);
			}
		}
	}

}
