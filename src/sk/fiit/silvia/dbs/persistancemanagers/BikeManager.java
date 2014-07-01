package sk.fiit.silvia.dbs.persistancemanagers;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import sk.fiit.silvia.dbs.models.Bicykel;
import sk.fiit.silvia.dbs.models.TypBicykla;
import sk.fiit.silvia.dbs.utils.HibernateUtil;

public class BikeManager  {

	public static List<Bicykel> getAllBike(){
		List<Bicykel> result = new LinkedList<Bicykel>();
		return HibernateUtil.getSessionFactory().getCurrentSession().createCriteria(Bicykel.class).list();
	}
	
	public void writeAllBike(){
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	    Session session = sessionFactory.getCurrentSession();
	    /** Starting the Transaction */
	    org.hibernate.Transaction tx = session.beginTransaction();
	    /** Creating Pojo */
	  
	    
	    List<Bicykel> bic =getAllBike();
	    for(Iterator<Bicykel> it = bic.iterator();it.hasNext();) {
	    	Bicykel bicykel = it.next();
	    	System.out.println(bicykel.getName() + ":" + bicykel.getId());
	    }

	    /** Commiting the changes */
	    tx.commit();
	    /** Closing Session */
	    if(session.isOpen()){
	    	session.close();
	}
	}
}
