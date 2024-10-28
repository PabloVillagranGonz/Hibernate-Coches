package com.example.practicahibernatecoches.dao;

import com.example.practicahibernatecoches.model.Coche;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class CocheDaoImpl implements CocheDao {

    @Override
    public void saveCoche(Coche coche, Session session) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(coche);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error al guardar el coche: " + e.getMessage(), e);
        }
    }

    @Override
    public Coche getCocheById(int id, Session session) {
        Transaction transaction = null;
        Coche coche = null;
        try {
            transaction = session.beginTransaction();
            coche = session.get(Coche.class, id);
            transaction.commit();


        } catch (Exception e) {

            if(transaction != null)
                transaction.rollback();
        }
        return coche;
    }

    @Override
    public List<Coche> getAllCoche(Session session) {
        Transaction transaction = null;
        List<Coche> coches = null;
        try {
            transaction = session.beginTransaction();
            coches = session.createQuery("from Coche", Coche.class).list();
            transaction.commit();
        } catch (Exception e) {
            if(transaction != null)
                transaction.rollback();
        }
        return coches;    }

    @Override
    public void updateCoche(Coche coche, Session session) {
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            session.saveOrUpdate(coche);
            transaction.commit();
        } catch (Exception e) {
            if(transaction != null)
                transaction.rollback();
        }
    }

    @Override
    public void deleteCocheById(int id, Session session) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Coche coche = session.get(Coche.class, id);
            session.delete(coche);
            transaction.commit();
        } catch (Exception e) {
            if(transaction != null)
                transaction.rollback();
        }
    }
}
