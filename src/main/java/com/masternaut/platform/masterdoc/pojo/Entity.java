package com.masternaut.platform.masterdoc.pojo;

import java.util.Map;

/**
 * User: pleresteux
 */
public class Entity extends AbstractEntity {
  private Map<String, AbstractEntity> fields;
  private boolean                     enumeration;

  public Entity() {
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Map<String, AbstractEntity> getFields() {
    return fields;
  }

  public void setFields(Map<String, AbstractEntity> fields) {
    this.fields = fields;
  }

  public boolean isEnumeration() {
    return enumeration;
  }

  public void setEnumeration(boolean enumeration) {
    this.enumeration = enumeration;
  }

}
