package org.workable.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.workable.entity.Review;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query(value = "select * from review where movie_id = ?1 and user_id = ?2", nativeQuery = true)
    Optional<Review> findByMovieIdAndUserId(Long movieId, Long userId);
}
