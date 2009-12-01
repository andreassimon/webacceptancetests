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
import java.util.List;
import java.util.SortedSet;

import org.springframework.roo.file.monitor.event.FileDetails;
import org.springframework.roo.process.manager.FileManager;
import org.springframework.roo.project.Path;
import org.springframework.roo.project.PathResolver;
import org.springframework.roo.shell.Converter;
import org.springframework.roo.shell.MethodTarget;
import org.springframework.roo.support.lifecycle.ScopeDevelopmentShell;

@ScopeDevelopmentShell
public class StoryGroupConverter implements Converter {

	private static final int STORY_FILE_SUFFIX_LENGTH = "Stories.java".length();
	private FileManager _fileManager;
	private AcceptanceTestsUtils _acceptanceTestsUtils;
	private PathResolver _pathResolver;

	public StoryGroupConverter(FileManager fileManager, AcceptanceTestsUtils acceptanceTestsUtils) {
		super();
		_fileManager = fileManager;
		_acceptanceTestsUtils = acceptanceTestsUtils;
		_pathResolver = _acceptanceTestsUtils.projectMetadata().getPathResolver();
	}

	public boolean supports(Class<?> requiredType, String optionContext) {
		return requiredType.equals(StoryGroup.class);
	}

	public Object convertFromText(String value, Class<?> requiredType,
			String optionContext) {
		if(value == null || value.length() == 0) {
			return StoryGroup.GENERAL;
		}
		return new StoryGroup(value);
	}

	public boolean getAllPossibleValues(List<String> completions,
			Class<?> requiredType, String existingData, String optionContext,
			MethodTarget target) {
		completions.add("General");
		addExistingStoryGroups(completions);
		return true;
	}

	private void addExistingStoryGroups(List<String> completions) {
		if (_acceptanceTestsUtils.projectMetadata() == null) {
			return;
		}

		SortedSet<FileDetails> candidates = _fileManager
				.findMatchingAntPath(storyGroupSearchPath());

		for (FileDetails fileIdentifier : candidates) {
			completions.add(getGroupNameFromFileIdentifier(fileIdentifier));
		}
	}

	private String getGroupNameFromFileIdentifier(FileDetails fileIdentifier) {
		String relativeSegment = _pathResolver.getRelativeSegment(fileIdentifier.getCanonicalPath());
		int groupNameBeginIndex = relativeSegment.lastIndexOf(File.separator) + 1;
		return relativeSegment.substring(groupNameBeginIndex, relativeSegment.length() - STORY_FILE_SUFFIX_LENGTH);
	}

	private String storyGroupSearchPath() {
		return _pathResolver.getRoot(Path.SRC_TEST_JAVA)
				+ File.separatorChar
				+ _acceptanceTestsUtils.acceptancetestsPackageName().replace(
						".", File.separator) + File.separator + "*Stories.java";
	}
}
