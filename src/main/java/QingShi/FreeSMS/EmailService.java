package QingShi.FreeSMS;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
 
/**
 * Class responsible for email work.
 * 
 * Three email address needs to distinguish
 * 1. Official email address used to send the email (End user don't need to change this)
 * 2. The optional email address end user can enter for the recipient to reply message to
 * 3. To email address, is the recipient "Phone Email Address"
 * 
 * Refer {@link http://www.mkyong.com/java/javamail-api-sending-email-via-gmail-smtp-example/}
 * 
 * Default you are using the Gmail as server.
 * 
 * @author shiqing
 *
 */
public class EmailService {
	// TODO change password back in local 
	private static final String OFFICIAL_EMAIL = "freesmsOfficial@gmail.com";
	private static final String OFFICIAL_PASSWORD = "PASSWORD";
	
	/**
	 * Send email
	 * @param toEmail - the end recipient "Phone Email Address"
	 * @param replyTo - optional sender email address, default is OFFICIAL_EMAIL
	 */
	public void sendEmail(String toEmail, String replyTo, String subject, String body) {
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
 
		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(OFFICIAL_EMAIL, OFFICIAL_PASSWORD);
			}
		  });
 
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(OFFICIAL_EMAIL));
			if (replyTo != null) {
				message.setReplyTo(new InternetAddress[] {new InternetAddress(replyTo)});
			}
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(toEmail));
			message.setSubject(subject);
			message.setText(body);
 
			Transport.send(message);
 
			System.out.println("Done");
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}
