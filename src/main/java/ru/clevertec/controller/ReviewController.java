package ru.clevertec.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.entity.Review;
import ru.clevertec.service.ReviewService;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<Review> addReview(
            @RequestParam Long clientId,
            @RequestParam Long carId,
            @RequestParam String text,
            @RequestParam Long rating
    ) {
        Review review = reviewService.addReview(clientId, carId, text, rating);
        return ResponseEntity.status(HttpStatus.CREATED).body(review);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Review>> searchReviews(@RequestParam String keyword) {
        List<Review> reviews = reviewService.searchReviews(keyword);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/rating/{rating}")
    public ResponseEntity<List<Review>> findReviewsByRating(@PathVariable Long rating) {
        List<Review> reviews = reviewService.findReviewsByRating(rating);
        return ResponseEntity.ok(reviews);
    }
}




