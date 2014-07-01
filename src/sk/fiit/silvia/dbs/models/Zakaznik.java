package sk.fiit.silvia.dbs.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "zakaznik")
public class Zakaznik {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
	private int id;
	
	//private int percenta;
	
	@Column(name = "meno")
	private String meno;
	
	@Column(name = "priezvisko")
	private String priezvisko;
	
	@ManyToOne
	 @JoinColumn(name="zlava_id")
	  private Zlava zlava;
	
	
	
	//private String nazov;

	public Zlava getZlava() {
		return zlava;
	}

	public void setZlava(Zlava zlava) {
		this.zlava = zlava;
	}

	/*public int getPercenta() {
		return percenta;
	}

	public void setPercenta(int percenta) {
		this.percenta = percenta;
	}

	public String getNazov() {
		return nazov;
	}

	public void setNazov(String nazov) {
		this.nazov = nazov;
	}
*/
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMeno() {
		return meno;
	}

	public void setMeno(String meno) {
		this.meno = meno;
	}

	public String getPriezvisko() {
		return priezvisko;
	}

	public void setPriezvisko(String priezvisko) {
		this.priezvisko = priezvisko;
	}

	public Zakaznik(int id, String meno, String priezvisko, int percenta,
			String nazov) {
		this.id = id;
		this.meno = meno;
		this.priezvisko = priezvisko;
		//this.percenta = percenta;
		//this.nazov = nazov;
	}

	public Zakaznik(){
		
	}
}
