package in.ashokit.utils;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EmailStatus {

	private String mesg;
	private boolean status;
	
	public EmailStatus(String mesg, boolean status) {
		super();
		this.mesg = mesg;
		this.status = status;
		
	}
	
}
