package in.ashokit.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name="CT_DTLS")
public class CoTriggerModel {

	@Id
	@GeneratedValue
	private Integer id;
	
	private Integer caseNo;
	
	@Lob
	@Column(name = "pdf", columnDefinition="LONGBLOB")
	private byte[] pdf;
	
	private String status;

	public CoTriggerModel(Integer caseNo, byte[] pdf, String status) {
		super();
		this.caseNo = caseNo;
		this.pdf = pdf;
		this.status = status;
	}
	
	
}
