package fr.masterdocs.pojo;

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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static javax.ws.rs.core.MediaType.WILDCARD;

/**
 * User: pleresteux @
 */
public class ResourceEntry implements Serializable {
    private String verb;
    private String path;
    private List<Param> queryParams;
    private List<Param> pathParams;
    private String requestEntity;
    private String responseEntity;
    private String mediaTypeConsumes;
    private String mediaTypeProduces;
    private String fullPath;
    private String methodName;

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

    public String getRequestEntity() {
        return requestEntity;
    }

    public void setRequestEntity(String requestEntity) {
        this.requestEntity = requestEntity;
    }

    public String getResponseEntity() {
        return responseEntity;
    }

    public void setResponseEntity(String responseEntity) {
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

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String calculateUniqKey() {
        return this.path + "<<" + this.verb;
    }
}
