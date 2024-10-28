package com.example.practicahibernatecoches.util;
import com.example.practicahibernatecoches.model.Coche;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
	static SessionFactory factory = null;
	static {
		Configuration cfg = new Configuration();
		cfg.configure("hibernate.cfg.xml");

		cfg.addAnnotatedClass(Coche.class);
		factory = cfg.buildSessionFactory();
	}

	public static SessionFactory getSessionFactory() {
		return factory;
	}

	public static Session getSession() {
		return factory.openSession();
	}

}