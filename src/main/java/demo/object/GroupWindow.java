package demo.object;

import java.util.Map;
import java.io.Serializable;

import demo.common.Constants;
public class GroupWindow implements Serializable {
	
	private String type = "";
	
    private Map<String, String> interval = null;
    private Map<String, String> hopInterval = null;
    
    public void setType(String type) {
    	this.type = type;
    }
    
    public void setInterval(Map interval) {
    	this.interval = interval;
    }
    
    public void setHopInterval(Map interval) {
    	this.hopInterval = interval;
    }

    public String getType() {
    	return type;
    }
    
    public Map getInterval() {
    	return interval;
    }
    
    public Map getHopInterval() {
    	return hopInterval;
    }
}
