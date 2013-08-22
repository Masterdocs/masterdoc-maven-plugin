package com.masterdoc.pojo;

import java.io.Serializable;

/**
 * User: pleresteux
 */
public class Field implements Serializable {
  private String name;
  private String type;

  public Field() {
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
}
