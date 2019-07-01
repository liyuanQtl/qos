package demo.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import demo.util.MailUtil;
import demo.object.EmailSendInfo;

public class CustomException extends Exception {

	private static final Logger logger			= LoggerFactory.getLogger(CustomException.class);

	private static final long	serialVersionUID	= 1L;
	private static String message = "";

	public CustomException() {
		super();
	}
	
	public CustomException(String message) {
		super(message);
		this.message = message;
		EmailSendInfo info = new EmailSendInfo();
		info.setSubject("flink exception");
		info.setContent(message);
		MailUtil mu = new MailUtil();
		mu.sendTextMail(info);
	}

    public String getMessage() {
        return message;
    }
}
