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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * @author hp
 *
 */
public class Dom {
	/**
	 * 读取文件
	 * 
	 * @param file
	 * @return
	 */
	public static Document readFile(final File file) {
		final SAXReader reader = new SAXReader();
		Document document = null;
		try {
			document = reader.read(file);
		} catch (final DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return document;
	}

	/**
	 * 文件转pom对象
	 * 
	 * @param file
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Pom transPom(final File file) {
		final Document doc = readFile(file);
		final Pom pom = new Pom();
		pom.setEncoding(doc.getXMLEncoding());
		pom.setModelVersion(doc.getRootElement().elementText("modelVersion"));
		final Parent parent = new Parent(
				doc.getRootElement().elementText("groupId"),
				doc.getRootElement().elementText("artifactId"),
				doc.getRootElement().elementText("version"));
		pom.setParent(parent);
		final List<Dependency> dependencies = new ArrayList<Dependency>();
		final List<Element> modules = doc.getRootElement().element("modules")
				.elements();
		for (final Element module : modules) {
			final Dependency dependency = new Dependency(
					doc.getRootElement().elementText("groupId"),
					module.getText(),
					doc.getRootElement().elementText("version"));
			dependencies.add(dependency);
		}
		pom.setDependencies(dependencies);
		pom.setArtifactId(doc.getRootElement().elementText("artifactId")
				+ "-coverage-report");
		pom.setName(doc.getRootElement().elementText("artifactId")
				+ "-coverage-report");
		final Element root = doc.getRootElement();
		for (final Object e : root.elements()) {
			root.remove((Element) e);
		}
		pom.setProject(root);
		return pom;

	}

	/**
	 * 创建子模块的pom文件
	 * 
	 * @param pom
	 * @param path
	 */
	public static void CreatePom(final Pom pom, final String path) {
		if (new File(path + System.getProperty("file.separator") + "pom.xml")
				.exists()) {
			return;
		}
		Element root = null;
		final Document document = DocumentHelper.createDocument();
		if (pom.getProject().declaredNamespaces().size() != 0) {
			root = document.addElement("project",
					pom.getProject().getNamespaceForPrefix(null).getURI());
			for (final Object space : pom.getProject().declaredNamespaces()) {
				if (((Namespace) space).getURI() != null) {
					root.addNamespace(((Namespace) space).getPrefix(),
							((Namespace) space).getURI());
				}
			}

		}
		if (pom.getProject().attributes().size() != 0) {
			root.setAttributes(pom.getProject().attributes());
		}
		final Element modelVersion = root.addElement("modelVersion");
		modelVersion.addText(pom.getModelVersion());
		final Element parent = root.addElement("parent");
		final Element parent_groupId = parent.addElement("groupId");
		parent_groupId.addText(pom.getParent().getGroupId());
		final Element parent_artifactId = parent.addElement("artifactId");
		parent_artifactId.addText(pom.getParent().getArtifactId());
		final Element parent_version = parent.addElement("version");
		parent_version.addText(pom.getParent().getVersion());
		final Element artifactId = root.addElement("artifactId");
		artifactId.addText(pom.getArtifactId());
		final Element name = root.addElement("name");
		name.addText(pom.getName());
		final Element dependencies = root.addElement("dependencies");
		for (final Dependency d : pom.getDependencies()) {
			final Element dependency = dependencies.addElement("dependency");
			final Element dependency_groupId = dependency.addElement("groupId");
			dependency_groupId.addText(d.getGroupId());
			final Element dependency_artifactId = dependency
					.addElement("artifactId");
			dependency_artifactId.addText(d.getArtifactId());
			final Element dependency_version = dependency.addElement("version");
			dependency_version.addText(d.getVersion());
		}
		createXml(document, "UTF-8", path, "pom.xml");
	}

	/**
	 * 更新父项目pom文件，插入新增模块
	 * 
	 * @param document
	 * @param moduleName
	 * @param path
	 */
	@SuppressWarnings("rawtypes")
	public static void insertModule(final Document document,
			final String moduleName, final String path) {
		// 不重复插入
		boolean flag = true;
		final List modules = document.getRootElement().element("modules")
				.elements();
		for (final Object module : modules) {
			final String moduleText = ((Element) module).getText();
			if (moduleText.equals(moduleName)) {
				flag = false;
			}
		}
		if (!flag) {
			return;
		}
		final Element module = document.getRootElement().element("modules")
				.addElement("module", document.getRootElement()
						.getNamespaceForPrefix(null).getURI());
		module.setText(moduleName);
		// 如果文件存在,则先删除
		if (new File(path + System.getProperty("file.separator") + "pom.xml")
				.exists()) {
			new File(path + System.getProperty("file.separator") + "pom.xml")
					.delete();
		}
		createXml(document, "UTF-8", path, "pom.xml");
	}

	/**
	 * 生成xml文件
	 * 
	 * @param document
	 * @param encoding
	 * @param path
	 * @param FileName
	 */
	@SuppressWarnings("static-access")
	public static void createXml(final Document document, final String encoding,
			final String path, final String FileName) {
		final File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
		final OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding(encoding);
		XMLWriter xmlWriter = null;
		try {
			xmlWriter = new XMLWriter(
					new FileOutputStream(path
							+ System.getProperty("file.separator") + FileName),
					format);
			xmlWriter.write(document);
			xmlWriter.close();
		} catch (final UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (final FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @param doc
	 * @return
	 */
	public static String getArtifaceId(final Document doc) {
		return doc.getRootElement().elementText("artifactId");
	}

	/**
	 * 判断是否多模块
	 * 
	 * @param doc
	 * @return
	 */
	public static boolean isMulModule(final Document doc) {
		final Element node = doc.getRootElement().element("modules");
		if (node == null) {
			return false;
		} else {
			return true;
		}
	}

}
