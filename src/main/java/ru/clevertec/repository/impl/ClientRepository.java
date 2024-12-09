package ru.clevertec.repository.impl;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.clevertec.entity.Car;
import ru.clevertec.entity.Client;
import ru.clevertec.repository.IBaseRepository;
import ru.clevertec.util.HibernateUtil;

import java.util.List;

public class ClientRepository implements IBaseRepository<Client, Long> {

    @Override
    public void save(Client client) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.save(client);
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(Client client) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.update(client);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(Client client) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.delete(client);
            session.getTransaction().commit();
        }
    }

    @Override
    public Client findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Client.class, id);
        }
    }

    @Override
    public List<Client> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Client", Client.class).list();
        }
    }

    public Client findClientWithCars(Long clientId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "SELECT c FROM Client c JOIN FETCH c.cars WHERE c.id = :id", Client.class)
                    .setParameter("id", clientId)
                    .getSingleResult();
        }
    }


    public void buyCar(Long clientId, Long carId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            Client client = session.get(Client.class, clientId);
            if (client == null) {
                throw new IllegalArgumentException("Client not found");
            }

            Hibernate.initialize(client.getCars());

            Car car = session.get(Car.class, carId);
            if (car == null) {
                throw new IllegalArgumentException("Car not found");
            }

            client.getCars().add(car);
            session.update(client);

            transaction.commit();
        }
    }
}