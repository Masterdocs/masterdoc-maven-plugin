package com.masterdoc.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * User: nlenouvel
 */
public class MasterDoc implements Serializable {

  private List<AbstractEntity> entities;
  private List<Resource>       resources;
  private MasterDocMetadata    metadata;

  /**
   * @return the entities
   */
  public List<AbstractEntity> getEntities() {
    return entities;
  }

  /**
   * @param entities
   *          the entities to set
   */
  public void setEntities(List<AbstractEntity> entities) {
    this.entities = entities;
  }

  /**
   * @return the resources
   */
  public List<Resource> getResources() {
    return resources;
  }

  /**
   * @param resources
   *          the resources to set
   */
  public void setResources(List<Resource> resources) {
    this.resources = resources;
  }

  /**
   * @return the metadata
   */
  public MasterDocMetadata getMetadata() {
    return metadata;
  }

  /**
   * @param metadata
   *          the metadata to set
   */
  public void setMetadata(MasterDocMetadata metadata) {
    this.metadata = metadata;
  }

}
