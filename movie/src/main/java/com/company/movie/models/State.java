package com.company.movie.models;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.SQLRestriction;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "states")
@SQLRestriction("deleted_at IS NULL")
public class State {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "cvegeo")
    private String cvegeo;

@Column(name = "cve_ent", unique = true)
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

    /* 
    public function municipalities()
    {
        return $this->hasMany(Municipality::class, 'cve_ent', 'cve_ent');
    }
    */
    @JsonIgnore
    @OneToMany (mappedBy = "state")
    private List<Municipality> municipalities;

    @PrePersist
    void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

//     @PreRemove
// void onDelete() {
//     if (municipalities != null) {
//         LocalDateTime now = LocalDateTime.now();
//         for (Municipality municipality : municipalities) {
//             municipality.setDeletedAt(now);
//         }
//     }
// }
}
