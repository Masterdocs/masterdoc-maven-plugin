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

import java.util.List;
import java.util.Map;

/**
 * User: pleresteux
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Entity extends AbstractEntity {
    private String superClass;
    private Map<String, AbstractEntity> fields;
    private List<String> subType;

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

    public String getSuperClass() {
        return superClass;
    }

    public void setSuperClass(String superClass) {
        this.superClass = superClass;
    }

    public List<String> getSubType() {
        return subType;
    }

    public void setSubType(List<String> subType) {
        this.subType = subType;
    }
}
