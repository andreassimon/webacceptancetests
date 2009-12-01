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



public class StoryGroup implements Comparable<StoryGroup> {

	public static final StoryGroup GENERAL = new StoryGroup("General");
	
	private String _name;

	public StoryGroup(String name) {
		_name = name;
	}

	public int compareTo(StoryGroup other) {
		return getName().compareTo(other.getName());
	}

	public String getName() {
		return _name;
	}

	@Override
	public String toString() {
		return _name;
	}

	public String getKey() {
		return _name;
	}
	
}
