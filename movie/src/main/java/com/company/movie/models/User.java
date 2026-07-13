package com.company.movie.models;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import com.company.movie.validation.ExistsByEmail;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String firstName;
    private String lastName;
@ExistsByEmail
    @Column(nullable = false)
    private String email;

    private String phone;
    private String address;
    // @JsonProperty(access = Access.WRITE_ONLY)
    private String password;
    private String username;

    @Transient
        @JsonProperty(access = Access.WRITE_ONLY)
    private boolean admin;

    @ManyToOne
    @JoinColumn(name = "rol_id")
    private Rol rol;

    private boolean enable;

    private Date createdAt;


}
