package demo.object;

import java.util.Map;
import java.util.List;

import demo.object.TableBean;

public class AlarmBean {
    private String name;
    private String cls;
    private List<TableBean> tables;
    
    public AlarmBean(String name, String cls, List<TableBean> tables) {
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
    public List<TableBean> getTables() {
    	return tables;
    }
}
