
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
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

import org.apache.log4j.Logger;



public class SendEmail {
	
	private static final Logger LOGGER = Logger.getLogger(SendEmail.class);
	public void sendEmailWithAttachment(){
		
	if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(LOGGER.getClass() + "Send Email method::Enter");
	}
	
	
		
	  // Recipient's email ID needs to be mentioned.
      String to = "";

      // Sender's email ID needs to be mentioned
      String from = "";

     
      // Assuming you are sending email through relay.jangosmtp.net
      String host = "mailhost.xxx.com";

      Properties props = new Properties();
     
      props.put("mail.smtp.host", host);
      props.put("mail.smtp.port", "25");

      // Get the Session object.
      Session session = Session.getInstance(props,null);

      String fileName ="Data.xlsx";
      
  	  String rootPath = "C:/jboss-as-7.1.1.Final";
		
	  String relativePath = "/ExportExcelData";
		
	  java.util.Date date = new java.util.Date();
      DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");    
      String today = (dateFormat.format(date)).toString();
	   
      String messasgeText = "Good Morning,\n\n" +
      		" Details.\n\n" +
      		" x\n"+
      		" xx.\n" +
      		" xxx \n\n"+
      		" xxxx n\n"+
      		" Thanks,\n"+
      		" ";
      
      String filePath = rootPath + File.separator + relativePath+File.separator +today + File.separator+fileName;
      
      try {
         // Create a default MimeMessage object.
         Message message = new MimeMessage(session);

         // Set From: header field of the header.
         message.setFrom(new InternetAddress(from));

         // Set To: header field of the header.
         message.setRecipients(Message.RecipientType.TO,
            InternetAddress.parse(to));

         // Set Subject: header field
         message.setSubject("eBOL Alert: Container Stuck @ Non-Retail/Retail Location");

         // Create the message part
         BodyPart messageBodyPart = new MimeBodyPart();
         
         // Now set the actual message
         //messageBodyPart.setText("Hi,\n\n" +"Please find attached Sheet \n\n");
         messageBodyPart.setText(messasgeText);

         // Create a multipart message
         Multipart multipart = new MimeMultipart();

         // Set text message part
        multipart.addBodyPart(messageBodyPart);
        
         messageBodyPart = new MimeBodyPart();
         DataSource source = new FileDataSource(filePath);
      
         messageBodyPart.setDataHandler(new DataHandler(source));
         
         messageBodyPart.setFileName(fileName);

         multipart.addBodyPart(messageBodyPart);
       
         // Send the complete message parts
         message.setContent(multipart);

         // Send message
         Transport.send(message);
         
         if (LOGGER.isDebugEnabled()) {
 			LOGGER.debug(LOGGER.getClass() + "Send Email method::Sent message successfully....with attachment");
         }

  
      }
      catch (MessagingException e) {
	    	  if (LOGGER.isDebugEnabled()) {
	  			LOGGER.debug(LOGGER.getClass() + "Send Email method::Catch Block");
	    	  }
	    	  if (LOGGER.isDebugEnabled()) {
		  			LOGGER.debug(LOGGER.getClass() + "Unable to send email due to unexpected exception");
		     }
	         throw new RuntimeException(e);
      }
   }
 }
