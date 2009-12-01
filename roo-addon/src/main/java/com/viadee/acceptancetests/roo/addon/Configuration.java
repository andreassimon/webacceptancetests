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

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.w3c.dom.Element;

public class Configuration {

	static class Parameter {

		private final String _key;
		private final Object _value;

		public Parameter(String key, Object value) {
			super();
			_key = key;
			_value = value;
		}

		public Parameter(String key, Parameter... parameters) {
			super();
			_key = key;
			_value = parameters;
		}

		public String getKey() {
			return _key;
		}

		public Object getValue() {
			return _value;
		}

		void addParameterElementToElement(Element element, POMWrapper pomWrapper) {
			if (_value instanceof Parameter) {
				Element keyElement = pomWrapper.addChildElementWithName(
						element, _key);
				((Parameter) _value).addParameterElementToElement(keyElement,
						pomWrapper);
			} else if (_value instanceof Parameter[]) {
				Element keyElement = pomWrapper.addChildElementWithName(
						element, _key);
				Parameter[] parameterArray = (Parameter[]) _value;
				for (Parameter parameter : parameterArray) {
					parameter.addParameterElementToElement(keyElement,
							pomWrapper);
				}
			} else {
				pomWrapper.addChildToParentWithNameAndTextContent(element,
						_key, _value.toString());
			}
		}

	}

	private List<Parameter> _parameterList = new LinkedList<Parameter>();

	public Configuration(Parameter... parameters) {
		for (Parameter parameter : parameters) {
			_parameterList.add(parameter);
		}
	}

	void addParameter(Parameter parameter) {
		_parameterList.add(parameter);
	}

	public List<Parameter> getParameters() {
		return Collections.unmodifiableList(_parameterList);
	}

}
