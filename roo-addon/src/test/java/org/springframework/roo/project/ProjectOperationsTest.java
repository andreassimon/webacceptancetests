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
package org.springframework.roo.project;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.springframework.roo.addon.maven.AddOnJumpstartOperations;
import org.springframework.roo.addon.maven.ApplicationContextOperations;
import org.springframework.roo.addon.maven.MavenOperations;
import org.springframework.roo.metadata.MetadataItem;
import org.springframework.roo.metadata.MetadataService;
import org.springframework.roo.model.JavaPackage;
import org.springframework.roo.model.JavaSymbolName;
import org.springframework.roo.process.manager.FileManager;

public class ProjectOperationsTest {

  private ProjectOperations _projectOperations;

  @Before
  public void setUp() {
    _projectOperations = new MavenOperations(metadataService(),
        projectMetadataProvider(), fileManager(), pathResolver(),
        applicationContextOperations(), addOnJumpstartOperations());
  }

  private FileManager fileManager() {
    return mock(FileManager.class);
  }

  private PathResolver pathResolver() {
    return mock(PathResolver.class);
  }

  private ApplicationContextOperations applicationContextOperations() {
    return mock(ApplicationContextOperations.class);
  }

  private AddOnJumpstartOperations addOnJumpstartOperations() {
    return mock(AddOnJumpstartOperations.class);
  }

  private ProjectMetadataProvider _projectMetadataProvider;

  private ProjectMetadataProvider projectMetadataProvider() {
    if (_projectMetadataProvider == null) {
      _projectMetadataProvider = mock(ProjectMetadataProvider.class);
    }
    return _projectMetadataProvider;
  }

  MetadataService _metadataService;

  private MetadataService metadataService() {
    if (_metadataService == null) {
      _metadataService = mock(MetadataService.class);
      MetadataItem projectMetadataItem = projectMetadataItem();
      when(_metadataService.get(ProjectMetadata.getProjectIdentifier()))
          .thenReturn(projectMetadataItem);
    }
    return _metadataService;
  }

  private MetadataItem projectMetadataItem() {
    MetadataItem projectMetadataItem = mock(ProjectMetadata.class);
    return projectMetadataItem;
  }

  @Test
  public void testRemoveBuildPlugin() {
    _projectOperations.removeBuildPlugin(new JavaPackage("org.mortbay.jetty"),
        new JavaSymbolName("maven-jetty-plugin"), "6.1.10");

    verify(projectMetadataProvider()).removeBuildPluginDependency(
        (Dependency) any());
  }

  @Test
  public void testBuildPluginUpdate() {
    Execution execution = new Execution("execute-acceptance-tests", "run");
    Dependency dependency = new Dependency("org.mortbay.jetty",
        "maven-jetty-plugin", "6.1.10");
    _projectOperations.buildPluginUpdate(dependency, execution);
    verify(projectMetadataProvider()).addBuildPluginDependency(dependency, execution);
  }

}
