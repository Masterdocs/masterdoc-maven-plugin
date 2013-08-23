package com.masterdoc.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static javax.ws.rs.core.MediaType.WILDCARD;

/**
 * User: pleresteux
 */
public class ResourceEntry implements Serializable {
  private String                 verb;
  private String                 path;
  private List<Param>            queryParams;
  private List<Param>            pathParams;
  private Serializable           requestEntity;
  private Serializable           responseEntity;
  private String                 mediaTypeConsumes;
  private String                 mediaTypeProduces;
  private CamelConsumeAnnotation CamelConsume;
  private String                 fullPath;

  public ResourceEntry() {
    this(WILDCARD, WILDCARD);
  }

  public ResourceEntry(String mediaTypeConsumes, String mediaTypeProduces) {
    this.mediaTypeConsumes = mediaTypeConsumes;
    this.mediaTypeProduces = mediaTypeProduces;
    this.pathParams = new ArrayList<Param>();
    this.queryParams = new ArrayList<Param>();
  }

  public String getVerb() {
    return verb;
  }

  public void setVerb(String verb) {
    this.verb = verb;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public List<Param> getQueryParams() {
    return queryParams;
  }

  public void setQueryParams(List<Param> queryParams) {
    this.queryParams = queryParams;
  }

  public List<Param> getPathParams() {
    return pathParams;
  }

  public void setPathParams(List<Param> pathParams) {
    this.pathParams = pathParams;
  }

  public Serializable getRequestEntity() {
    return requestEntity;
  }

  public void setRequestEntity(Serializable requestEntity) {
    this.requestEntity = requestEntity;
  }

  public Serializable getResponseEntity() {
    return responseEntity;
  }

  public void setResponseEntity(Serializable responseEntity) {
    this.responseEntity = responseEntity;
  }

  public String getMediaTypeConsumes() {
    return mediaTypeConsumes;
  }

  public void setMediaTypeConsumes(String mediaTypeConsumes) {
    this.mediaTypeConsumes = mediaTypeConsumes;
  }

  public String getMediaTypeProduces() {
    return mediaTypeProduces;
  }

  public void setMediaTypeProduces(String mediaTypeProduces) {
    this.mediaTypeProduces = mediaTypeProduces;
  }

  public CamelConsumeAnnotation getCamelConsume() {
    return CamelConsume;
  }

  public void setCamelConsume(CamelConsumeAnnotation camelConsume) {
    CamelConsume = camelConsume;
  }

  public void setFullPath(String fullPath) {
    this.fullPath = fullPath;
  }

  public String getFullPath() {
    return fullPath;
  }

  public String calculateUniqKey() {
    return this.verb + ">>" + this.path;
  }
}
