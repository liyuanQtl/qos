package demo.object;

import java.util.Map;
import java.util.List;
import java.io.Serializable;

import demo.object.Column;
import demo.object.TimeWindow;

public class TableBean implements Serializable {
    private String sql = "";
    private String name = "";
    private String topic = "";
    private String timeAttr = "";
    private TimeWindow window = null;
    private List<Column> params = null;
    private String[] columns = null;
    
    public TableBean(String name, String topic, String sql, String[] columns, List<Column> params, String timeAttr, TimeWindow window) {
    	this.name = name;
    	this.topic = topic;
    	this.sql = sql;
    	this.columns = columns;
    	this.params = params;
    	this.timeAttr = timeAttr;
    	this.window = window;
    }
    public String getName() {
    	return name;
    }
    public String getTopic() {
    	return topic;
    }
    public String getSql() {
    	return sql;
    }
    public String[] getColumns() {
    	return columns;
    }
    public List<Column> getParams() {
    	return params;
    }
    public String getTimeAttr() {
    	return timeAttr;
    }
    public TimeWindow getWindow() {
    	return window;
    }
}
