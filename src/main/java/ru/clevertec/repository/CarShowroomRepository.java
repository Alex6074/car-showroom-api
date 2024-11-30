package ru.clevertec.repository;

import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.clevertec.entity.CarShowroom;
import ru.clevertec.util.HibernateUtil;

import java.util.List;

public class CarShowroomRepository implements CrudRepository<CarShowroom, Long> {
    @Override
    public CarShowroom create(CarShowroom showroom) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSession()) {
            transaction = session.beginTransaction();
            session.persist(showroom);
            transaction.commit();
            return showroom;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }

    @Override
    public CarShowroom findById(Long id) {
        try (Session session = HibernateUtil.getSession()) {
            return session.get(CarShowroom.class, id);
        }
    }

    @Override
    public List<CarShowroom> findAll() {
        try (Session session = HibernateUtil.getSession()) {
            String hql = "FROM CarShowroom";
            return session.createQuery(hql, CarShowroom.class).list();
        }
    }

    @Override
    public CarShowroom update(CarShowroom showroom) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSession()) {
            transaction = session.beginTransaction();
            session.merge(showroom);
            transaction.commit();
            return showroom;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }

    @Override
    public void deleteById(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSession()) {
            transaction = session.beginTransaction();
            CarShowroom showroom = session.get(CarShowroom.class, id);
            if (showroom != null) {
                session.delete(showroom);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }

    public List<CarShowroom> getCarShowroomsUsingEntityGraph() {
        try (Session session = HibernateUtil.getSession();
             EntityManager entityManager = session.getEntityManagerFactory().createEntityManager()) {
            EntityGraph<?> entityGraph = entityManager
                    .getEntityGraph("CarShowroom.withCarsAndCategories");

            return session.createQuery("FROM CarShowroom", CarShowroom.class)
                    .setHint("javax.persistence.fetchgraph", entityGraph)
                    .getResultList();
        }
    }

    public List<CarShowroom> getCarShowroomsUsingJoinFetch() {
        try (Session session = HibernateUtil.getSession()) {
            String jpql = "SELECT DISTINCT cs FROM CarShowroom cs " +
                    "JOIN FETCH cs.cars c " +
                    "JOIN FETCH c.category";

            return session.createQuery(jpql, CarShowroom.class).list();
        }
    }

}
