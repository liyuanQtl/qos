package demo.object;

import java.util.Properties;

import demo.util.PropertyReader;
import demo.exception.CustomException;

public class EmailSendInfo {
	// 登陆邮件发送服务器的用户名和密码
    private String userName       = "";
    private String password       = "";
    // 发送邮件的服务器的IP和端口
    private String mailServerPort = "";
    private String mailServerHost = "";
    // 邮件发送者的地址
    private String fromAddress = "";
    // 邮件接收者的地址
    private String toAddress = "";
    // 是否需要身份验证
    private boolean validate = false;
    // 邮件主题
    private String subject;
    // 邮件的文本内容
    private String content;
    private String mailFileName = "";
    // 邮件附件的文件名
    private String[] attachFileNames;

    private void setDefault() {
    	try {
	    	Properties prop = PropertyReader.getProp("/mail.properties");
	    	this.userName = (String) prop.get("userName");
	    	this.password = (String) prop.get("password");
	    	this.mailServerPort = (String) prop.get("mailServerPort");
	    	this.mailServerHost = (String) prop.get("mailServerHost");
	    	this.fromAddress = (String) prop.get("fromAddress");
	    	this.toAddress = (String) prop.get("toAddress");
	    	this.subject = (String) prop.get("subject");
	    	this.content = (String) prop.get("content");
	    	this.mailFileName = (String) prop.get("mailFileName");
	    	String va = (String) prop.get("validate");
	    	this.validate = va.toLowerCase().equals("true")? true:false;
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    
    public EmailSendInfo() {
    	super();
    	setDefault();
    }
    
    /**
     * 获得邮件会话属性
     */
    public Properties getProperties() {
        Properties p = new Properties();
        p.put("mail.smtp.host", this.mailServerHost);
        p.put("mail.smtp.port", this.mailServerPort);
        p.put("mail.smtp.auth", validate ? "true" : "false");
        return p;
    }

    public String getMailServerHost() {
        return mailServerHost;
    }

    public void setMailServerHost(String mailServerHost) {
        this.mailServerHost = mailServerHost;
    }

    public String getMailServerPort() {
        return mailServerPort;
    }

    public void setMailServerPort(String mailServerPort) {
        this.mailServerPort = mailServerPort;
    }

    public boolean isValidate() {
        return validate;
    }

    public void setValidate(boolean validate) {
        this.validate = validate;
    }

    public String[] getAttachFileNames() {
        return attachFileNames;
    }

    public void setAttachFileNames(String[] fileNames) {
        this.attachFileNames = fileNames;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String textContent) {
        this.content = textContent;
    }
    public String getMailFileName() {
        return mailFileName;
    }

    public void setMailFileName(String mailFileName) {
        this.mailFileName = mailFileName;
    }
}