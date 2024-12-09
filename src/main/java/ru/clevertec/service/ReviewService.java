package ru.clevertec.service;

import ru.clevertec.entity.Car;
import ru.clevertec.entity.Client;
import ru.clevertec.entity.Review;
import ru.clevertec.repository.impl.CarRepository;
import ru.clevertec.repository.impl.ClientRepository;
import ru.clevertec.repository.impl.ReviewRepository;

import java.util.List;

public class ReviewService {

    private final ReviewRepository reviewRepository = new ReviewRepository();
    private final ClientRepository clientRepository = new ClientRepository();
    private final CarRepository carRepository = new CarRepository();

    public void addReview(Client client, Car car, String text, int rating) {
        Review review = new Review();
        review.setClient(client);
        review.setCar(car);
        review.setText(text);
        review.setRating(rating);

        reviewRepository.save(review);
    }

    public List<Review> searchReviews(String keyword) {
        return reviewRepository.searchReviews(keyword);
    }

    public void updateReview(Review review) {
        reviewRepository.update(review);
    }

    public void deleteReview(Review review) {
        reviewRepository.delete(review);
    }

    public Review getReviewById(Long id) {
        return reviewRepository.findById(id);
    }

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public List<Review> findReviewsByRating(int rating) {
        return reviewRepository.findReviewsByRating(rating);
    }
}
