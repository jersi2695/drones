package com.mulasoft.drones.model;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;


@Entity
@Data
public class Medication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Pattern(regexp = "^[a-zA-Z0-9-_]+$")
    private String name;

    @Column(nullable = false)
    private Double weight;

    @Column(nullable = false, unique = true)
    @Pattern(regexp = "^[A-Z0-9_]+$")
    private String code;

    @Lob
    private byte[] image;

    // constructors, getters, setters
}