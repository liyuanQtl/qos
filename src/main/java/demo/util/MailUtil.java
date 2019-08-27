package demo.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import demo.object.EmailSendInfo;
import demo.util.EmailAuthenticator;

public class MailUtil {

	private static final Logger _log = LoggerFactory.getLogger(MailUtil.class);
	
    public static boolean sendTextMail(EmailSendInfo mailInfo) {   
        boolean sendStatus = false;//发送状态
        // 判断是否需要身份认证    
        EmailAuthenticator authenticator = null;    
        Properties pro = mailInfo.getProperties();
        if (mailInfo.isValidate()) {
        // 如果需要身份认证，则创建一个密码验证器    
          authenticator = new EmailAuthenticator(mailInfo.getUserName(), mailInfo.getPassword());    
        }   
        // 根据邮件会话属性和密码验证器构造一个发送邮件的session  
        Session sendMailSession = Session.getInstance(pro, authenticator);    
        //【调试时使用】开启Session的debug模式
        sendMailSession.setDebug(true);
        try {    
            // 根据session创建一个邮件消息    
            MimeMessage  mailMessage = new MimeMessage(sendMailSession);    
            // 创建邮件发送者地址    
            System.out.println("send mail address:"+mailInfo.getFromAddress());
            Address from = new InternetAddress(mailInfo.getFromAddress());    
            // 设置邮件消息的发送者    
            mailMessage.setFrom(from);    
            // 创建邮件的接收者地址，并设置到邮件消息中    
            Address to = new InternetAddress(mailInfo.getToAddress());    
            mailMessage.setRecipient(Message.RecipientType.TO,to);    
            // 设置邮件消息的主题    
            mailMessage.setSubject(mailInfo.getSubject(), "UTF-8");
            // 设置邮件消息发送的时间    
            mailMessage.setSentDate(new Date());    
            // 设置邮件消息的主要内容    
            String mailContent = mailInfo.getContent();    
            mailMessage.setText(mailContent, "UTF-8"); 
//                mailMessage.saveChanges(); 
            
            //生成邮件文件
            createEmailFile(mailInfo.getMailFileName(), mailMessage); 
            
            // 发送邮件    
            Transport.send(mailMessage);   
            sendStatus = true;    
        } catch (MessagingException ex) {    
              _log.error("以文本格式发送邮件出现异常", ex);
              return sendStatus;
        }    
        return sendStatus;    
    }
    
    public static boolean sendMail(EmailSendInfo mailInfo, File emailFile) {
        boolean sendStatus = false;//发送状态
        // 判断是否需要身份认证    
        EmailAuthenticator authenticator = null;    
        Properties pro = mailInfo.getProperties();
        if (mailInfo.isValidate()) {    
        // 如果需要身份认证，则创建一个密码验证器    
          authenticator = new EmailAuthenticator(mailInfo.getUserName(), mailInfo.getPassword());    
        }   
        // 根据邮件会话属性和密码验证器构造一个发送邮件的session  
        Session sendMailSession = Session.getInstance(pro, authenticator);    
        //【调试时使用】开启Session的debug模式
        sendMailSession.setDebug(true);
        try {    
            InputStream source = new FileInputStream(emailFile);
            // 根据session创建一个邮件消息    
             Message  mailMessage = new MimeMessage(sendMailSession, source);    
            // 发送邮件    
            Transport.send(mailMessage);   
            //【重要】关闭输入流，否则会导致文件无法移动或删除
            source.close();
            sendStatus = true;    
        } catch (MessagingException e) {    
              _log.error("以文本格式发送邮件出现异常", e);
              return sendStatus;
        } catch (FileNotFoundException e) {
            _log.error("FileNotFoundException", e);
            return sendStatus;   
        } catch (Exception e) {
            _log.error("Exception", e);
            return sendStatus;
        }
        return sendStatus;    
    }
    
    public static void createEmailFile(String fileName, Message mailMessage)
            throws MessagingException {
        
        File f = new File(fileName); 
        try {
            mailMessage.writeTo(new FileOutputStream(f));
        } catch (IOException e) {
            _log.error("IOException", e);  
        }
    } 
}
