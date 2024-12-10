package ru.clevertec.repository.impl;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import ru.clevertec.entity.CarShowroom;
import ru.clevertec.repository.IBaseRepository;
import ru.clevertec.util.HibernateUtil;

import java.util.List;

public class CarShowroomRepository implements IBaseRepository<CarShowroom, Long> {

    @Override
    public void save(CarShowroom showroom) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.save(showroom);
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(CarShowroom showroom) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.update(showroom);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(CarShowroom showroom) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.delete(showroom);
            session.getTransaction().commit();
        }
    }

    @Override
    public CarShowroom findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(CarShowroom.class, id);
        }
    }

    @Override
    public List<CarShowroom> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from CarShowroom", CarShowroom.class).list();
        }
    }
    public CarShowroom findShowroomWithCars(Long showroomId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<CarShowroom> query = cb.createQuery(CarShowroom.class);
            Root<CarShowroom> showroom = query.from(CarShowroom.class);

            showroom.fetch("cars", JoinType.LEFT);
            query.select(showroom).where(cb.equal(showroom.get("id"), showroomId));

            return session.createQuery(query).getSingleResult();
        }
    }
}