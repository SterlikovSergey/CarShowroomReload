package ru.clevertec.repository.impl;

import org.hibernate.Session;
import ru.clevertec.entity.Category;
import ru.clevertec.repository.IBaseRepository;
import ru.clevertec.util.HibernateUtil;

import java.util.List;

public class CategoryRepository implements IBaseRepository<Category, Long> {

    @Override
    public void save(Category category) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.save(category);
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(Category category) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.update(category);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(Category category) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.delete(category);
            session.getTransaction().commit();
        }
    }

    @Override
    public Category findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Category.class, id);
        }
    }

    @Override
    public List<Category> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Category", Category.class).list();
        }
    }

    public Category findCategoryWithCars(Long categoryId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "SELECT c FROM Category c LEFT JOIN FETCH c.cars WHERE c.id = :id", Category.class)
                    .setParameter("id", categoryId)
                    .getSingleResult();
        }
    }
}
