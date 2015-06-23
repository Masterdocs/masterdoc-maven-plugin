package fr.masterdocs.pojo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * User: pleresteux
 */
public class Resource implements Serializable {

    private String rootPath;
    private Map<String, List<ResourceEntry>> entryList;

    public Resource() {
    }

    public String getRootPath() {
        return rootPath;
    }

    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    public Map<String, List<ResourceEntry>> getEntryList() {
        return entryList;
    }

    public void setEntryList(Map<String, List<ResourceEntry>> entryList) {
        this.entryList = entryList;
    }
}
