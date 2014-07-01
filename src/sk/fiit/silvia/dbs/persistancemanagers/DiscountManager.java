package sk.fiit.silvia.dbs.persistancemanagers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import sk.fiit.silvia.dbs.Gui;
import sk.fiit.silvia.dbs.models.TypBicykla;
import sk.fiit.silvia.dbs.models.Zakaznik;
import sk.fiit.silvia.dbs.models.Zlava;
import sk.fiit.silvia.dbs.utils.HibernateUtil;

public class DiscountManager  {

private Gui okno;
	
	public DiscountManager(Gui okno){
		this.okno=okno;
	} 
	
	public static List<Zlava> getAllDisc(){
		List<Zlava> result = new LinkedList<Zlava>();
		return HibernateUtil.getSessionFactory().getCurrentSession().createCriteria(Zlava.class).list();
	}

	public DiscountManager(){}
	public  void writeAllDisc(){
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	    Session session = sessionFactory.getCurrentSession();
	    /** Starting the Transaction */
	    org.hibernate.Transaction tx = session.beginTransaction();
	    /** Creating Pojo */
	  
	    
	    List<Zlava> zlav = getAllDisc();
	    for(Iterator<Zlava> it = zlav.iterator();it.hasNext();) {
	    	Zlava zlava = it.next();
	    	this.okno.napisVysledok("ID z¾avy :" + zlava.getId()
					+ " jej názov :" + zlava.getNazov() + " a percentá: "
					+ zlava.getPercenta());
	    }
	   	    
	    /** Commiting the changes */
	    tx.commit();
	    /** Closing Session */
	    if(session.isOpen()){
	    	session.close();
	    }
	 
	}

}
