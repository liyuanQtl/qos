package demo.object;

import java.util.Map;
import java.io.Serializable;

import demo.object.GroupWindow;
import demo.object.OverWindow;

public class TimeWindow implements Serializable {
	
	private String windowType = "";
    private String timeAttr = "";
    private OverWindow overWindow = null;
    private GroupWindow groupWindow = null;
    
    public void setWindowType(String windowType) {
    	this.windowType = windowType;
    }
    
    public void setTimeAttr(String timeAttr) {
    	this.timeAttr = timeAttr;
    }
    
    public void setGroupWindow(GroupWindow gw) {
    	this.groupWindow = gw;
    }
    
    public void setOverWindow(OverWindow ow) {
    	this.overWindow = ow;
    }
    
    public String getWindowType() {
    	return windowType;
    }
    
    public String getTimeAttr() {
    	return timeAttr;
    }
    
    public GroupWindow getGroupWindow() {
    	return groupWindow;
    }
    
    public OverWindow getOverWindow() {
    	return overWindow;
    }
}
