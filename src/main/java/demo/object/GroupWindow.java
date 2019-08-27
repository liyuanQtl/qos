package demo.object;

import java.util.Map;
import java.io.Serializable;

import demo.common.Constants.GROUP_WINDOW_ASSIGNER;
public class GroupWindow implements Serializable {
	
	private GROUP_WINDOW_ASSIGNER type;
    private String over = "";
    private String every = "";
    private String on = "";
    private String as = "w";
    private String groupBy = "";
    
    public void setType(GROUP_WINDOW_ASSIGNER type) {
    	this.type = type;
    }
    
    publci void setOver(String over) {
    	this.over = over;
    }
    
    public void setEvery(String every) {
    	this.every = every;
    }
    
    public void setOn(String on) {
    	this.on = on;
    }
    
    public void setAs(String as) {
    	this.as = as;
    }
    
    public void setGroupby(String groupBy) {
    	this.groupBy = groupBy;
    }
    
    public GROUP_WINDOW_ASSIGNER getType() {
    	return type;
    }
    
    public String getOver() {
    	return over;
    }
    
    public String getEvery() {
    	return every;
    }
    
    public String getOn() {
    	return on;
    }
    
    public String getGroupby() {
    	return groupby;
    }
    
    public String getAs() {
    	return as;
    }
}
