package in.ashokit.utils;
import java.io.File;


import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;


@Component
public class EmailWithPdf {

	
	//create java mail sender object
	@Autowired
	private JavaMailSender javaMailSender;
	
	
	
	public EmailStatus sendEmail(String toEmail, String subject, String body,File file) {
	
		System.out.println("EmailWithPdf.sendEmail()"+javaMailSender);
		boolean isSent=false;
		String mesg="";
		try {
			MimeMessage mimeMessage=javaMailSender.createMimeMessage();
			MimeMessageHelper helper=new MimeMessageHelper(mimeMessage,true);
			//SimpleMailMessage emailMessage=new SimpleMailMessage();
			//message.setFrom("kumarsanjuashokit@gmail.com");
			//System.out.println("EmailWithPdf.sendEmail()>>");
			String sender="kumarsanjuashokit@gmail.com";
			helper.setFrom(sender);
			helper.setTo(toEmail);
			
			helper.setSubject(subject);
			helper.setText(body,true);
			helper.addAttachment("PlanNotification.pdf", file);
			
			javaMailSender.send(mimeMessage);
			isSent= true;
			mesg="email sent to " +toEmail +"From "+sender;
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage()); 
			isSent=false;
			mesg="";
			
		}
		return new EmailStatus(mesg, isSent);
	}
}