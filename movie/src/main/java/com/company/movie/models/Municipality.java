package com.company.movie.models;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "municipalities")
public class Municipality {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "cve_ent")
    private String cveEnt;

    @Column(name = "cve_mun")
    private String cveMun;

    @Column(name = "nomgeo")
    private String nomgeo;

    @Column(name = "pob_total")
    private Integer pobTotal;

    @JsonIgnore
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @JsonIgnore
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    /**
    public function state()
    {
        return $this->belongsTo(State::class, 'cve_ent', 'cve_ent');
    }
     */
    // Se encarga exclusivamente de LEER los datos del relacionado
    @ManyToOne
    @JoinColumn(name = "cve_ent", referencedColumnName = "cve_ent", insertable = false, updatable = false)
    private State state;

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
