package com.mulasoft.drones.repositories;

import com.mulasoft.drones.models.Drone;
import com.mulasoft.drones.models.DroneState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface DroneRepository extends JpaRepository<Drone, Long>
{
    List<Drone> findByState ( DroneState state );
}
