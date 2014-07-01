package sk.fiit.silvia.dbs.persistancemanagers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import sk.fiit.silvia.dbs.Gui;
import sk.fiit.silvia.dbs.models.Lokalita;
import sk.fiit.silvia.dbs.models.TypBicykla;
import sk.fiit.silvia.dbs.models.Zakaznik;
import sk.fiit.silvia.dbs.utils.HibernateUtil;

public class PlaceManager {

private Gui okno;
	
	public PlaceManager(Gui okno){
		this.okno=okno;
	} 
	
	public PlaceManager(){
		
	} 
	public static List<Lokalita> getAllPlace(){
		List<Lokalita> result = new LinkedList<Lokalita>();
		return HibernateUtil.getSessionFactory().getCurrentSession().createCriteria(Lokalita.class).list();
	}

	public  void writeAllPlace(){
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	    Session session = sessionFactory.getCurrentSession();
	    /** Starting the Transaction */
	    org.hibernate.Transaction tx = session.beginTransaction();
	    /** Creating Pojo */
	  
	    
	    List<Lokalita> place = getAllPlace();
	    for(Iterator<Lokalita> it = place.iterator();it.hasNext();) {
	    	Lokalita lokalita = it.next();
	    	this.okno.napisVysledok("ID lokality :" + lokalita.getId()
					+ " názov: " + lokalita.getNazov());
	    }
	   	    
	    /** Commiting the changes */
	    tx.commit();
	    /** Closing Session */
	    if(session.isOpen()){
	    	session.close();
	    }
	 
	}

}
