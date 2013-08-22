package com.masterdoc.pojo;

import java.util.List;

/**
 * User: pleresteux
 */
public class Enumeration extends AbstractEntity {
  private List<String> values;
  private boolean      enumeration;

  public Enumeration() {
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<String> getValues() {
    return values;
  }

  public void setValues(List<String> values) {
    this.values = values;
  }

  public boolean isEnumeration() {
    return true;
  }

}
