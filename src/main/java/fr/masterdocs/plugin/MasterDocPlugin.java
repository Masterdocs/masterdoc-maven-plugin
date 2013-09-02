package fr.masterdocs.plugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import static org.apache.maven.plugins.annotations.ResolutionScope.COMPILE;

/**
 * masterdoc goal entry.
 *
 * @author nlenouvel
 */
@Mojo(name = "masterdoc", requiresDependencyResolution = COMPILE)
public class MasterDocPlugin extends AbstractMojo {

	/**
	 * The Maven Project.
	 */
	@Parameter(defaultValue = "${project}", readonly = true, required = true)
	private MavenProject project;
	/**
	 * Path where to generate documentation.
	 */
	@Parameter(property = "pathToGenerateFile", readonly = true, required = false, defaultValue = "${project.reporting.outputDirectory}")
	private String pathToGenerateFile;
	/**
	 * Package where to find the classes annotated to generate documentation.
	 */
	@Parameter(property = "packageDocumentationResources", readonly = true, required = true)
	private String[] packageDocumentationResources;

	/**
	 * Generate html5 site
	 */
	@Parameter(property = "generateHTMLSite", readonly = true, required = false, defaultValue = "true")
	private boolean generateHTMLSite;

	public void execute() throws MojoExecutionException {
		new MasterDocLogoGenerator();
		try {
			new MasterDocGenerator(project, pathToGenerateFile, packageDocumentationResources, generateHTMLSite);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
