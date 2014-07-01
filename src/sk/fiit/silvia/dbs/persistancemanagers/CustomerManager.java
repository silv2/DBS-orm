package sk.fiit.silvia.dbs.persistancemanagers;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import sk.fiit.silvia.dbs.Gui;
import sk.fiit.silvia.dbs.models.Bicykel;
import sk.fiit.silvia.dbs.models.Rezervacia;
import sk.fiit.silvia.dbs.models.Zakaznik;
import sk.fiit.silvia.dbs.utils.HibernateUtil;

public class CustomerManager{

	private Gui okno;
	
	public CustomerManager(Gui okno){
		this.okno=okno;
	} 
	
	public static List<Zakaznik> getAllCustomer(){
		List<Zakaznik> result = new LinkedList<Zakaznik>();
		return HibernateUtil.getSessionFactory().getCurrentSession().createCriteria(Zakaznik.class).list();
	}

	public CustomerManager(){}
	public  void writeAllCust(){
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	    Session session = sessionFactory.getCurrentSession();
	    /** Starting the Transaction */
	    org.hibernate.Transaction tx = session.beginTransaction();
	    /** Creating Pojo */
	  
	    
	    List<Zakaznik> cust = getAllCustomer();
	    for(Iterator<Zakaznik> it = cust.iterator();it.hasNext();) {
	    	Zakaznik zakaznik = it.next();
	    	this.okno.napisVysledok("ID zákazníka : " + zakaznik.getId()
			+ "  Meno a priezvisko zákazníka :  "
			+ zakaznik.getMeno() + "  " + zakaznik.getPriezvisko());
	    }
	   	    
	    /** Commiting the changes */
	    tx.commit();
	    /** Closing Session */
	    if(session.isOpen()){
	    	session.close();
	    }
	 
	}

	
}
