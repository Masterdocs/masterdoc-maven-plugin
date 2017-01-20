package fr.masterdocs.plugin;

/*-
 * #%L
 * masterdocs
 * $Id:$
 * $HeadURL:$
 * %%
 * Copyright (C) 2017 masterdocs
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

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

    /**
     * Generate html5 site
     */
    @Parameter(property = "maxDepth", readonly = true, required = false, defaultValue = "1")
    private Integer maxDepth;

    public void execute() throws MojoExecutionException {
        new MasterDocLogoGenerator();
        try {
            new MasterDocGenerator(project, pathToGenerateFile, packageDocumentationResources, generateHTMLSite, maxDepth);
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
