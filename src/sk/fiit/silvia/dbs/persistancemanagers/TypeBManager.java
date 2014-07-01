package sk.fiit.silvia.dbs.persistancemanagers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import sk.fiit.silvia.dbs.Gui;
import sk.fiit.silvia.dbs.models.Bicykel;
import sk.fiit.silvia.dbs.models.TypBicykla;
import sk.fiit.silvia.dbs.models.TypBicykla;
import sk.fiit.silvia.dbs.utils.HibernateUtil;

public class TypeBManager {


	private Gui okno;

	public static List<TypBicykla> getAllType(){
		List<TypBicykla> result = new LinkedList<TypBicykla>();
		return HibernateUtil.getSessionFactory().getCurrentSession().createCriteria(TypBicykla.class).list();
	}
	public TypeBManager(Gui okno){
		this.okno=okno;
	}
	public TypeBManager(){
		
	}
	
	public  void writeAllType(){
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	    Session session = sessionFactory.getCurrentSession();
	    
	     org.hibernate.Transaction tx = session.beginTransaction();
	     
	    
	    /** Starting the Transaction */
	   
	   
	    /** Creating Pojo */
	  
	    
	    List<TypBicykla> rez = getAllType();
	    
	    for(Iterator<TypBicykla> it = rez.iterator();it.hasNext();) {
	    	//System.out.println("bu2");
	    	TypBicykla typB = it.next();
	    	this.okno.napisVysledok("ID typu :" + typB.getId()
					+ ", názov: " + typB.getNazov_typu()+", cena :"+typB.getCena()+" eur");
	    }
	   	    
	    /** Commiting the changes */
	    tx.commit();
	    /** Closing Session */
	    if(session.isOpen()){
	    	session.close();
	    }
	 
	}

}
