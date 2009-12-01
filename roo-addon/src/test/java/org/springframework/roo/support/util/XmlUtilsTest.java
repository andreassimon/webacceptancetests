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
package org.springframework.roo.support.util;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import org.junit.Test;
import org.springframework.roo.model.JavaPackage;
import org.springframework.roo.model.JavaSymbolName;
import org.springframework.roo.project.Dependency;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XmlUtilsTest {

  @Test
  public void findElementsShouldWork() {
    Document dependencyDoc;
    try {
      InputStream documentInputStream = new ByteArrayInputStream(documentText()
          .getBytes());
      dependencyDoc = XmlUtils.getDocumentBuilder().parse(documentInputStream);
    } catch (Exception e) {
      throw new IllegalStateException(e);
    }

    Element dependenciesElement = (Element) dependencyDoc.getFirstChild();

    List<Element> dependencyElements = XmlUtils.findElements(
        "/dependencies/acceptanceTests/dependency", dependenciesElement);
    assertEquals(2, dependencyElements.size());
    
    for(Element dependencyElement : dependencyElements) {
      Dependency dependency = new Dependency(dependencyElement);
      assertEquals(new JavaPackage("org.springframework"), dependency.getGroupId());
      assertEquals(new JavaSymbolName("org.springframework.web"), dependency.getArtifactId());
      assertEquals("3.0", dependency.getVersionId());
    }
  }

  private String documentText() {
    return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>"
        + "<dependencies>"
        + "  <acceptanceTests>"
        + "    <dependency org=\"org.springframework\" name=\"org.springframework.web\" rev=\"3.0\" />"
        + "    <dependency org=\"org.springframework\" name=\"org.springframework.web\" rev=\"3.0\" />"
        + "  </acceptanceTests>"
        + "</dependencies>";
  }

}
