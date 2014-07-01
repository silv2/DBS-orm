package sk.fiit.silvia.dbs;

import java.awt.Button;
import java.awt.Font;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import sk.fiit.silvia.dbs.persistancemanagers.BikeManager;
import sk.fiit.silvia.dbs.persistancemanagers.CustomerManager;
import sk.fiit.silvia.dbs.persistancemanagers.DiscountManager;
import sk.fiit.silvia.dbs.persistancemanagers.PlaceManager;
import sk.fiit.silvia.dbs.persistancemanagers.ReservationManager;
import sk.fiit.silvia.dbs.persistancemanagers.TypeBManager;
import java.awt.Color;
import javax.swing.UIManager;

public class Gui extends JFrame {

	CustomerManager cm = new CustomerManager(this);
	ReservationManager rm = new ReservationManager(this);
	TypeBManager tbm = new TypeBManager(this);
	BikeManager bmb = new BikeManager();
	DiscountManager dm = new DiscountManager(this);
	PlaceManager lm = new PlaceManager(this);
	TextArea textArea;
	JEditorPane editorPane_1;
	private final static String newline = "\n";
	public int state = 0, pocdel = 0;
	public String druh = new String();
	public String lokalita = new String();
	public String zlava = new String();
	public String meno = new String();
	public String priezvisko = new String();
	public String zakaznik = new String();
	public String datum = new String();
	public String cas = new String();
	public String idRez = new String();
	public String idRezAkt = new String();
	public String status = new String();

	public String getIdRezAkt() {
		return idRezAkt;
	}

	public void setIdRezAkt(String idRezAkt) {
		this.idRezAkt = idRezAkt;
	}

	public String getIdRez() {
		return idRez;
	}

	public void setIdRez(String idRez) {
		this.idRez = idRez;
	}

	public String getZakaznik() {
		return zakaznik;
	}

	public void setZakaznik(String zakaznik) {
		this.zakaznik = zakaznik;
	}

	public String getDatum() {
		return datum;
	}

	public void setDatum(String datum) {
		this.datum = datum;
	}

	public String getCas() {
		return cas;
	}

	public void setCas(String cas) {
		this.cas = cas;
	}

	public String getDruh() {
		return druh;
	}

	public void setDruh(String druh) {
		this.druh = druh;
	}

	public String getLokalita() {
		return lokalita;
	}

	public void setLokalita(String lokalita) {
		this.lokalita = lokalita;
	}

	public String getZlava() {
		return zlava;
	}

	public void setZlava(String zlava) {
		this.zlava = zlava;
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

	public Gui() {

		setBounds(100, 100, 680, 561);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnNovRezervcia = new JMenu("nov\u00E1 rezerv\u00E1cia");
		menuBar.add(mnNovRezervcia);

		JMenuItem mntmVybraLokalitu = new JMenuItem("vybra\u0165 lokalitu");
		mntmVybraLokalitu.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (pocdel == 1)
					pocdel = 0;
				prepisVysledok("");
				napisText("NapÌö vybran˙ lokalitu (jej ID) a stlaË tlaËidlo pri rezerv·cii.");
				lm.writeAllPlace();

				state = 4;

			}
		});
		mnNovRezervcia.add(mntmVybraLokalitu);

		JMenuItem mntmVybraDruhBicykla = new JMenuItem(
				"vybra\u0165 druh bicykla");
		mntmVybraDruhBicykla.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (pocdel == 1)
					pocdel = 0;
				prepisVysledok("");
				napisText("NapÌö vybran˝ druh bicykla (jeho ID) a stlaË tlaËidlo pri rezerv·cii.");
				tbm.writeAllType();
				state = 3;

			}
		});
		mnNovRezervcia.add(mntmVybraDruhBicykla);

		JMenuItem mntmPridajZakaznika = new JMenuItem(
				"pridanie z\u00E1kazn\u00EDka");
		mntmPridajZakaznika.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (pocdel == 1)
					pocdel = 0;
				prepisVysledok("");
				napisText("NapÌö meno z·kaznÌka");
				state = 1;
			}
		});

		JMenuItem mntmPridajDatumA = new JMenuItem(
				"pridanie d\u00E1tumu a \u010Dasu");
		mntmPridajDatumA.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (pocdel == 1)
					pocdel = 0;
				prepisVysledok("");
				state = 8;
				napisText("NapÌö d·tum (napr. 24.5.2014) rezerv·cie a stlaË tlaËidlo pri rezerv·cii.");
			}
		});
		mnNovRezervcia.add(mntmPridajDatumA);

		JMenuItem mntmVybratZExistujucich = new JMenuItem(
				"vybra\u0165 z existuj\u00FAcich z\u00E1kazn\u00EDkov");
		mntmVybratZExistujucich.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (pocdel == 1)
					pocdel = 0;
				prepisVysledok("");
				napisText("NapÌö tvoje ËÌslo (ID) ako z·kaznÌka a stlaË tlaËidlo pri rezerv·cii.");
				cm.writeAllCust();

				state = 6;
			}
		});
		mnNovRezervcia.add(mntmVybratZExistujucich);
		mnNovRezervcia.add(mntmPridajZakaznika);

		JMenuItem mntmVybraZavu = new JMenuItem("vybra\u0165 z\u013Eavu");
		mntmVybraZavu.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (pocdel == 1)
					pocdel = 0;
				prepisVysledok("");
				napisText("NapÌö vybran˙ zæavu (jej ID) a stlaË tlaËidlo pri rezerv·cii.");
				dm.writeAllDisc();

				state = 5;
			}
		});
		mnNovRezervcia.add(mntmVybraZavu);

		JMenu mnZrusenieRezervacie = new JMenu("zru\u0161enie rezerv\u00E1cie");
		menuBar.add(mnZrusenieRezervacie);

		JMenuItem mntmVybratRezervaciuNa = new JMenuItem(
				"vybra\u0165 rezerv\u00E1ciu na zru\u0161enie");
		mntmVybratRezervaciuNa.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (pocdel == 1)
					pocdel = 0;
				prepisVysledok("");
				napisText("NapÌö ID rezerv·cie, ktor˙ chceö zruöiù a stlaË tlaËidlo pri zruöenÌ.");
				rm.writeAllRez();

			}
		});
		mnZrusenieRezervacie.add(mntmVybratRezervaciuNa);

		JMenu mnAktualizaciaRezervacie = new JMenu(
				"aktualiz\u00E1cia rezerv\u00E1cie");
		menuBar.add(mnAktualizaciaRezervacie);

		JMenuItem mntmVybratRezervaciuNa_1 = new JMenuItem(
				"vybra\u0165 rezerv\u00E1ciu na aktualiz\u00E1ciu");
		mntmVybratRezervaciuNa_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (pocdel == 1)
					pocdel = 0;
				prepisVysledok("");
				napisText("NapÌö ID rezerv·cie, ktor˙ chceö aktualizovaù a stlaË tlaËidlo pri aktualiz·cii.");

				rm.writeAllRez();

				status = "rezervacie";
			}
		});
		mnAktualizaciaRezervacie.add(mntmVybratRezervaciuNa_1);

		JMenuItem mntmAktualizovatCas = new JMenuItem(
				"aktualizova\u0165 \u010Das");
		mntmAktualizovatCas.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (pocdel == 1)
					pocdel = 0;
				prepisVysledok("");
				napisText("NapÌö nov˝ Ëas (napr. 12.00) a stlaË tlaËidlo pri aktualiz·cii.");
				status = "cas";
			}
		});
		mnAktualizaciaRezervacie.add(mntmAktualizovatCas);

		JMenuItem mntmAktualizovatDatum = new JMenuItem(
				"aktualizova\u0165 d\u00E1tum");
		mntmAktualizovatDatum.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (pocdel == 1)
					pocdel = 0;
				prepisVysledok("");
				napisText("NapÌö nov˝ d·tum (napr. 25.4.2014) a stlaË tlaËidlo pri aktualiz·cii.");
				status = "datum";
			}
		});
		mnAktualizaciaRezervacie.add(mntmAktualizovatDatum);

		JMenuItem mntmAktualizovatLokalitu = new JMenuItem(
				"aktualizova\u0165 lokalitu");
		mntmAktualizovatLokalitu.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (pocdel == 1)
					pocdel = 0;
				prepisVysledok("");

				napisText("NapÌö akou lokalitou to chceö nahradiù (jej ID) a stlaË tlaËidlo pri aktualiz·cii.");
				lm.writeAllPlace();
				status = "lokalita";
			}
		});
		mnAktualizaciaRezervacie.add(mntmAktualizovatLokalitu);

		JMenuItem mntmAktualizovatTypBicykla = new JMenuItem(
				"aktualizova\u0165 typ bicykla");
		mntmAktualizovatTypBicykla.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (pocdel == 1)
					pocdel = 0;
				napisText("NapÌö ak˝m druhom bicykla to chceö nahradiù (jeho ID) a stlaË tlaËidlo pri aktualiz·cii.");
				tbm.writeAllType();
				status = "druh";

			}
		});
		mnAktualizaciaRezervacie.add(mntmAktualizovatTypBicykla);

		JMenu mnVyhladavanie = new JMenu("vyh\u013Ead\u00E1vanie");
		menuBar.add(mnVyhladavanie);

		JMenu mnFiltrovaniePodlaMena = new JMenu("filtrovanie pod\u013Ea mena");
		mnVyhladavanie.add(mnFiltrovaniePodlaMena);

		JMenuItem mntmZobrazitVsetkychZakaznikov = new JMenuItem(
				"zobrazi\u0165 v\u0161etk\u00FDch z\u00E1kazn\u00EDkov");
		mntmZobrazitVsetkychZakaznikov.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (pocdel == 1)
					pocdel = 0;
				prepisVysledok("");
				napisText("NapÌö ËÌslo z·kaznÌka, ktorÈho rezerv·cie chceö vidieù a stlaË tlaËidlo pri vyhæad·vanÌ.");
				cm.writeAllCust();

				status = "filzakaznik";
			}
		});
		mnFiltrovaniePodlaMena.add(mntmZobrazitVsetkychZakaznikov);

		JMenuItem mntmNapisCastMena = new JMenuItem(
				"nap\u00EDsa\u0165 meno (jeho \u010Das\u0165)");
		mntmNapisCastMena.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (pocdel == 1)
					pocdel = 0;
				prepisVysledok("");
				status = "filzakmeno";
				napisText("NapÌö meno (jeho Ëasù) z·kaznÌka, ktorÈho rezerv·cie chceö vidieù a stlaË tlaËidlo pri vyhæad·vanÌ.");
			}
		});
		mnFiltrovaniePodlaMena.add(mntmNapisCastMena);

		JMenuItem mntmNapsaPriezviskojeho = new JMenuItem(
				"nap\u00EDsa\u0165 priezvisko (jeho \u010Das\u0165)");
		mntmNapsaPriezviskojeho.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (pocdel == 1)
					pocdel = 0;
				prepisVysledok("");
				status = "filzakpriez";
				napisText("NapÌö priezvisko (jeho Ëasù) z·kaznÌka, ktorÈho rezerv·cie chceö vidieù a stlaË tlaËidlo pri vyhæad·vanÌ.");
			}
		});
		mnFiltrovaniePodlaMena.add(mntmNapsaPriezviskojeho);

		JMenuItem mntmFiltrovaniePodlaDatumu = new JMenuItem(
				"filtrovanie pod\u013Ea d\u00E1tumu");
		mntmFiltrovaniePodlaDatumu.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (pocdel == 1)
					pocdel = 0;
				prepisVysledok("");
				napisText("NapÌö d·tum (napr. 24.5.2014 ; 2014 ; 24.5.), pre ktor˝ chceö vidieù vöetky rezerv·cie \na stlaË tlaËidlo pri vyhæad·vanÌ.");
				status = "fildatum";
			}
		});
		mnVyhladavanie.add(mntmFiltrovaniePodlaDatumu);

		JMenu mnFiltrovaniePodlaLokality = new JMenu(
				"filtrovanie pod\u013Ea lokality");
		mnVyhladavanie.add(mnFiltrovaniePodlaLokality);

		JMenuItem mntmVybratZvolenuLokalitu = new JMenuItem(
				"vybra\u0165 zvolen\u00FA lokalitu");
		mntmVybratZvolenuLokalitu.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (pocdel == 1)
					pocdel = 0;
				prepisVysledok("");
				napisText("NapÌö ak˙ lokalitu chceö vyfiltrovaù (jej ID) a stlaË tlaËidlo pri vyhæad·vanÌ.");
				lm.writeAllPlace();

				status = "fillokalita";
			}
		});
		mnFiltrovaniePodlaLokality.add(mntmVybratZvolenuLokalitu);

		JMenu mnStatistika = new JMenu("\u0161tatistika");
		menuBar.add(mnStatistika);

		JMenuItem mntmNajobjednavanejsiDruhBicykla = new JMenuItem(
				"najobjedn\u00E1vanej\u0161\u00ED druh bicykla");
		mntmNajobjednavanejsiDruhBicykla.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (pocdel == 1)
					pocdel = 0;
				prepisVysledok("");
				rm.statistic();
			}
		});
		mnStatistika.add(mntmNajobjednavanejsiDruhBicykla);

		JMenuItem mntmNajastejZkaznk = new JMenuItem(
				"naj\u010Dastej\u0161\u00ED z\u00E1kazn\u00EDk");
		mntmNajastejZkaznk.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				if (pocdel == 1)
					pocdel = 0;
				prepisVysledok("");
				rm.statisticCust();
			}
		});
		mnStatistika.add(mntmNajastejZkaznk);

		JMenuItem mntmNajastejiaLokalita = new JMenuItem(
				"naj\u010Dastej\u0161ia lokalita");
		mntmNajastejiaLokalita.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (pocdel == 1)
					pocdel = 0;
				prepisVysledok("");
				rm.statisticLok();
			}
		});
		mnStatistika.add(mntmNajastejiaLokalita);

		JMenuBar menuBar_1 = new JMenuBar();
		menuBar.add(menuBar_1);
		getContentPane().setLayout(null);

		textArea = new TextArea();
		textArea.setEditable(false);
		textArea.setBounds(0, 176, 638, 305);
		textArea.setFont(new Font("Serif", Font.PLAIN, 15));
		getContentPane().add(textArea);

		final JEditorPane editorPane = new JEditorPane();
		editorPane.setBounds(10, 39, 225, 32);
		getContentPane().add(editorPane);

		Button button = new Button("Tla\u010Didlo pri rezerv\u00E1cii");
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (pocdel == 1)
					pocdel = 0;
				if (state == 3) {
					druh = editorPane.getText();
					prepisVysledok("");
					napisText("ZaznamenanÈ\n");
				}
				if (state == 2) {
					prepisVysledok("");
					priezvisko = editorPane.getText();
					napisText("ZaznamenanÈ\n");
				}
				if (state == 1) {
					prepisVysledok("");
					meno = editorPane.getText();
					napisText("Zadaj priezvisko\n");
					state = 2;
				}
				if (state == 4) {
					prepisVysledok("");
					lokalita = editorPane.getText();
					napisText("ZaznamenanÈ\n");
				}

				if (state == 5) {
					prepisVysledok("");
					zlava = editorPane.getText();
					napisText("ZaznamenanÈ\n");
				}
				if (state == 6) {
					prepisVysledok("");
					zakaznik = editorPane.getText();
					napisText("ZaznamenanÈ\n");
				}
				if (state == 9) {
					prepisVysledok("");
					cas = editorPane.getText();
					napisText("ZaznamenanÈ\n");
				}
				if (state == 8) {
					prepisVysledok("");
					datum = editorPane.getText();
					napisText("Zadaj Ëas (napr. 12.00) rezerv·cie\n");
					state = 9;
				}
				if (!zakaznik.isEmpty() && !datum.isEmpty() && !cas.isEmpty()
						&& !lokalita.isEmpty() && !druh.isEmpty())
					try {
						rm.putReservation(datum, cas, Integer.parseInt(druh),
								Integer.parseInt(zakaznik),
								Integer.parseInt(lokalita));
					} catch (NumberFormatException | SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				if (!zlava.isEmpty() && !meno.isEmpty()
						&& !priezvisko.isEmpty() && !datum.isEmpty()
						&& !cas.isEmpty() && !lokalita.isEmpty()
						&& !druh.isEmpty())
					try {
						rm.putReservationCust(datum, cas,
								Integer.parseInt(druh), meno, priezvisko,
								Integer.parseInt(lokalita),
								Integer.parseInt(zlava));
					} catch (NumberFormatException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			}
		});
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		button.setBounds(264, 21, 137, 22);
		getContentPane().add(button);

		Button button_1 = new Button("Tla\u010Didlo pri zru\u0161en\u00ED");
		button_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				prepisVysledok("");
				idRez = editorPane.getText();
				if (pocdel < 1) {
					napisText("Bola vybrat· rezerv·cia na vymazanie.\nSkutoËne chceö vymazaù tento z·znam? Ak ·no, stlaË eöte raz tlaËidlo. Ak nie, bez problÈmov \npokraËuj Ôalej.");
					pocdel++;
				} else {

					napisText("Bola vymazan· rezerv·cia s ID " + idRez);

					rm.deleteReservation(Integer.parseInt(idRez));

				}

			}
		});
		button_1.setBounds(264, 63, 137, 22);
		getContentPane().add(button_1);

		Button button_2 = new Button("Tla\u010Didlo pri aktualiz\u00E1cii");
		button_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (pocdel == 1)
					pocdel = 0;
				prepisVysledok("");
				if (status.compareTo("rezervacie") == 0
						|| (status.compareTo("datum") != 0
								&& status.compareTo("cas") != 0
								&& status.compareTo("lokalita") != 0 && status
								.compareTo("druh") != 0)) {
					idRezAkt = editorPane.getText();
					napisText("Bola vybrat· rezerv·cia na aktualiz·ciu, pre pokraËovanie aktualiz·cie vyber Ëo chceö aktualizovaù.");
				}
				if (status.compareTo("datum") == 0) {
					datum = editorPane.getText();
					prepisVysledok("");
					rm.updateReservationDate(datum, Integer.parseInt(idRezAkt));
				}

				if (status.compareTo("cas") == 0) {
					cas = editorPane.getText();
					prepisVysledok("");
					rm.updateReservationTime(cas, Integer.parseInt(idRezAkt));

				}
				if (status.compareTo("lokalita") == 0) {
					lokalita = editorPane.getText();
					prepisVysledok("");
					rm.updateReservationPlace(Integer.parseInt(lokalita),
							Integer.parseInt(idRezAkt));

				}
				if (status.compareTo("druh") == 0) {
					druh = editorPane.getText();
					prepisVysledok("");
					rm.updateReservationType(Integer.parseInt(druh),
							Integer.parseInt(idRezAkt));

				}
			}
		});
		button_2.setBounds(443, 21, 153, 22);
		getContentPane().add(button_2);

		Button button_3 = new Button(
				"Tla\u010Didlo pri vyh\u013Ead\u00E1van\u00ED");
		button_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				if (pocdel == 1)
					pocdel = 0;
				if (status.compareTo("filzakaznik") == 0) {
					zakaznik = editorPane.getText();
					prepisVysledok("");
					rm.getFilterReservationsCus(zakaznik);

				}
				if (status.compareTo("fildatum") == 0) {
					datum = editorPane.getText();
					prepisVysledok("");
					rm.getFilterReservationsDate(datum);

				}
				if (status.compareTo("fillokalita") == 0) {
					lokalita = editorPane.getText();
					prepisVysledok("");
					rm.getFilterReservationsPlace(Integer.parseInt(lokalita));

				}
				if (status.compareTo("filzakmeno") == 0) {
					zakaznik = editorPane.getText();
					prepisVysledok("");
					rm.getFilterReservationsCusName(zakaznik);

				}
				if (status.compareTo("filzakpriez") == 0) {
					zakaznik = editorPane.getText();
					prepisVysledok("");
					rm.getFilterReservationsCusPriez(zakaznik);

				}
			}
		});
		button_3.setBounds(443, 63, 153, 22);
		getContentPane().add(button_3);

		editorPane_1 = new JEditorPane();
		editorPane_1.setFont(new Font("Serif", Font.BOLD, 14));
		editorPane_1.setBackground(UIManager
				.getColor("CheckBoxMenuItem.background"));
		editorPane_1.setDisabledTextColor(Color.BLACK);
		editorPane_1.setSelectionColor(Color.BLACK);
		editorPane_1.setForeground(Color.BLACK);
		editorPane_1.setEditable(false);
		editorPane_1.setBounds(10, 109, 623, 61);
		getContentPane().add(editorPane_1);
	}

	public void napisText(String vysledok) {
		editorPane_1.setText(vysledok + newline);
	}

	public void napisVysledok(String vysledok) {
		textArea.append(vysledok + newline);

	}

	public void prepisVysledok(String vysledok) {
		textArea.setText(vysledok + newline);
	}
}
