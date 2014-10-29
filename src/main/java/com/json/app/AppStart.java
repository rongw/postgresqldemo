package com.json.app;

import java.util.List;
import java.util.Scanner;

import org.apache.commons.lang.math.NumberUtils;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.json.app.bean.Candidate;
import com.json.persistence.HibernateSession;

/**
 * 
 * @author rongw
 */
public class AppStart {

	private static SessionFactory factory;
	private static Scanner sc = new Scanner(System.in);
	private static final String CAST_INT_JSON_OPERATOR = "cast(profile->>'%1$s' as int)=%2$d";
	private static final String JSON_OPERATOR = "profile->>'%1$s'='%2$s'";
			
	public static void main(String[] args) {
		factory = HibernateSession.getSessionFactory();
		boolean live = true;
		 while (live){
			 System.out.println("-------------Enter 1 for FirstName column search------------------");
			 System.out.println("-------------Enter 2 for JSON search------------------------");
			 System.out.println("-------------Enter 3 to quit------------------------");
			 int option = Integer.parseInt(getInput());
			 switch (option){
			 case 1:
				 System.out.println("Enter FirstName for search:");
				 demoHibernateORM(getInput());	 
				 break;
			 case 2:
				 System.out.println("Enter JSON search in format of key=value");
				 String input = getInput();
				 String params [] = input.split("=");
				 demoHibernateJson(params[0],params[1]);
				 break;
			 case 3:
				 live = false;
				 break;
			 }
		 }
		sc.close();
		System.exit(0);
	}
	
	private static String getInput(){		
		String line = sc.nextLine();		
		return line;
	}
	
	private static void demoHibernateORM(String firstName){
		Session session = factory.openSession();
		session.beginTransaction();
		Criteria cr = session.createCriteria(Candidate.class);
		cr.add(Restrictions.eq("firstName", firstName));		
		printResult(cr.list());
		session.close();
	}
	
	private static void demoHibernateJson(String key, String value){
		Session session = factory.openSession();
		session.beginTransaction();
		String criteria = null;
		if(NumberUtils.isNumber(value)){			
			criteria = String.format(CAST_INT_JSON_OPERATOR,key,Integer.parseInt(value));
		}else{			
			criteria = String.format(JSON_OPERATOR,key,value);
		}
		SQLQuery qr = session.createSQLQuery("SELECT * FROM app_main.candidate where "+criteria);
		qr.addEntity(Candidate.class);
		printResult(qr.list());
		session.close();
	}
	
	private static void printResult(final List<Candidate> candidate){
		for (Candidate c : candidate) {
			System.out.println( String.format("FirstName:%1$s||LastName:%2$s||Profile:%3$s", c.getFirstName(),c.getLastName(), c.getProfile()));
		}
	}

}
