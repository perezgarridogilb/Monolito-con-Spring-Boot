package com.company.movie.repositories;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.company.movie.models.Vendor;

public interface VendorRepository extends JpaRepository<Vendor, Integer> {

    @Query("from Vendor v order by v.name")
    List<Vendor> findAll();
}
