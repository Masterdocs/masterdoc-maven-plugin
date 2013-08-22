package com.masternaut.platform.masterdoc.pojo;

import java.io.Serializable;

/**
 * User: pleresteux
 */
public class CamelConsumeAnnotation implements Serializable {
  private String uri;
  private String context;

  public CamelConsumeAnnotation() {
  }

  public String getUri() {
    return uri;
  }

  public void setUri(String uri) {
    this.uri = uri;
  }

  public String getContext() {
    return context;
  }

  public void setContext(String context) {
    this.context = context;
  }

}
