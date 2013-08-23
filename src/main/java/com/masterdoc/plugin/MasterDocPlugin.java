package com.masterdoc.plugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;

import com.masterdoc.listener.GenerateDocListener;
import com.masterdoc.listener.logo.LogoGenerator;

/**
 * masterdoc goal entry.
 * 
 * @author nlenouvel
 */
@Mojo(name = "masterdoc", requiresDependencyResolution = ResolutionScope.COMPILE)
public class MasterDocPlugin extends AbstractMojo {

  /**
   * The Maven Project.
   */
  @Parameter(defaultValue = "${project}", readonly = true, required = true)
  private MavenProject project;

  /**
   * Path where to generate documentation.
   */
  @Parameter(property = "pathToGenerateFile", readonly = true, required = true)
  private String       pathToGenerateFile;

  /**
   * Package where to find the classes annotated to generate documentation.
   */
  @Parameter(property = "packageDocumentationResources", readonly = true, required = true)
  private String[]     packageDocumentationResources;

  public void execute() throws MojoExecutionException {
    new LogoGenerator();
    try {
      new GenerateDocListener(project, pathToGenerateFile, packageDocumentationResources);
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
