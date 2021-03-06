package fr.masterdocs.pojo;

/*-
 * #%L
 * masterdocs
 * $Id:$
 * $HeadURL:$
 * %%
 * Copyright (C) 2017 masterdocs
 * %%
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * 3. Neither the name of the masterdocs nor the names of its contributors
 *    may be used to endorse or promote products derived from this software without
 *    specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
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
