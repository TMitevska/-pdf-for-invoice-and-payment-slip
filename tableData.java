import com.itextpdf.text.Phrase;

public class tableData {
	private Phrase artikal;
	private Phrase merka;
	private int kolicina;
	private int cena;
	
	public tableData(){}
	
	public tableData(Phrase artikal, Phrase merka, int kolicina, int cena) {
		super();
		this.artikal = artikal;
		this.merka = merka;
		this.kolicina = kolicina;
		this.cena = cena;
	}
	
	public Phrase getArtikal() {
		return artikal;
	}
	public void setArtikal(Phrase artikal) {
		this.artikal = artikal;
	}
	public Phrase getMerka() {
		return merka;
	}
	public void setMerka(Phrase merka) {
		this.merka = merka;
	}
	public int getKolicina() {
		return kolicina;
	}
	public void setKolicina(int kolicina) {
		this.kolicina = kolicina;
	}
	public float getCena() {
		return cena;
	}
	public void setCenaBezDDV(int cena) {
		this.cena = cena;
	}
	public float getCenaBezDDV(){
		return (float)this.cena/(float)1.18;
	}
	public int vkupenIznos(){
		return this.cena*this.kolicina;
	}
	
}
