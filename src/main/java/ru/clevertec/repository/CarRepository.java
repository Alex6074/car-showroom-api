package ru.clevertec.repository;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import ru.clevertec.entity.Car;
import ru.clevertec.entity.CarShowroom;
import ru.clevertec.util.HibernateUtil;

import java.util.List;

public class CarRepository implements CrudRepository<Car, Long> {

    @Override
    public Car create(Car car) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSession()) {
            transaction = session.beginTransaction();
            session.persist(car);
            transaction.commit();
            return car;
        } catch (Exception e) {
            if (transaction != null && transaction.getStatus().canRollback()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    @Override
    public Car findById(Long id) {
        try (Session session = HibernateUtil.getSession()) {
            return session.get(Car.class, id);
        }
    }

    @Override
    public List<Car> findAll() {
        try (Session session = HibernateUtil.getSession()) {
            String hql = "SELECT c FROM Car c JOIN FETCH c.category JOIN FETCH c.showroom";
            return session.createQuery(hql, Car.class)
                    .setHint("org.hibernate.cacheable", true)
                    .list();
        }
    }

    @Override
    public Car update(Car car) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSession()) {
            transaction = session.beginTransaction();
            session.merge(car);
            transaction.commit();
            return car;
        } catch (Exception exception) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw exception;
        }
    }

    @Override
    public void deleteById(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSession()) {
            transaction = session.beginTransaction();
            Car car = session.get(Car.class, id);
            if (car != null) {
                session.delete(car);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }

    public void assignToShowroom(Car car, CarShowroom showroom) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSession()) {
            transaction = session.beginTransaction();
            Car foundCar = session.get(Car.class, car.getId());
            if (foundCar != null) {
                foundCar.setShowroom(showroom);
                session.merge(foundCar);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public List<Car> findCarsByParameters(String brand, Integer year, String category, Double minPrice, Double maxPrice) {
        try (Session session = HibernateUtil.getSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Car> cq = cb.createQuery(Car.class);
            Root<Car> root = cq.from(Car.class);

            Predicate predicate = cb.conjunction();

            if (brand != null) {
                predicate = cb.and(predicate, cb.equal(root.get("brand"), brand));
            }
            if (year != null) {
                predicate = cb.and(predicate, cb.equal(root.get("year"), year));
            }
            if (category != null) {
                predicate = cb.and(predicate, cb.equal(root.get("category").get("name"), category));
            }
            if (minPrice != null) {
                predicate = cb.and(predicate, cb.ge(root.get("price"), minPrice));
            }
            if (maxPrice != null) {
                predicate = cb.and(predicate, cb.le(root.get("price"), maxPrice));
            }

            cq.where(predicate);
            Query<Car> query = session.createQuery(cq);
            return query.list();
        }
    }

    public List<Car> findCarsSortedByPrice(boolean ascending) {
        try (Session session = HibernateUtil.getSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Car> cq = cb.createQuery(Car.class);
            Root<Car> root = cq.from(Car.class);

            if (ascending) {
                cq.orderBy(cb.asc(root.get("price")));
            } else {
                cq.orderBy(cb.desc(root.get("price")));
            }

            Query<Car> query = session.createQuery(cq);
            return query.list();
        }
    }

    public List<Car> findCarsWithPagination(int page, int pageSize) {
        try (Session session = HibernateUtil.getSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Car> cq = cb.createQuery(Car.class);
            Root<Car> root = cq.from(Car.class);

            Query<Car> query = session.createQuery(cq);
            query.setFirstResult((page - 1) * pageSize);
            query.setMaxResults(pageSize);
            return query.list();
        }
    }
}
