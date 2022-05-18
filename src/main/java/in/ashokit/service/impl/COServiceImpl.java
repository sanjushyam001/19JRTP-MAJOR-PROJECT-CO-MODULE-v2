package in.ashokit.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;


import in.ashokit.entity.CoTriggerModel;
import in.ashokit.entity.EDModel;

import in.ashokit.repository.CoTriggerRepository;
import in.ashokit.repository.EligdRepository;
import in.ashokit.repository.UserRepository;
import in.ashokit.service.COService;
import in.ashokit.utils.EmailStatus;
import in.ashokit.utils.EmailWithPdf;
import in.ashokit.utils.PdfGenerator;

@Service
public class COServiceImpl implements COService {


	@Autowired
	private EmailWithPdf emailWithPdf;
	
	@Autowired
	private CoTriggerRepository coTriggerRepo;
	
	@Autowired
	private EligdRepository eligdRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	Integer successCount=0;
	Integer failureCount=0;
	
	
	@Override
	public Map<String, Object> sendNotification() {
		
		Map<String, Object> statusMap=new HashedMap<>();
		successCount=0;
		failureCount=0;
		List<String> response=new ArrayList<>();
		
		//# 1 HERE WE ARE FETCHING ALL TRIGGERS FROM CO-TRIGGERS(ct_dtls) TABLE 
		
		List<CoTriggerModel> triggers = coTriggerRepo.findAll();
		
		/*
	    # HERE WE ARE PROCESSING THE triggers LIST OBJECT USING STREAM
		# filter(t -> t.getStatus().equalsIgnoreCase("pending")) PASS THE PREDICATE TO CHECK THE STATUS AND RETURN THE LIST OF OBJECT WHICH HAVING PENDING STATYS
		#map(t -> t.getCaseNo()) HERE WE EXTRACTING ALL CASE NO FROM FILTER OBJECTS  
		#collect(Collectors.toList()) HERE COLLECT ALL CASE NO AS LIST
		*/
		
		List<Integer> allTriggersCaseNo = triggers.stream()
				.filter(t -> t.getStatus().equalsIgnoreCase("pending"))
				.map(t -> t.getCaseNo()).collect(Collectors.toList());
		
		System.out.println("COServiceImpl.getTrigersCaseNoByStatus():: LIST OF CASE NO "+allTriggersCaseNo);
		
		//# 2 ITERATE ALL CASE NOs ONE BY ONE FROM 'allTriggersCaseNo' LIST
		
		allTriggersCaseNo.forEach(caseNo->{
			
			System.out.println("COServiceImpl.getTrigersCaseNoByStatus():: CASE NO ::  "+caseNo);
			
			//# 3 FIND MODEL OBJECT BY CASE NO 
			
			EDModel edModel = eligdRepo.findEdModelByCaseNo(caseNo);
			
			System.out.println("COServiceImpl.getTrigersCaseNoByStatus() EMODEL:: "+edModel);
			
			try {
				
				//ClassPathResource pdf = new ClassPathResource("static/"+edModel.getCaseNo()+".pdf");
				File file = new File("src\\main\\resources\\static\\pdf"+"\\"+edModel.getCaseNo()+".pdf");
				//String file = new File("src\\main\\resources\\static\\pdf").getAbsolutePath()+"\\" +edModel.getCaseNo()+".pdf";
				PdfGenerator.generatePdf(edModel,file);
				
				//# 4 FETCHING EMAIL FROM USER_DTLS TABLE BY USING CASE NO
				
				String email = userRepo.findUserByCaseNo(caseNo).getEmail();
				String subject="Eligibility Info";
				String body="";
				
				//# CALLING email() METHOD FOR SENDING EMAIL TO EACH APPLICANT
				//EmailWithPdf emailWithPdf=new EmailWithPdf();
				EmailStatus emailStatus = emailWithPdf.sendEmail(email, subject, body, file);
				FileInputStream fileInputStream=new FileInputStream(file);
				byte[] fileData=new byte[(int) file.length()];
				fileInputStream.read(fileData);
				if(emailStatus.isStatus()) {
					savePdfToDb(caseNo, fileData);
					response.add(emailStatus.getMesg());
					
				}
				successCount++;
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				failureCount++;
			}
			 
			
		});
		statusMap.put("SUCCESS: ", successCount);
		statusMap.put("FAILED: ", failureCount);
		statusMap.put("NOTIFCATIONS: ", response);
		return statusMap;
	}
	private boolean savePdfToDb(Integer caseNo,byte[] fileData) {
	
		System.out.println("COServiceImpl.savePdfToDb() " +caseNo);
		
		CoTriggerModel ct = coTriggerRepo.findByCaseNo(caseNo); 
		ct.setPdf(fileData);
		ct.setStatus("Completed");
		CoTriggerModel saveModel = coTriggerRepo.save(ct);
		
		return saveModel!=null?true:false;
	}

}
