package demo.object;

import java.util.Map;
import java.io.Serializable;

import demo.common.Constants;
public class OverWindow implements Serializable {
	
	private String partion = "";
    private String orderBy = "";
    private int preceding  = -1;
    
    public void setPartion(String partion) {
    	this.partion = partion;
    }
    
    public void setOrderBy(String orderBy) {
    	this.orderBy = orderBy;
    }
    
    public void setPreceding(int preceding) {
    	this.preceding = preceding;
    }
    
    public String getPartion() {
    	return partion;
    }
    
    public String getOrderBy() {
    	return orderBy;
    }
    
    public int getPreceding() {
    	return preceding;
    }
}
