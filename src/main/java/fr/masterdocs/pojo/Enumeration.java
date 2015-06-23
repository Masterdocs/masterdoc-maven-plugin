package fr.masterdocs.pojo;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.List;

/**
 * User: pleresteux
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Enumeration extends AbstractEntity {
    private List<String> values;

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

}
