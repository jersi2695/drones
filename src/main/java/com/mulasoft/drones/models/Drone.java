package com.mulasoft.drones.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.validation.constraints.Max;
import java.util.List;


@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Drone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DRONE_ID")
    private Long id;

    @Column(length = 100)
    private String serialNumber;

    @Enumerated( EnumType.STRING)
    private DroneModel model;

    @Column(nullable = false)
    private Double weightLimit;

    @Max(100)
    private Double batteryCapacity;

    @Enumerated(EnumType.STRING)
    private DroneState state;

    @OneToMany
    @JoinTable(
          name="DRONE_MEDICATION",
          joinColumns={ @JoinColumn(name="DRONE_ID", referencedColumnName="DRONE_ID") },
          inverseJoinColumns={ @JoinColumn(name="MEDICATION_ID", referencedColumnName="MEDICATION_ID", unique=true) }
    )
    private List<Medication> medications;

}