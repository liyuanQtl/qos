package demo.object;

import java.io.Serializable;

public class OverWindow implements Serializable {
	
	private String partitionBy = "";
    private String orderBy = "";
    private String preceding = "";
    private String alias = "w";
    private String following = "";
    public void setPartionBy(String partitionBy) {
    	this.partion = partion;
    }
    
    public void setOrderBy(String orderBy) {
    	this.orderBy = orderBy;
    }
    
    public void setPreceding(String preceding) {
    	this.preceding = preceding;
    }
    
    public void setAlias(String alias) {
    	this.alias = alias;
    }
    
    public void setFollowing(String following) {
    	this.following = following;
    }
    
    public String getPartionBy() {
    	return partionBy;
    }
    
    public String getOrderBy() {
    	return orderBy;
    }
    
    public String getPreceding() {
    	return preceding;
    }
    
    public String getAlias() {
    	return alias;
    }
    
    public String getFollowing() {
    	return following;
    }
}
