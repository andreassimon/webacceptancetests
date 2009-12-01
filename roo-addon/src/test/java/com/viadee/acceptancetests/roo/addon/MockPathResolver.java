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

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.roo.project.Path;
import org.springframework.roo.project.PathResolver;

public class MockPathResolver implements PathResolver {

	public String getIdentifier(Path path, String relativePath) {
		if("ROOT".equals(path.toString()) && "/pom.xml".equals(relativePath)) {
			return "ROOT/pom.xml";
		}
		try {
			return new File(path.toString(), relativePath).getCanonicalPath();
		} catch (IOException e) {
			throw new IllegalArgumentException(String.format("Could not create identifier for path \"%s\" and relative path \"%s\"", path, relativePath), e);
		}
	}

	public String getFriendlyName(String identifier) { return null; }

	public List<Path> getNonSourcePaths() { return null; }

	public Path getPath(String identifier) { return null; }

	public List<Path> getPaths() { return null; }

	public String getRelativeSegment(String identifier) { return null; }

	public String getRoot(Path path) { return null; }

	public List<Path> getSourcePaths() { return null; }

}