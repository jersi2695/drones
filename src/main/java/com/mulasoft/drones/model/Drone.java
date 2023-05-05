package com.mulasoft.drones.model;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
@Data
public class Drone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String serialNumber;

    @Enumerated( EnumType.STRING)
    private DroneModel model;

    @Column(nullable = false)
    private Double weightLimit;

    private Double batteryCapacity;

    @Enumerated(EnumType.STRING)
    private DroneState state;

}