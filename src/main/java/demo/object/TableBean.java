package demo.object;

import java.util.Map;
import java.util.List;
import java.io.Serializable;

import demo.object.Column;

public class TableBean implements Serializable {
    private String name;
    private String topic;
    private String sql;
    private List<Column> columns;
    private List<Column> params;
    
    public TableBean(String name, String topic, String sql, List<Column> columns, List<Column> params) {
    	this.name = name;
    	this.topic = topic;
    	this.sql = sql;
    	this.columns = columns;
    	this.params = params;
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
    public List<Column> getColumns() {
    	return columns;
    }
    public List<Column> getParams() {
    	return params;
    }
}
