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

import java.io.Serializable;
import java.util.List;

/**
 * User: nlenouvel
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class MasterDoc implements Serializable {

    private List<AbstractEntity> entities;
    private List<Resource> resources;
    private MasterDocMetadata metadata;

    /**
     * @return the entities
     */
    public List<AbstractEntity> getEntities() {
        return entities;
    }

    /**
     * @param entities the entities to set
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
     * @param resources the resources to set
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
     * @param metadata the metadata to set
     */
    public void setMetadata(MasterDocMetadata metadata) {
        this.metadata = metadata;
    }

}
