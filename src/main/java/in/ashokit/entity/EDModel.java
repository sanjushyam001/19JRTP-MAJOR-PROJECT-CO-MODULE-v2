package in.ashokit.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "ELIGD_DTLS")
public class EDModel {

	@Id
	@GeneratedValue
	private Integer id;
	private Integer caseNo;
	private String holderName;
	private Integer holderSsn;
	private String planName;
	private String planStatus;
	private LocalDate startDate;
	private LocalDate endDate;
	private Double benifitAmnt;
	private String denialReason;
	
	//arguments constructor
	public EDModel(Integer caseNo, String holderName,Integer holderSsn,String planName, String planStatus, LocalDate startDate, LocalDate endDate, Double benifitAmnt,
			String denialReason) {
		super();
		this.caseNo=caseNo;
		this.holderName=holderName;
		this.holderSsn=holderSsn;
		this.planName = planName;
		this.planStatus = planStatus;
		this.startDate = startDate;
		this.endDate = endDate;
		this.benifitAmnt = benifitAmnt;
		this.denialReason = denialReason;
	}
	
	
}
