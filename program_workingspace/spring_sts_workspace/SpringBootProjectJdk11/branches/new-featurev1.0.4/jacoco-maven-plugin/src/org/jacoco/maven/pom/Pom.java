/*******************************************************************************
 * Copyright (c) 2009, 2018 Mountainminds GmbH & Co. KG and Contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    John Oliver, Marc R. Hoffmann, Jan Wloka - initial API and implementation
 *
 *******************************************************************************/
package org.jacoco.maven.pom;

import java.util.List;

import org.dom4j.Element;

/**
 * 新建的pom对象
 * 
 * @author hp
 *
 */
public class Pom {
	String encoding;
	Element project;
	String modelVersion;
	Parent parent;
	List<Dependency> dependencies;
	String artifactId;
	String name;

	/**
	 * @return
	 */
	public String getEncoding() {
		return encoding;
	}

	/**
	 * @param encoding
	 */
	public void setEncoding(final String encoding) {
		this.encoding = encoding;
	}

	/**
	 * @return
	 */
	public Element getProject() {
		return project;
	}

	/**
	 * @param project
	 */
	public void setProject(final Element project) {
		this.project = project;
	}

	/**
	 * @return
	 */
	public String getModelVersion() {
		return modelVersion;
	}

	/**
	 * @param modelVersion
	 */
	public void setModelVersion(final String modelVersion) {
		this.modelVersion = modelVersion;
	}

	/**
	 * @return
	 */
	public Parent getParent() {
		return parent;
	}

	/**
	 * @param parent
	 */
	public void setParent(final Parent parent) {
		this.parent = parent;
	}

	/**
	 * @return
	 */
	public List<Dependency> getDependencies() {
		return dependencies;
	}

	/**
	 * @param dependencies
	 */
	public void setDependencies(final List<Dependency> dependencies) {
		this.dependencies = dependencies;
	}

	/**
	 * @return
	 */
	public String getArtifactId() {
		return artifactId;
	}

	/**
	 * @param artifactId
	 */
	public void setArtifactId(final String artifactId) {
		this.artifactId = artifactId;
	}

	/**
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 */
	public void setName(final String name) {
		this.name = name;
	}
}
