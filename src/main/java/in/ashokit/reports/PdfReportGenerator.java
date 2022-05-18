package in.ashokit.reports;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.swing.text.AttributeSet.ColorAttribute;

import org.apache.poi.ss.usermodel.Color;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;

import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;

import com.lowagie.text.pdf.PdfWriter;


import in.ashokit.entity.EDModel;

public class PdfReportGenerator {

	public void export(EDModel eModel) throws Exception{
		FileOutputStream fos=new FileOutputStream(new File(eModel.getCaseNo()+".pdf"));
		Document document=new Document(PageSize.A4);
		PdfWriter.getInstance(document, fos);
		document.open();
		
		Font font=FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		font.setSize(17);
		
		Paragraph p=new Paragraph("List Of Plans",font);
		p.setAlignment(Paragraph.ALIGN_CENTER);
		
		document.add(p);
		
		PdfPTable table=new PdfPTable(10);
		table.setWidthPercentage(100f);
		table.setWidths(new float[] {1.5f,1.5f,2.0f,4.0f,2.0f,1.5f,1.5f,1.5f,1.5f,1.5f});
		table.setSpacingBefore(10);
		
		PdfPCell cell=new PdfPCell();
		//cell.setBackgroundColor();
		cell.setPadding(5);
		Font font1=FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		font1.setColor(20, 20, 20);
		cell.setPhrase(new Phrase("ID",font1));
		table.addCell(cell);
		cell.setPhrase(new Phrase("CASE NO",font1));
		table.addCell(cell);
		cell.setPhrase(new Phrase("NAME",font1));
		table.addCell(cell);
		cell.setPhrase(new Phrase("SSN",font1));
		table.addCell(cell);
		cell.setPhrase(new Phrase("PLAN NAME",font1));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("STATUS",font1));
		table.addCell(cell);
		cell.setPhrase(new Phrase("START-DATE",font1));
		table.addCell(cell);
		cell.setPhrase(new Phrase("END-DATE",font1));
		table.addCell(cell);
		cell.setPhrase(new Phrase("BENIFIT AMOUNT",font1));
		table.addCell(cell);
		cell.setPhrase(new Phrase("DENIAL REASON",font1));
		table.addCell(cell);
		
		
			table.addCell(eModel.getId()+"");
			table.addCell(eModel.getCaseNo()+"");
			table.addCell(eModel.getHolderName());
			table.addCell(eModel.getHolderSsn()+"");
			table.addCell(eModel.getPlanName());
			table.addCell(eModel.getPlanStatus());
			table.addCell(eModel.getStartDate()==null?"--":eModel.getStartDate().toString());
			table.addCell(eModel.getEndDate()==null?"--":eModel.getEndDate().toString());
			table.addCell(eModel.getBenifitAmnt()==null?"--":eModel.getBenifitAmnt().toString());
			table.addCell(eModel.getDenialReason());
		
		document.add(table);
		document.close();
	}
}
