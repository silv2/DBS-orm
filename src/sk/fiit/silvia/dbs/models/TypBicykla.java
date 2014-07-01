package sk.fiit.silvia.dbs.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name = "typbicykla")
public class TypBicykla {

	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
	private int id;
	
	@Column(name = "cena")
	private int cena;
	
	@Column(name = "nazov_typu")
	private String nazov_typu;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNazov_typu() {
		return nazov_typu;
	}

	public void setNazov_typu(String nazov_typu) {
		this.nazov_typu = nazov_typu;
	}

	public TypBicykla(int id, String nazov_typu) {
		this.id = id;
		this.nazov_typu = nazov_typu;
	}

	public int getCena() {
		return cena;
	}

	public void setCena(int cena) {
		this.cena = cena;
	}
	
	public TypBicykla(){}
	
}
