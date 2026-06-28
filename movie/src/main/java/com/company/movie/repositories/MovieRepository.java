package com.company.movie.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.company.movie.models.Movie;

public interface MovieRepository extends JpaRepository<Movie, Integer>  {

    @Query("from Movie m order by m.name")
    List<Movie> findMovieByOrder();


    @Query("from Movie m where m.vendor.id =?1 order by m.name")
    List<Movie> findByVendor(int vendorId);

    @Query("from Movie m where m.name like concat('%', ?1, '%')")
    List<Movie> search(String query);


}