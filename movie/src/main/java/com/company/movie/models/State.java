package com.company.movie.models;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "states")
public class State {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "cvegeo")
    private String cvegeo;

    @Column(name = "cve_ent")
    private String cveEnt;

    @Column(name = "nomgeo")
    private String nomgeo;

    @Column(name = "nom_abrev")
    private String nomAbrev;

    @Column(name = "pob_total")
    private Integer pobTotal;

    @Column(name = "pob_femenina")
    private Integer pobFemenina;

    @Column(name = "pob_masculina")
    private Integer pobMasculina;

    @Column(name = "total_viviendas_habitadas")
    private Integer totalViviendasHabitadas;

    @JsonIgnore
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @JsonIgnore
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    // @JsonIgnore
    // @OneToMany(mappedBy = "state")
    // private List<Municipality> municipalities;

    @PrePersist
    void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
