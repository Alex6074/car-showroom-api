package ru.clevertec.repository;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;
import ru.clevertec.entity.Car;
import ru.clevertec.entity.Client;
import ru.clevertec.entity.Review;
import ru.clevertec.util.HibernateUtil;

import java.util.List;

public class ReviewRepository implements CrudRepository<Review, Long> {
    @Override
    public Review create(Review review) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSession()) {
            transaction = session.beginTransaction();
            session.persist(review);
            transaction.commit();
            return review;
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
            throw e;
        }

    }

    @Override
    public Review findById(Long id) {
        try (Session session = HibernateUtil.getSession()) {
            return session.get(Review.class, id);
        }
    }

    @Override
    public List<Review> findAll() {
        try (Session session = HibernateUtil.getSession()) {
            String hql = "FROM Review";
            return session.createQuery(hql, Review.class).list();
        }
    }

    @Override
    public Review update(Review review) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSession()) {
            transaction = session.beginTransaction();
            session.merge(review);
            transaction.commit();
            return review;
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
            Review review = session.get(Review.class, id);
            if (review != null) {
                session.delete(review);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }

    public Review addReview(Client client, Car car, String text, int rating) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSession()) {
            transaction = session.beginTransaction();
            Client foundClient = session.get(Client.class, client.getId());
            Car foundCar = session.get(Car.class, car.getId());
            if (foundClient != null && foundCar != null) {
                Review review = new Review();
                review.setClient(foundClient);
                review.setCar(foundCar);
                review.setText(text);
                review.setRating(rating);
                session.persist(review);
                transaction.commit();
                return review;
            } else {
                throw new IllegalArgumentException("Client or Car not found");
            }
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
            throw e;
        }
    }

    public List<Review> searchByKeyword(String keyword) {
        try (Session session = HibernateUtil.getSession()) {
            SearchSession searchSession = Search.session(session);
            searchSession.massIndexer().startAndWait();
            return searchSession.search(Review.class)
                    .where(f -> f.match()
                            .fields("text")
                            .matching(keyword))
                    .fetchAllHits();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }

    public List<Review> searchByRating(int rating) {
        try (Session session = HibernateUtil.getSession()) {
            SearchSession searchSession = Search.session(session);

            return searchSession.search(Review.class)
                    .where(f -> f.match()
                            .field("rating")
                            .matching(rating))
                    .fetchHits(20);
        }
    }

}
