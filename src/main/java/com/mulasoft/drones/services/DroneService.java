package com.mulasoft.drones.services;

import com.mulasoft.drones.dtos.ResponseDto;
import com.mulasoft.drones.models.Drone;
import com.mulasoft.drones.models.DroneState;
import com.mulasoft.drones.models.Medication;
import com.mulasoft.drones.repositories.DroneRepository;
import com.mulasoft.drones.repositories.MedicationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class DroneService
{

    private final DroneRepository droneRepository;
    private final MedicationRepository medicationRepository;

    public Drone createDrone(Drone drone){
        return droneRepository.save( drone );
    }

    public Optional<Drone> getDrone( Long droneID){
        return droneRepository.findById( droneID );
    }

    public List<Drone> getDrones( DroneState droneState){
        return droneRepository.findByState( droneState );
    }

    public ResponseDto<Boolean> loadMedications( Drone drone, List<Long> medicationsIds){
        if(drone.getBatteryCapacity() < 25){
            return ResponseDto.<Boolean>builder().data( false ).message( "Battery level is below 25%" ).build();
        }

        if(!drone.getState().equals( DroneState.IDLE )){
            return ResponseDto.<Boolean>builder().data( false ).message( "Drone has invalid status" ).build();
        }

        List<Medication> medications = medicationRepository.findAllById( medicationsIds );
        if(medicationsIds.size() != medications.size()){
            // There are some medication ids that are not found
            return ResponseDto.<Boolean>builder().data( false ).message( "Medications not found" ).build();
        }

        double medicationsWeight = medications.stream().mapToDouble( Medication::getWeight ).sum();

        if( medicationsWeight > drone.getWeightLimit()){
            return ResponseDto.<Boolean>builder().data( false ).message( "Medications weight exceeds drone capacity" ).build();
        }

        drone.getMedications().addAll( medications );
        drone.setState( DroneState.LOADED );
        droneRepository.save( drone );
        return ResponseDto.<Boolean>builder().data( true ).build();
    }
}
