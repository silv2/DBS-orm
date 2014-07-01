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
@Table(name = "rezervacia")
public class Rezervacia {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "cas")
	private String cas;

	@Column(name = "datum")
	private String datum;

	@ManyToOne
	@JoinColumn(name = "typbicykla")
	private TypBicykla typ;

	@ManyToOne
	@JoinColumn(name = "zakaznik_id")
	private Zakaznik zakaznik;

	public Lokalita getLokalita() {
		return lokalita;
	}

	public void setLokalita(Lokalita lokalita) {
		this.lokalita = lokalita;
	}

	@ManyToOne
	@JoinColumn(name = "lokalita_id")
	private Lokalita lokalita;

	public Zakaznik getZakaznik() {
		return zakaznik;
	}

	public void setZakaznik(Zakaznik zakaznik) {
		this.zakaznik = zakaznik;
	}

	public TypBicykla getTyp() {
		return typ;
	}

	public void setTyp(TypBicykla typ) {
		this.typ = typ;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCas() {
		return cas;
	}

	public void setCas(String cas) {
		this.cas = cas;
	}

	public String getDatum() {
		return datum;
	}

	public void setDatum(String datum) {
		this.datum = datum;
	}

}
