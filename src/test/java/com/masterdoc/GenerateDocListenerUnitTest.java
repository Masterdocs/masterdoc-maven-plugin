package com.masterdoc;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import com.masterdoc.listener.GenerateDocListener;

public class GenerateDocListenerUnitTest {

	@Test
	public void should_generate_doc() {
		// new GenerateDocListener("com.masterdoc");

		List<ClassLoader> classLoadersList = new LinkedList<ClassLoader>();
		classLoadersList.add(ClasspathHelper.contextClassLoader());
		classLoadersList.add(ClasspathHelper.staticClassLoader());

		Reflections reflections = new Reflections(
				new ConfigurationBuilder()
						.setScanners(new SubTypesScanner(false),
								new ResourcesScanner())
						.setUrls(
								ClasspathHelper.forClassLoader(classLoadersList
										.toArray(new ClassLoader[0])))
						.filterInputsBy(
								new FilterBuilder().include(FilterBuilder
										.prefix("com.masterdoc"))));
	}
}
