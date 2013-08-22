package com.masterdoc.pojo;

import java.io.Serializable;

/**
 * User: pleresteux
 */
public class Param implements Serializable {
  private String name;
  private String type;
  private String className;

  public Param() {
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getClassName() {
    return className;
  }

  public void setClassName(String className) {
    this.className = className;
  }
}
