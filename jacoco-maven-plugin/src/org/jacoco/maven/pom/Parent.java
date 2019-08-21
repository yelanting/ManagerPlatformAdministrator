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

/**
 * 父节点
 * 
 * @author hp
 *
 */
public class Parent {
	String groupId;
	String artifactId;
	String version;

	/**
	 * @param groupId
	 * @param artifactId
	 * @param version
	 */
	public Parent(final String groupId, final String artifactId,
			final String version) {
		this.groupId = groupId;
		this.artifactId = artifactId;
		this.version = version;
	}

	/**
	 * @return
	 */
	public String getGroupId() {
		return groupId;
	}

	/**
	 * @param groupId
	 */
	public void setGroupId(final String groupId) {
		this.groupId = groupId;
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
	public String getVersion() {
		return version;
	}

	/**
	 * @param version
	 */
	public void setVersion(final String version) {
		this.version = version;
	}
}
