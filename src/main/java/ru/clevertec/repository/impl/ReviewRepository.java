package ru.clevertec.repository.impl;

import org.hibernate.Session;
import ru.clevertec.entity.Review;
import ru.clevertec.repository.IBaseRepository;
import ru.clevertec.util.HibernateUtil;

import java.util.List;

public class ReviewRepository implements IBaseRepository<Review, Long> {

    @Override
    public void save(Review review) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.save(review);
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(Review review) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.update(review);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(Review review) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.delete(review);
            session.getTransaction().commit();
        }
    }

    @Override
    public Review findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Review.class, id);
        }
    }

    @Override
    public List<Review> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Review", Review.class).list();
        }
    }

    public List<Review> searchReviews(String keyword) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT r FROM Review r " +
                    "JOIN FETCH r.client " +
                    "JOIN FETCH r.car " +
                    "WHERE r.text LIKE :keyword";
            return session.createQuery(hql, Review.class)
                    .setParameter("keyword", "%" + keyword + "%")
                    .getResultList();
        }
    }

    public List<Review> findReviewsByRating(int rating) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT r FROM Review r WHERE r.rating = :rating";
            return session.createQuery(hql, Review.class)
                    .setParameter("rating", rating)
                    .getResultList();
        }
    }
}