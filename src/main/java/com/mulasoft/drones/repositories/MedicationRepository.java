package com.mulasoft.drones.repositories;


import com.mulasoft.drones.models.Medication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MedicationRepository extends JpaRepository<Medication, Long>
{
}
