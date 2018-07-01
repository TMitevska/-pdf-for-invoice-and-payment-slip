import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.FontSelector;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

public class header {
	private PdfPTable table;
	private PdfPTable uplatnicaTable;
	private String brfaktura;
	private String buyerInfo;
	private String[] infoForOwner={"Омега Принт","Ул. Србо Томовиќ бр:23 Куманово","Даночен број: МК: 4017993112636","Жиро сметка: 320100004042185","Банка: Централна Кооперативна Банка"};
	Scanner scanner=new Scanner(System.in);
	public header(){}
	public header( String brfaktura) throws DocumentException {
		super();
		this.brfaktura = brfaktura;
		this.table=new PdfPTable(2);
		this.table.setWidthPercentage(100);
		this.table.setWidths(new float[] {1,1});
		this.uplatnicaTable=new PdfPTable(2);
		this.uplatnicaTable.setWidthPercentage(100);
		this.uplatnicaTable.setWidths(new float[] {1,1});
		setBuyerInfo();
	}

	public String getBrfaktura() {
		return brfaktura;
	}

	public void setBrfaktura(String brfaktura) {
		this.brfaktura = brfaktura;
	}
	
	public PdfPTable getUplatnicaTable() {
		return uplatnicaTable;
	}
	public void setUplatnicaTable(PdfPTable uplatnicaTable) {
		this.uplatnicaTable = uplatnicaTable;
	}
	public PdfPTable getTable() {
		return table;
	}

	public void setTable(PdfPTable table) {
		this.table = table;
	}
	@SuppressWarnings("deprecation")
	public PdfPTable createFakturaHeader(Document document,FontSelector fs,int height ) throws DocumentException{
		addTableContent(getOwnerInfo(),fs,height,0);
		Date startdate=Calendar.getInstance().getTime();
		Date enddate=Calendar.getInstance().getTime();
		enddate.setMonth(startdate.getMonth()+1);
		addTableContent("Фактура бр: "+this.getBrfaktura()+" \n"+"Датум: "+getDate(startdate)+" \n"+"Валута: "+getDate(enddate)+" \n\n\n"+getBuyerInfo(),fs,height,2);
		return this.getTable();
	}
	@SuppressWarnings("deprecation")
	public PdfPTable createUplatnicaHeader(Document document,FontSelector fs,int height ) throws DocumentException{
		addUplatnicaTableContent(getOwnerInfo(),fs,height,0);
		Date startdate=Calendar.getInstance().getTime();
		Date enddate=Calendar.getInstance().getTime();
		enddate.setMonth(startdate.getMonth()+1);
		addUplatnicaTableContent(getBuyerInfo(),fs,height,2);
		addUplatnicaTableContent("Уплатница бр: "+this.getBrfaktura(),fs,height,0);
		addUplatnicaTableContent("Време на издавање: "+getDate(startdate),fs,height,2);
		return this.getUplatnicaTable();
	}
	private  void addTableContent( String data, FontSelector fs,int height,int align) throws DocumentException { 
		PdfPCell cell=new PdfPCell(new Phrase(fs.process(data))); 
		cell.setHorizontalAlignment(align);
		cell.setFixedHeight(height);
		cell.setBorder(0);
		this.getTable().addCell(cell);
	}
	private  void addUplatnicaTableContent( String data, FontSelector fs,int height,int align) throws DocumentException { 
		PdfPCell cell=new PdfPCell(new Phrase(fs.process(data))); 
		cell.setHorizontalAlignment(align);
		cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
		cell.setFixedHeight(height);
		cell.setBorder(0);
		this.getUplatnicaTable().addCell(cell);
	}
	@SuppressWarnings("deprecation")
	private  String getDate(Date date){
		String result="";
		if(date.getDate()<9)
			result+="0";
		result+=date.getDate();
		if((date.getMonth()+1)<9)
			result+=".0";
		else
			result+=".";
		result+=(date.getMonth()+1);
		return result+="."+(date.getYear()+1900);
	}
	private String getOwnerInfo(){
		String result="";
		for(String string:this.infoForOwner)
			result+=string+" \n";
		return result;
	}
	private String getBuyerInfo(){
		return this.buyerInfo;
	}
	public void setBuyerInfo() {
		String result="";
		System.out.println("Внеси име на купувач!");
		result+=scanner.nextLine()+"\n";
		System.out.println("Внеси улица и град!");
		result+="Ул. "+scanner.nextLine()+"\n";
		System.out.println("Внеси дополнителни информации!");
		result+=scanner.nextLine()+"\n";
		this.buyerInfo = result;
	}
	
}
