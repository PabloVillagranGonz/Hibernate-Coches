package com.example.practicahibernatecoches.dao;

import com.example.practicahibernatecoches.model.Coche;
import org.hibernate.Session;

import java.util.List;

public interface CocheDao {

	void  saveCoche(Coche coche, Session session);

	List<Coche> getAllCoche(Session session);

	void updateCoche(Coche coche, Session session);

	void deleteCocheById(int id,Session session);

	Coche buscarCoche(String matricula, Session session);
}
