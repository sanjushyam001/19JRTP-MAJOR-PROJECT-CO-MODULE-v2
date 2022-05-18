package in.ashokit.utils;


import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class EmailUtils {

	private String subject="Unlock User";
	private String body="";
	
	public String formatWelcomeEmail(String fname,String lname,String generatedPaswd,String toEmail) {
		
		body=body+"Hi, "+fname+" "+lname+"\n Welcome to our family,Your registration is almost complete.\n";
		body=body+"Please unlock your account using below details\n";
		body=body+"\nTemporary password: "+generatedPaswd+"\n\n";
		body=body+"Click here for verification : http://localhost:4200/edit?email="+toEmail;
		
		return body;
	}
	
	public String readUnlockAccEmailBody(String fname,String lname,String generatedPaswd, String toEmail) {
		String body="";
		StringBuffer buffer =new StringBuffer();
		//Path filePath=Paths.get("UNLOCK-ACCT-EMAIL-BODY-TEMPLATE.txt");
		Path fpath = Paths.get("UNLOCK-ACCT-EMAIL-BODY-TEMPLATE.txt");
		System.out.println("File Path: "+fpath.toAbsolutePath());
		try(Stream<String> stream=Files.lines(fpath)) {
			
			stream.forEach(line->{
				buffer.append(line);
			});
			
			body=buffer.toString();
			body=body.replace("{FNAME}",fname);
			body=body.replace("{LNAME}",lname);
			body=body.replace("{TEMP-PWSD}",generatedPaswd);
			body=body.replace("{EMAIL}",toEmail);
			System.out.println("BODY:: "+body);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return body;
	}
}
