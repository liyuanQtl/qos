package demo.object;

import java.util.Map;
import java.io.Serializable;

import demo.object.Column;

public class Topic implements Serializable {
    private String name = "";
    private String type = "";
    private String separate = "";
    private Map<String, Column> fields = null;
    
    public Topic(String name, String type, String separate, Map<String, Column> fields) {
    	this.name = name;
    	this.type = type;
    	this.separate = separate;
    	this.fields = fields;
    }
    public String getName() {
    	return name;
    }
    public String getType() {
    	return type;
    }
    public String getSeparate() {
    	return separate;
    }
    public Map<String, Column> getFields() {
    	return fields;
    }
}
