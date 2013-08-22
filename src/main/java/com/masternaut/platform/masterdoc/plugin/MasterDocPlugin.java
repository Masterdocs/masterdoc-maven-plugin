package com.masternaut.platform.masterdoc.plugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;

import com.masternaut.platform.masterdoc.listener.GenerateDocListener;

/**
 * 
 */
@Mojo(name = "masterdoc")
public class MasterDocPlugin extends AbstractMojo {

  public void execute() throws MojoExecutionException {
    getLog().info("#################################################################");
    getLog().info("# #    #   ##    ####  ##### ###### #####  #####   ####   ####  #");
    getLog().info("# ##  ##  #  #  #        #   #      #    # #    # #    # #    # #");
    getLog().info("# # ## # #    #  ####    #   #####  #    # #    # #    # #      #");
    getLog().info("# #    # ######      #   #   #      #####  #    # #    # #      #");
    getLog().info("# #    # #    # #    #   #   #      #   #  #    # #    # #    # #");
    getLog().info("# #    # #    #  ####    #   ###### #    # #####   ####   ####  #");
    getLog().info("#################################################################");
    new GenerateDocListener("com.masternaut.synaps.services.fleetmanagement.rest");
  }
}
