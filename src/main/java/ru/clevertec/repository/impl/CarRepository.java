package ru.clevertec.repository.impl;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.clevertec.entity.Car;
import ru.clevertec.repository.IBaseRepository;
import ru.clevertec.util.HibernateUtil;

import java.util.ArrayList;
import java.util.List;

public class CarRepository implements IBaseRepository<Car, Long> {

    @Override
    public void save(Car car) {
        if (car.getShowroom() == null) {
            System.err.println("Showroom is null for car: " + car);
            throw new IllegalArgumentException("Car must have a showroom");
        }
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(car);
            transaction.commit();
        }
    }


    @Override
    public void update(Car car) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.update(car);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(Car car) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.delete(car);
            session.getTransaction().commit();
        }
    }

    @Override
    public Car findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Car.class, id);
        }
    }

    @Override
    public List<Car> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Car", Car.class).list();
        }
    }

    public List<Car> findCarsSortedByPrice(boolean ascending) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Car> query = cb.createQuery(Car.class);
            Root<Car> car = query.from(Car.class);

            Order order = ascending ? cb.asc(car.get("price")) : cb.desc(car.get("price"));
            query.select(car).orderBy(order);

            return session.createQuery(query).getResultList();
        }
    }

    public List<Car> findCarsWithPagination(int pageNumber, int pageSize) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Car> query = cb.createQuery(Car.class);
            Root<Car> car = query.from(Car.class);

            query.select(car);

            return session.createQuery(query)
                    .setFirstResult((pageNumber - 1) * pageSize)
                    .setMaxResults(pageSize)
                    .getResultList();
        }
    }

    public Car findCarWithShowroom(Long carId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "SELECT c FROM Car c JOIN FETCH c.showroom WHERE c.id = :id", Car.class)
                    .setParameter("id", carId)
                    .getSingleResult();
        }
    }

    public List<Car> findCarsByFilters(String brand, Integer year, Long categoryId, Double minPrice, Double maxPrice) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Car> query = cb.createQuery(Car.class);
            Root<Car> car = query.from(Car.class);

            List<Predicate> predicates = new ArrayList<>();
            if (brand != null) predicates.add(cb.equal(car.get("brand"), brand));
            if (year != null) predicates.add(cb.equal(car.get("year"), year));
            if (categoryId != null) predicates.add(cb.equal(car.get("category").get("id"), categoryId));
            if (minPrice != null) predicates.add(cb.ge(car.get("price"), minPrice));
            if (maxPrice != null) predicates.add(cb.le(car.get("price"), maxPrice));

            query.select(car).where(cb.and(predicates.toArray(new Predicate[0])));
            return session.createQuery(query).getResultList();
        }
    }
}
