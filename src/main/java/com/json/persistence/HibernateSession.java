package com.json.persistence;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;



public class HibernateSession{

	private static SessionFactory sessionFactory;
	
    public static String RESOURCE_PATH = "/hibernate.cfg.xml";

    
	public static SessionFactory getSessionFactory(){
		try {
	            Configuration configuration = new Configuration();
	            configuration.configure(RESOURCE_PATH);
	            StandardServiceRegistryBuilder ssrb = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
	            sessionFactory = configuration.buildSessionFactory(ssrb.build());
		} catch (Exception ex) {
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
		return sessionFactory;
	}
	

    

}
