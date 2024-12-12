package ru.clevertec.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.clevertec.dto.ReviewDto;
import ru.clevertec.entity.Car;
import ru.clevertec.entity.Client;
import ru.clevertec.entity.Review;
import ru.clevertec.exception.ReviewNotFoundException;
import ru.clevertec.mapper.ReviewMapper;
import ru.clevertec.repository.CarRepository;
import ru.clevertec.repository.ClientRepository;
import ru.clevertec.repository.ReviewRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ClientRepository clientRepository;
    private final CarRepository carRepository;
    private final ReviewMapper reviewMapper;

    public Review addReview(Long clientId, Long carId, String text, Long rating) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new IllegalArgumentException("Client not found"));
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new IllegalArgumentException("Car not found"));

        Review review = Review.builder()
                .client(client)
                .car(car)
                .text(text)
                .rating(rating)
                .build();

        return reviewRepository.save(review);
    }

    public List<Review> searchReviews(String keyword) {
        return reviewRepository.findReviewsByKeyword(keyword);
    }

    public void updateReview(Long id, ReviewDto reviewDto) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ReviewNotFoundException("Review with ID " + id + " not found"));
        reviewMapper.updateReviewFromDto(reviewDto, review);
        reviewRepository.save(review);
    }

    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }

    public Review getReviewById(Long id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new ReviewNotFoundException("Review with ID " + id + " not found"));
    }

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public List<Review> findReviewsByRating(Long rating) {
        return reviewRepository.findByRating(rating);
    }
}
