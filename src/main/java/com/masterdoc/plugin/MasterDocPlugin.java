package com.masterdoc.plugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;

import com.masterdoc.listener.GenerateDocListener;

/**
 * 
 */
@Mojo(name = "masterdoc")
public class MasterDocPlugin extends AbstractMojo {

	private String packageDocumentationResource;

	public void execute() throws MojoExecutionException {
		getLog().info(
				"#################################################################");
		getLog().info(
				"# #    #   ##    ####  ##### ###### #####  #####   ####   ####  #");
		getLog().info(
				"# ##  ##  #  #  #        #   #      #    # #    # #    # #    # #");
		getLog().info(
				"# # ## # #    #  ####    #   #####  #    # #    # #    # #      #");
		getLog().info(
				"# #    # ######      #   #   #      #####  #    # #    # #      #");
		getLog().info(
				"# #    # #    # #    #   #   #      #   #  #    # #    # #    # #");
		getLog().info(
				"# #    # #    #  ####    #   ###### #    # #####   ####   ####  #");
		getLog().info(
				"#################################################################");
		getLog().info(
				"Generate REST documentation on package "
						+ packageDocumentationResource);
		new GenerateDocListener(packageDocumentationResource);
	}
}
