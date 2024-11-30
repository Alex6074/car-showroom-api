package ru.clevertec.repository;

import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.clevertec.entity.Category;
import ru.clevertec.util.HibernateUtil;

import java.util.List;

public class CategoryRepository implements CrudRepository<Category, Long> {
    @Override
    public Category create(Category category) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSession()) {
            transaction = session.beginTransaction();
            session.persist(category);
            transaction.commit();
            return category;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }

    @Override
    public Category findById(Long id) {
        try (Session session = HibernateUtil.getSession()) {
            return session.get(Category.class, id);
        }
    }

    @Override
    public List<Category> findAll() {
        try (Session session = HibernateUtil.getSession()) {
            String hql = "FROM Category";
            return session.createQuery(hql, Category.class).list();
        }
    }

    @Override
    public Category update(Category category) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSession()) {
            transaction = session.beginTransaction();
            session.merge(category);
            transaction.commit();
            return category;
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
            Category category = session.get(Category.class, id);
            if (category != null) {
                session.delete(category);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }
}
