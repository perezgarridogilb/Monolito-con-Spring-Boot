package com.company.movie.repositories;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.company.movie.models.Municipality;

public interface MunicipalityRepository extends JpaRepository<Municipality, Integer> {

    @Query(value = "SELECT * FROM municipalities WHERE cve_ent = :cveEnt AND cve_mun = :cveMun", nativeQuery = true)
    Optional<Municipality> findByCveEntAndCveMunWithTrashed(@Param("cveEnt") String cveEnt, @Param("cveMun") String cveMun);

    @Modifying
    @Query("UPDATE Municipality m SET m.deletedAt = :now WHERE m.cveEnt = :cveEnt")
    void softDeleteByCveEnt(@Param("now") LocalDateTime now, @Param("cveEnt") String cveEnt);
}
