package ru.clevertec.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.clevertec.config.ConfigLoader;

import java.util.Properties;

public class HibernateUtil {
    private static final Logger logger = LoggerFactory.getLogger(HibernateUtil.class);
    private static final SessionFactory sessionFactory;

    static {
        try {
            Properties properties = new Properties();
            properties.put("hibernate.dialect", ConfigLoader.get("hibernate.dialect"));
            properties.put("hibernate.show_sql", ConfigLoader.get("hibernate.show_sql"));
            properties.put("hibernate.format_sql", ConfigLoader.get("hibernate.format_sql"));
            properties.put("hibernate.hbm2ddl.auto", ConfigLoader.get("hibernate.hbm2ddl.auto"));
            properties.put("hibernate.connection.driver_class", ConfigLoader.get("datasource.driver-class-name"));
            properties.put("hibernate.connection.url", ConfigLoader.get("datasource.url"));
            properties.put("hibernate.connection.username", ConfigLoader.get("datasource.username"));
            properties.put("hibernate.connection.password", ConfigLoader.get("datasource.password"));

            sessionFactory = new Configuration()
                    .addProperties(properties)
                    .addPackage("ru.clevertec.entity")
                    .addAnnotatedClass(ru.clevertec.entity.Car.class)
                    .addAnnotatedClass(ru.clevertec.entity.CarShowroom.class)
                    .addAnnotatedClass(ru.clevertec.entity.Client.class)
                    .addAnnotatedClass(ru.clevertec.entity.Category.class)
                    .addAnnotatedClass(ru.clevertec.entity.Review.class)
                    .buildSessionFactory();
        } catch (Throwable ex) {
            logger.error("Initial SessionFactory creation failed.", ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        logger.info("Shutting down Hibernate session factory.");
        getSessionFactory().close();
    }
}
