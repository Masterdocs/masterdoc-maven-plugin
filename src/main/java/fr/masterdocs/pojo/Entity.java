package fr.masterdocs.pojo;

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
