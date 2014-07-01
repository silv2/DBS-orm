package sk.fiit.silvia.dbs.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "zlava")
public class Zlava {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
	private int id;
	
	@Column(name = "percenta")
	private int percenta;
	
	@Column(name = "nazov")
	private String nazov;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPercenta() {
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

	public Zlava(int id, int percenta, String nazov) {
		this.id = id;
		this.percenta = percenta;
		this.nazov = nazov;
	}

	public Zlava(){}
}
