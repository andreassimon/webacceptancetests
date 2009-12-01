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

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.springframework.roo.process.manager.MutableFile;

public class MutablePomFile implements MutableFile {

	public InputStream getInputStream() {
		return getClass().getClassLoader().getResourceAsStream("test-pom.xml");
	}

	public OutputStream getOutputStream() {
		return new ByteArrayOutputStream();
	}

	public String getCanonicalPath() { return null; }

}
