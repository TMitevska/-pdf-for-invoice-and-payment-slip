import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.FontSelector;
import com.itextpdf.text.pdf.PdfWriter;

public class application {
	
	private static File fontFile = new File("C:\\Users\\tamar\\OneDrive\\Desktop\\Arsenal-Regular.otf");
	private static Document faktura;
	private static Document uplatnica;
	private static FontSelector fs;
	private static Scanner scanner = new Scanner(System.in);
	private static BaseFont unicode;
	private static PdfWriter fakturaWriter;
	private static PdfWriter uplatnicaWriter;
	private static documentTable table;
	
	public static void main(String args[]) throws DocumentException, IOException{
			createHeaders(100);
			createDocumentBody();
		 	createDocumentFooter();
		    closeDocuments();
	}
	private static void createDocumentBody() throws DocumentException{
		table=new documentTable(fs);
		table.writeDataForTable(fs);
		table.writeDataForTable2(fs);
		faktura.add(table.getTable1());
		uplatnica.add(table.getTable1());
		writeSum();
		faktura.add(table.getTable2());
	}
	private static void closeDocuments(){
		faktura.close();
		uplatnica.close();
	}
	
	private static void addSumContent(Document document, String paragraph, FontSelector fs,int width) throws DocumentException { 
        Phrase phrase = fs.process(paragraph);
        Paragraph paragraph1 = new Paragraph(phrase);
	    paragraph1.setSpacingBefore(width);
	    paragraph1.setAlignment(2);
	    paragraph1.setIndentationRight(20);
        document.add(paragraph1);
}

	private static String brojNaFaktura(){
		System.out.println("Внеси број на фактура! пример(08/18)");
		return scanner.nextLine().replace("/", "_");
	}
	
	private static void init() throws DocumentException, IOException{
		faktura.open();
		uplatnica.open();
        fakturaWriter.getAcroForm().setNeedAppearances(true);
        uplatnicaWriter.getAcroForm().setNeedAppearances(true);
        unicode = BaseFont.createFont(fontFile.getAbsolutePath(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        fs = new FontSelector();
        fs.addFont(new Font(unicode));
	}
	
	private static void createHeaders(int height) throws DocumentException, IOException{
		faktura = new Document();
		uplatnica=new Document();
	 	String brfaktura=brojNaFaktura();
        fakturaWriter = PdfWriter.getInstance(faktura, new FileOutputStream("C:\\Users\\tamar\\Desktop\\dokumenti\\faktura\\faktura"+brfaktura+".pdf"));
        uplatnicaWriter = PdfWriter.getInstance(uplatnica, new FileOutputStream("C:\\Users\\tamar\\Desktop\\dokumenti\\uplatnica\\uplatnica"+brfaktura+".pdf"));
        init();
        header headerdata=new header(brfaktura.replace("_", "/"));
        faktura.add(headerdata.createFakturaHeader(faktura, fs, height));
        uplatnica.add(headerdata.createUplatnicaHeader(faktura, fs, height-20));
	}
	public static void writeSum() throws DocumentException{
		addSumContent(faktura, "Вкупно:     "+table.vkupno(), fs,0);
		addSumContent(uplatnica, "Вкупно:     "+table.vkupno(), fs,0);
	}
	
	private static void createDocumentFooter() throws DocumentException{
		footer footerdata=new footer("Оливер Митевски");
		faktura.add(footerdata.writeFakturaFooter(fs));
		uplatnica.add(footerdata.writeUplatnicaFooter(fs));
	}
}
