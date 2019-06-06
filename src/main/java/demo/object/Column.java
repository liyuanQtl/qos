package demo.object;

import java.io.Serializable;

public class Column implements Serializable {
    private String name;
    private String type;
    private String discription;
    
    public Column(String name, String type, String discription) {
    	this.name = name;
    	this.type = type;
    	this.discription = discription;
    }
    public String getName() {
    	return name;
    }
    public String getType() {
    	return type;
    }
    public String getDiscription() {
    	return discription;
    }
}
