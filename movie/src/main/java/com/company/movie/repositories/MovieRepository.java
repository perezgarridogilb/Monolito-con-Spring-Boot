package com.company.movie.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.company.movie.models.Movie;

public interface MovieRepository extends JpaRepository<Movie, Integer>  {

    @Query("from Movie m order by m.name")
    Page<Movie> findMovieByOrder(Pageable pageable);


    @Query("from Movie m where m.vendor.id =?1 order by m.name")
    Page<Movie> findByVendor(int vendorId, Pageable pageable);

    @Query("from Movie m where m.name like concat('%', ?1, '%')")
    Page<Movie> search(String query, Pageable pageable);


}