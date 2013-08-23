package com.masterdoc.pojo;


public class MasterDocMetadata {

  /** MasterDoc generation date. */
  private String   generationDate;
  /** MasterDoc project groupId where the plugin is called. */
  private String groupId;
  /** MasterDoc project artifactId where the plugin is called. */
  private String artifactId;
  /** MasterDoc project version where the plugin is called. */
  private String version;

  /**
   * @return the generationDate
   */
  public String getGenerationDate() {
    return generationDate;
  }

  /**
   * @param generationDate
   *          the generationDate to set
   */
  public void setGenerationDate(String generationDate) {
    this.generationDate = generationDate;
  }

  /**
   * @return the groupId
   */
  public String getGroupId() {
    return groupId;
  }

  /**
   * @param groupId
   *          the groupId to set
   */
  public void setGroupId(String groupId) {
    this.groupId = groupId;
  }

  /**
   * @return the artifactId
   */
  public String getArtifactId() {
    return artifactId;
  }

  /**
   * @param artifactId
   *          the artifactId to set
   */
  public void setArtifactId(String artifactId) {
    this.artifactId = artifactId;
  }

  /**
   * @return the version
   */
  public String getVersion() {
    return version;
  }

  /**
   * @param version
   *          the version to set
   */
  public void setVersion(String version) {
    this.version = version;
  }
}
