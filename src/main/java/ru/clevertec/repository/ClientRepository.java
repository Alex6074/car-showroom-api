package ru.clevertec.repository;


import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.clevertec.entity.Car;
import ru.clevertec.entity.Client;
import ru.clevertec.util.HibernateUtil;

import java.util.List;

public class ClientRepository implements CrudRepository<Client, Long> {

    @Override
    public Client create(Client client) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSession()) {
            transaction = session.beginTransaction();
            session.persist(client);
            transaction.commit();
            return client;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }

    @Override
    public Client findById(Long id) {
        try (Session session = HibernateUtil.getSession()) {
            return session.get(Client.class, id);
        }
    }

    @Override
    public List<Client> findAll() {
        try (Session session = HibernateUtil.getSession()) {
            String hql = "FROM Client";
            return session.createQuery(hql, Client.class)
                    .setHint("org.hibernate.cacheable", true)
                    .list();
        }
    }

    @Override
    public Client update(Client client) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSession()) {
            transaction = session.beginTransaction();
            session.merge(client);
            transaction.commit();
            return client;
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
            Client client = session.get(Client.class, id);
            if (client != null) {
                session.delete(client);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }

    public Client buyCar(Client client, Car car) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSession()) {
            transaction = session.beginTransaction();
            Client foundClient = session.get(Client.class, client.getId());
            Car foundCar = session.get(Car.class, car.getId());
            if (foundClient != null && foundCar != null) {
                foundClient.getCars().add(foundCar);
                session.merge(foundClient);
            }
            transaction.commit();
            return foundClient;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }
}
