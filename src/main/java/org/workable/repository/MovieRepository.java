package org.workable.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.workable.entity.Movie;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    //@Query(value ="SELECT m FROM Movie m WHERE m.addedBy = ?1",  nativeQuery = true)
    @Query(value = "select * from movie where user_id = ?1", nativeQuery = true)
    List<Movie> findAllByUserId(Long userId);
}
