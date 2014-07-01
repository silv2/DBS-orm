package sk.fiit.silvia.dbs.persistancemanagers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

import sk.fiit.silvia.dbs.Gui;
import sk.fiit.silvia.dbs.models.Lokalita;
import sk.fiit.silvia.dbs.models.Rezervacia;
import sk.fiit.silvia.dbs.models.TypBicykla;
import sk.fiit.silvia.dbs.models.Zakaznik;
import sk.fiit.silvia.dbs.models.Zlava;
import sk.fiit.silvia.dbs.utils.HibernateUtil;

public class ReservationManager {

	private Gui okno;

	public static List<Rezervacia> getAllReservations() {
		List<Rezervacia> result = new LinkedList<Rezervacia>();
		return HibernateUtil.getSessionFactory().getCurrentSession()
				.createCriteria(Rezervacia.class).list();
	}

	public ReservationManager(Gui okno) {
		this.okno = okno;
	}

	public void writeAllRez() {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		org.hibernate.Transaction tx = session.beginTransaction();
		
		List<Rezervacia> rez = getAllReservations();
		for (Iterator<Rezervacia> it = rez.iterator(); it.hasNext();) {
			Rezervacia rezervacia = it.next();
			okno.napisVysledok(" ID: " + rezervacia.getId()
					+ "\n Meno zákazníka : "
					+ rezervacia.getZakaznik().getMeno() + " "
					+ rezervacia.getZakaznik().getPriezvisko()
					+ "\n Dátum a èas: " + rezervacia.getDatum() + " , "
					+ rezervacia.getCas() + "\n Typ bicykla: "
					+ rezervacia.getTyp().getNazov_typu() + "\n Lokalita : "
					+ rezervacia.getLokalita().getNazov() + "\n");
		}

		tx.commit();

		if (session.isOpen()) {
			session.close();
		}

	}

	public void getFilterReservationsCus(String zakaznik) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		org.hibernate.Transaction tx = session.beginTransaction();

		Criteria criteria = session.createCriteria(Zakaznik.class).add(
				Restrictions.eq("id", Integer.parseInt(zakaznik)));

		List<Rezervacia> rez = getAllReservations();
		for (Object curCon : criteria.list()) {

			for (Iterator<Rezervacia> it = rez.iterator(); it.hasNext();) {
				Rezervacia rezervacia = it.next();
				if (rezervacia.getZakaznik() == curCon) {
					okno.napisVysledok(" ID: " + rezervacia.getId()
							+ "\n Meno zákazníka : "
							+ rezervacia.getZakaznik().getMeno() + " "
							+ rezervacia.getZakaznik().getPriezvisko()
							+ "\n Dátum a èas: " + rezervacia.getDatum()
							+ " , " + rezervacia.getCas() + "\n Typ bicykla: "
							+ rezervacia.getTyp().getNazov_typu()
							+ "\n Lokalita : "
							+ rezervacia.getLokalita().getNazov() + "\n");
				}
			}
		}

		tx.commit();

		if (session.isOpen()) {
			session.close();
		}

	}

	public void getFilterReservationsCusName(String zakaznik) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();

		org.hibernate.Transaction tx = session.beginTransaction();

		Criteria criteria = session.createCriteria(Zakaznik.class).add(
				Restrictions.ilike("meno", zakaznik,MatchMode.ANYWHERE));
		List<Zakaznik> result = criteria.list();
		List<Rezervacia> rez = getAllReservations();
		boolean preslo=false;
		for (Zakaznik curCon : result) {
			
			for (Iterator<Rezervacia> it = rez.iterator(); it.hasNext();) {
				Rezervacia rezervacia = it.next();
				if (rezervacia.getZakaznik().getMeno().equals(curCon.getMeno())) {
					okno.napisVysledok(" ID: " + rezervacia.getId()
							+ "\n Meno zákazníka : "
							+ rezervacia.getZakaznik().getMeno() + " "
							+ rezervacia.getZakaznik().getPriezvisko()
							+ "\n Dátum a èas: " + rezervacia.getDatum()
							+ " , " + rezervacia.getCas() + "\n Typ bicykla: "
							+ rezervacia.getTyp().getNazov_typu()
							+ "\n Lokalita : "
							+ rezervacia.getLokalita().getNazov() + "\n");
					preslo=true;
				}
			}
		}

		if(preslo==false) okno.napisVysledok("Zákazník s takýmto menom nemá žiadnu rezerváciu.");
		tx.commit();

		if (session.isOpen()) {
			session.close();
		}

	}

	public void statisticLok() {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();

		org.hibernate.Transaction tx = session.beginTransaction();

		Criteria criteria = session.createCriteria(Rezervacia.class);

		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.groupProperty("lokalita"), "lokalita");
		projectionList.add(Projections.rowCount(), "pocet");
		criteria.setProjection(projectionList);
		criteria.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Integer> keys = new ArrayList<Integer>();
		List<HashMap<String, Object>> results = criteria.list();
		long max = 0;
		Lokalita lok = new Lokalita();
		for (HashMap curCon : results) {
			long keyy = (long) curCon.get("pocet");

			if (keyy > max) {
				max = keyy;
				lok = (Lokalita) curCon.get("lokalita");
			}
		}

		okno.napisVysledok("Najèastejšia lokalita je : " + lok.getNazov());
		tx.commit();

		if (session.isOpen()) {
			session.close();
		}

	}

	public void statisticCust() {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();

		org.hibernate.Transaction tx = session.beginTransaction();
		Criteria criteria = session.createCriteria(Rezervacia.class);

		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.groupProperty("zakaznik"), "zakaznik");
		projectionList.add(Projections.rowCount(), "pocet");
		criteria.setProjection(projectionList);
		criteria.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Integer> keys = new ArrayList<Integer>();
		List<HashMap<String, Object>> results = criteria.list();
		long max = 0;
		Zakaznik zak = new Zakaznik();
		for (HashMap curCon : results) {
			long keyy = (long) curCon.get("pocet");

			if (keyy > max) {
				max = keyy;
				zak = (Zakaznik) curCon.get("zakaznik");
			}
		}

		okno.napisVysledok("Najèastejší zákazník je : " + zak.getMeno() + " "
				+ zak.getPriezvisko());
		tx.commit();

		if (session.isOpen()) {
			session.close();
		}

	}

	public void statistic() {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();

		org.hibernate.Transaction tx = session.beginTransaction();
		Criteria criteria = session.createCriteria(Rezervacia.class);

		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.groupProperty("typ"), "typ");
		projectionList.add(Projections.rowCount(), "pocet");
		criteria.setProjection(projectionList);
		criteria.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Integer> keys = new ArrayList<Integer>();
		List<HashMap<String, Object>> results = criteria.list();
		long max = 0;
		TypBicykla typ = new TypBicykla();
		for (HashMap curCon : results) {
			long keyy = (long) curCon.get("pocet");
			if (keyy > max) {
				max = keyy;
				typ = (TypBicykla) curCon.get("typ");
			}
		}

		okno.napisVysledok("Najèastejšie objednaný druh bicykla je : "
				+ typ.getNazov_typu() + " "+max);
		tx.commit();

		if (session.isOpen()) {
			session.close();
		}

	}

	public void getFilterReservationsCusPriez(String zakaznik) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();

		org.hibernate.Transaction tx = session.beginTransaction();

		Criteria criteria = session.createCriteria(Zakaznik.class).add(
				Restrictions.ilike("priezvisko", "%" + zakaznik + "%"));
		boolean preslo=false;
		List<Rezervacia> rez = getAllReservations();
		for (Object curCon : criteria.list()) {

			for (Iterator<Rezervacia> it = rez.iterator(); it.hasNext();) {
				Rezervacia rezervacia = it.next();
				if (rezervacia.getZakaznik() == curCon) {
					okno.napisVysledok(" ID: " + rezervacia.getId()
							+ "\n Meno zákazníka : "
							+ rezervacia.getZakaznik().getMeno() + " "
							+ rezervacia.getZakaznik().getPriezvisko()
							+ "\n Dátum a èas: " + rezervacia.getDatum()
							+ " , " + rezervacia.getCas() + "\n Typ bicykla: "
							+ rezervacia.getTyp().getNazov_typu()
							+ "\n Lokalita : "
							+ rezervacia.getLokalita().getNazov() + "\n");
					preslo=true;
				}
			}
		}
		
		if(preslo==false) okno.napisVysledok("Zákazník s takýmto menom nemá žiadnu rezerváciu.");
		tx.commit();

		if (session.isOpen()) {
			session.close();
		}

	}

	public void getFilterReservationsPlace(int lokalita) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();

		org.hibernate.Transaction tx = session.beginTransaction();

		Criteria criteria = session.createCriteria(Lokalita.class).add(
				Restrictions.eq("id", lokalita));
		boolean preslo=false;
		List<Rezervacia> rez = getAllReservations();
		for (Object curCon : criteria.list()) {

			for (Iterator<Rezervacia> it = rez.iterator(); it.hasNext();) {
				Rezervacia rezervacia = it.next();
				if (rezervacia.getLokalita() == curCon) {
					okno.napisVysledok(" ID: " + rezervacia.getId()
							+ "\n Meno zákazníka : "
							+ rezervacia.getZakaznik().getMeno() + " "
							+ rezervacia.getZakaznik().getPriezvisko()
							+ "\n Dátum a èas: " + rezervacia.getDatum()
							+ " , " + rezervacia.getCas() + "\n Typ bicykla: "
							+ rezervacia.getTyp().getNazov_typu()
							+ "\n Lokalita : "
							+ rezervacia.getLokalita().getNazov() + "\n");
					preslo=true;
				}
			}
		}

		if(preslo==false) okno.napisVysledok("Nie je žiadna rezervácia na tejto lokalite.");
		tx.commit();

		if (session.isOpen()) {
			session.close();
		}

	}

	public void getFilterReservationsDate(String datum) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();

		org.hibernate.Transaction tx = session.beginTransaction();

		Criteria criteria = session.createCriteria(Rezervacia.class).add(
				Restrictions.ilike("datum", "%" + datum + "%"));

		boolean preslo=false;
		for (Object curCon : criteria.list()) {

			Rezervacia rezervacia = (Rezervacia) curCon;
			okno.napisVysledok(" ID: " + rezervacia.getId()
					+ "\n Meno zákazníka : "
					+ rezervacia.getZakaznik().getMeno() + " "
					+ rezervacia.getZakaznik().getPriezvisko()
					+ "\n Dátum a èas: " + rezervacia.getDatum() + " , "
					+ rezervacia.getCas() + "\n Typ bicykla: "
					+ rezervacia.getTyp().getNazov_typu() + "\n Lokalita : "
					+ rezervacia.getLokalita().getNazov() + "\n");
			preslo=true;

		}

		if(preslo==false) okno.napisVysledok("Nie je žiadna rezervácia v takomto dátume.");
		tx.commit();

		if (session.isOpen()) {
			session.close();
		}

	}

	public void putReservation(String datum, String cas, int druh,
			int zakaznik, int lokalita) throws SQLException {
		TypeBManager tm = new TypeBManager();
		CustomerManager cm = new CustomerManager();
		PlaceManager lm = new PlaceManager();
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();

		org.hibernate.Transaction tx = session.beginTransaction();
		Rezervacia rezervacia = new Rezervacia();
		rezervacia.setDatum(datum);
		rezervacia.setCas(cas);
		List<TypBicykla> rez = tm.getAllType();
		for (Iterator<TypBicykla> it = rez.iterator(); it.hasNext();) {
			TypBicykla typB = it.next();
			if (typB.getId() == druh)
				rezervacia.setTyp(typB);
		}

		List<Zakaznik> zak = cm.getAllCustomer();
		for (Iterator<Zakaznik> it = zak.iterator(); it.hasNext();) {
			Zakaznik zakaznikk = it.next();
			if (zakaznikk.getId() == zakaznik)
				rezervacia.setZakaznik(zakaznikk);
		}

		List<Lokalita> lok = lm.getAllPlace();
		for (Iterator<Lokalita> it = lok.iterator(); it.hasNext();) {
			Lokalita lokal = it.next();
			if (lokal.getId() == lokalita)
				rezervacia.setLokalita(lokal);
		}

		session.save(rezervacia);

		tx.commit();

		if (session.isOpen()) {
			session.close();

		}
		writeAllRez();
	}

	public void putReservationCust(String datum, String cas, int druh,
			String meno, String priezvisko, int lokalita, int zlava)
			throws SQLException {
		TypeBManager tm = new TypeBManager();
		DiscountManager dm = new DiscountManager();
		PlaceManager lm = new PlaceManager();
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();
	
		org.hibernate.Transaction tx = session.beginTransaction();

		Zakaznik zakaz = new Zakaznik();
		zakaz.setMeno(meno);
		zakaz.setPriezvisko(priezvisko);
		List<Zlava> zlav = dm.getAllDisc();
		for (Iterator<Zlava> it = zlav.iterator(); it.hasNext();) {
			Zlava zlavaa = it.next();
			if (zlavaa.getId() == zlava)
				zakaz.setZlava(zlavaa);
			;
		}
		session.save(zakaz);
		Rezervacia rezervacia = new Rezervacia();
		rezervacia.setDatum(datum);
		rezervacia.setCas(cas);
		List<TypBicykla> rez = tm.getAllType();
		for (Iterator<TypBicykla> it = rez.iterator(); it.hasNext();) {
			TypBicykla typB = it.next();
			if (typB.getId() == druh)
				rezervacia.setTyp(typB);
		}

		rezervacia.setZakaznik(zakaz);

		List<Lokalita> lok = lm.getAllPlace();
		for (Iterator<Lokalita> it = lok.iterator(); it.hasNext();) {
			Lokalita lokal = it.next();
			if (lokal.getId() == lokalita)
				rezervacia.setLokalita(lokal);
		}
	
		session.save(rezervacia);

		tx.commit();
	
		if (session.isOpen()) {
			session.close();

		}
		writeAllRez();
	}

	public void deleteReservation(int id) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();
	
		org.hibernate.Transaction tx = session.beginTransaction();

		List<Rezervacia> list = getAllReservations();
		for (Iterator<Rezervacia> it = list.iterator(); it.hasNext();) {
			Rezervacia rezervacia = it.next();
			if (rezervacia.getId() == id)
				session.delete(rezervacia);
		}

	
		tx.commit();

		if (session.isOpen()) {
			session.close();

		}
		writeAllRez();
	}

	public void updateReservationDate(String datum, int id) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();

		org.hibernate.Transaction tx = session.beginTransaction();

		List<Rezervacia> list = getAllReservations();
		for (Iterator<Rezervacia> it = list.iterator(); it.hasNext();) {
			Rezervacia rezervacia = it.next();
			if (rezervacia.getId() == id) {
				rezervacia.setDatum(datum);
				session.saveOrUpdate(rezervacia);
			}
		}

	
		tx.commit();
	
		if (session.isOpen()) {
			session.close();

		}
		writeAllRez();
	}

	public void updateReservationTime(String cas, int id) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();
	
		org.hibernate.Transaction tx = session.beginTransaction();


		List<Rezervacia> list = getAllReservations();
		for (Iterator<Rezervacia> it = list.iterator(); it.hasNext();) {
			Rezervacia rezervacia = it.next();
			if (rezervacia.getId() == id) {
				rezervacia.setCas(cas);
				session.saveOrUpdate(rezervacia);
			}
		}


		tx.commit();
	
		if (session.isOpen()) {
			session.close();

		}
		writeAllRez();
	}

	public void updateReservationPlace(int lokalita, int id) {
		PlaceManager lm = new PlaceManager();
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();
	
		org.hibernate.Transaction tx = session.beginTransaction();
	

		List<Rezervacia> list = getAllReservations();
		for (Iterator<Rezervacia> it = list.iterator(); it.hasNext();) {
			Rezervacia rezervacia = it.next();
			if (rezervacia.getId() == id) {
				List<Lokalita> lok = lm.getAllPlace();
				for (Iterator<Lokalita> itt = lok.iterator(); itt.hasNext();) {
					Lokalita lokal = itt.next();
					if (lokal.getId() == lokalita)
						rezervacia.setLokalita(lokal);
				}
				session.saveOrUpdate(rezervacia);
			}
		}

	
		tx.commit();
	
		if (session.isOpen()) {
			session.close();

		}
		writeAllRez();
	}

	public void updateReservationType(int typ, int id) {
		TypeBManager tm = new TypeBManager();
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();
	
		org.hibernate.Transaction tx = session.beginTransaction();
	

		List<Rezervacia> list = getAllReservations();
		for (Iterator<Rezervacia> it = list.iterator(); it.hasNext();) {
			Rezervacia rezervacia = it.next();
			if (rezervacia.getId() == id) {
				List<TypBicykla> typb = tm.getAllType();
				for (Iterator<TypBicykla> itt = typb.iterator(); itt.hasNext();) {
					TypBicykla typbic = itt.next();
					if (typbic.getId() == typ)
						rezervacia.setTyp(typbic);
				}
				session.saveOrUpdate(rezervacia);
			}
		}

		tx.commit();
	
		if (session.isOpen()) {
			session.close();

		}
		writeAllRez();
	}

}
