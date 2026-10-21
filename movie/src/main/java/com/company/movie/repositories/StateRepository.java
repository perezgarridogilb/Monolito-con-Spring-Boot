package com.company.movie.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.company.movie.models.Movie;
import com.company.movie.models.State;

public interface StateRepository extends JpaRepository<State, Integer>  {
// Equivalente a ->withTrashed()->where('cve_ent', cveEnt)->first()
    @Query(value = "SELECT * FROM states WHERE cve_ent = :cveEnt", nativeQuery = true)
    Optional<State> findByCveEntWithTrashed(@Param("cveEnt") String cveEnt);
}
