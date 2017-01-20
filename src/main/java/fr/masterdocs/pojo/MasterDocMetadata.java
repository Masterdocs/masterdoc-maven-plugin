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

import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * @author nlenouvel
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class MasterDocMetadata {

    /**
     * MasterDoc generation date.
     */
    private String generationDate;
    /**
     * MasterDoc project groupId where the plugin is called.
     */
    private String groupId;
    /**
     * MasterDoc project artifactId where the plugin is called.
     */
    private String artifactId;
    /**
     * MasterDoc project version where the plugin is called.
     */
    private String version;

    /**
     * @return the generationDate
     */
    public String getGenerationDate() {
        return generationDate;
    }

    /**
     * @param generationDate the generationDate to set
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
     * @param groupId the groupId to set
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
     * @param artifactId the artifactId to set
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
     * @param version the version to set
     */
    public void setVersion(String version) {
        this.version = version;
    }
}
