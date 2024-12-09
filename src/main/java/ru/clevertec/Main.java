package ru.clevertec;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.clevertec.entity.Car;
import ru.clevertec.entity.CarShowroom;
import ru.clevertec.entity.Category;
import ru.clevertec.entity.Client;
import ru.clevertec.entity.Review;
import ru.clevertec.service.CarService;
import ru.clevertec.service.CarShowroomService;
import ru.clevertec.service.CategoryService;
import ru.clevertec.service.ClientService;
import ru.clevertec.service.ReviewService;
import ru.clevertec.util.HibernateUtil;

import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        CarService carService = new CarService();
        ClientService clientService = new ClientService();
        CarShowroomService showroomService = new CarShowroomService();
        CategoryService categoryService = new CategoryService();
        ReviewService reviewService = new ReviewService();

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            // === CRUD для всех сущностей ===
            // Создание автосалонов
            CarShowroom showroom1 = CarShowroom.builder()
                    .name("Elite Motors")
                    .address("123 Main Street")
                    .build();
            showroomService.addShowroom(showroom1);

            CarShowroom showroom2 = CarShowroom.builder()
                    .name("Luxury Cars")
                    .address("456 Elm Street")
                    .build();
            showroomService.addShowroom(showroom2);

            // Создание категорий
            Category categorySedan = Category.builder()
                    .name("Sedan")
                    .build();
            categoryService.addCategory(categorySedan);

            Category categorySUV = Category.builder()
                    .name("SUV")
                    .build();
            categoryService.addCategory(categorySUV);

            // Добавление автомобилей
            Car car1 = Car.builder()
                    .model("Camry")
                    .brand("Toyota")
                    .year(2022)
                    .price(30000.0)
                    .category(categorySedan)
                    .showroom(showroom1)
                    .build();
            Car car2 = Car.builder()
                    .model("Accord")
                    .brand("Honda")
                    .year(2021)
                    .price(28000.0)
                    .category(categorySedan)
                    .showroom(showroom1)
                    .build();
            Car car3 = Car.builder()
                    .model("RAV4")
                    .brand("Toyota")
                    .year(2023)
                    .price(35000.0)
                    .category(categorySUV)
                    .showroom(showroom2)
                    .build();
            Car car4 = Car.builder()
                    .model("CR-V")
                    .brand("Honda")
                    .year(2022)
                    .price(34000.0)
                    .category(categorySUV)
                    .showroom(showroom2)
                    .build();

            carService.addCar(car1);
            carService.addCar(car2);
            carService.addCar(car3);
            carService.addCar(car4);

            // Обновление автомобиля
            car1.setPrice(31000.0);
            carService.updateCar(car1);

            // Удаление автомобиля
            carService.deleteCar(car2);

            // === Привязка автомобиля к автосалону ===
            Car car5 = Car.builder()
                    .model("Civic")
                    .brand("Honda")
                    .year(2024)
                    .price(29000.0)
                    .category(categorySedan)
                    .showroom(showroom2)
                    .build();
            carService.addCar(car5);

            // === Регистрация клиентов ===
            // === Привязка автомобиля к клиенту ===
            Client client1 = Client.builder()
                    .name("Alice Johnson")
                    .registrationDate(LocalDate.now())
                    .build();
            clientService.addClient(client1);
            clientService.buyCar(client1, car1);

            Client client2 = Client.builder()
                    .name("Bob Smith")
                    .registrationDate(LocalDate.now())
                    .build();
            clientService.addClient(client2);
            clientService.buyCar(client2, car3);

            // === Добавление отзывов ===
            reviewService.addReview(client1, car1, "Great performance and comfort!", 5);
            reviewService.addReview(client2, car3, "Excellent SUV with plenty of space.", 4);

            // === Поиск автомобилей ===
            // Поиск по параметрам
            System.out.println("\n=== Автомобили Toyota ===");
            List<Car> toyotaCars = carService.findCarsByFilters("Toyota", null, null, 0.0, 0.0);
            toyotaCars.forEach(System.out::println);

            // Сортировка по цене
            System.out.println("\n=== Автомобили, отсортированные по цене (убывание) ===");
            List<Car> sortedCars = carService.findCarsSortedByPrice(false);
            sortedCars.forEach(System.out::println);

            // Пагинация
            System.out.println("\n=== Пагинация автомобилей (страница 1, размер 2) ===");
            List<Car> paginatedCars = carService.findCarsWithPagination(1, 2);
            paginatedCars.forEach(System.out::println);

            // === Полнотекстовый поиск отзывов ===
            System.out.println("\n=== Поиск отзывов с ключевым словом 'SUV' ===");
            List<Review> suvReviews = reviewService.searchReviews("SUV");
            suvReviews.forEach(review -> {
                Hibernate.initialize(review.getClient());
                Hibernate.initialize(review.getCar());
            });
            suvReviews.forEach(System.out::println);

            // Поиск отзывов по рейтингу
            System.out.println("\n=== Поиск отзывов с рейтингом 5 ===");
            List<Review> reviewsByRating = reviewService.findReviewsByRating(5);
            reviewsByRating.forEach(System.out::println);

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            HibernateUtil.shutdown();
        }
    }
}
