package demo.object;

import java.util.Map;
import java.util.List;

import demo.object.TableBean;

public class AlarmBean {
    private String name = "";
    private String cls = "";
    private String[] tables = null;
    
    public AlarmBean(String name, String cls, String[] tables) {
    	this.name = name;
    	this.cls = cls;
    	this.tables = tables;
    }
    public String getName() {
    	return name;
    }
    public String getCls() {
    	return cls;
    }
    public String[] getTables() {
    	return tables;
    }
}
