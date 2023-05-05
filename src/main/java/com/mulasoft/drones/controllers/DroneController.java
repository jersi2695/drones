package com.mulasoft.drones.controllers;

import com.mulasoft.drones.dtos.BatteryDto;
import com.mulasoft.drones.dtos.ResponseDto;
import com.mulasoft.drones.models.Drone;
import com.mulasoft.drones.models.DroneState;
import com.mulasoft.drones.models.Medication;
import com.mulasoft.drones.services.DroneService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/drones")
@AllArgsConstructor
public class DroneController
{


    private final DroneService droneService;


    @PostMapping
    @ResponseStatus( HttpStatus.CREATED)
    public Drone createDrone(@RequestBody Drone drone) {
        return droneService.createDrone(drone);
    }

    @PostMapping( "/{droneId}/medications")
    public ResponseEntity<ResponseDto<Boolean>> loadMedications( @RequestBody
                                    List<Long> medicationsIds, @PathVariable("droneId") Long droneId ){

        Optional<Drone> maybeDrone = droneService.getDrone( droneId );

        if( maybeDrone.isEmpty() ){
            return ResponseEntity.notFound().build();
        }

        ResponseDto<Boolean> response = droneService.loadMedications(maybeDrone.get(), medicationsIds);

        if(response.data){
            return ResponseEntity.ok(response);
        }else{
            return ResponseEntity.badRequest().body( response );
        }


    }

    @GetMapping( "/{droneId}/medications")
    public ResponseEntity<List<Medication>> getMedicationsByDrone(@PathVariable("droneId") Long droneId){
        Optional<Drone> maybeDrone = droneService.getDrone( droneId );

        if( maybeDrone.isEmpty() ){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(maybeDrone.get().getMedications());
    }

    @GetMapping( "/{state}")
    public List<Drone> getDrones( @PathVariable("state")
                                  DroneState droneState ){
        return droneService.getDrones( droneState );
    }

    @GetMapping( "/{droneId}/battery")
    public ResponseEntity<BatteryDto> checkBatteryLevel( @PathVariable("droneId") Long droneId ){
        Optional<Drone> maybeDrone = droneService.getDrone( droneId );

        if( maybeDrone.isEmpty() ){
            return ResponseEntity.notFound().build();
        }

        Drone drone = maybeDrone.get();

        return ResponseEntity.ok(
              BatteryDto.builder().battery( drone.getBatteryCapacity() ).id( drone.getId() ).build());
    }

}
