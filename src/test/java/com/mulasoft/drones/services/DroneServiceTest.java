package com.mulasoft.drones.services;

import com.mulasoft.drones.dtos.ResponseDto;
import com.mulasoft.drones.models.Drone;
import com.mulasoft.drones.models.DroneState;
import com.mulasoft.drones.models.Medication;
import com.mulasoft.drones.repositories.DroneRepository;
import com.mulasoft.drones.repositories.MedicationRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;



@RunWith( SpringRunner.class)
@SpringBootTest
public class DroneServiceTest
{

    @MockBean
    MedicationRepository medicationRepository;

    @MockBean
    DroneRepository droneRepository;

    @Autowired
    private DroneService droneService;


    @Test
    public void createDroneSuccess(){
        droneService.createDrone( Drone.builder().build() );
        verify( droneRepository ).save( any() );
    }

    @Test
    public void loadMedicationFailBecauseBatteryLevel(){
        Drone drone = Drone.builder().batteryCapacity( 20.0 ).build();
        ResponseDto<Boolean> response = droneService.loadMedications( drone, List.of(1L) );

        assertFalse( response.getData() );
        assertEquals( "Battery level is below 25%", response.getMessage() );

        verify( medicationRepository, times( 0 ) ).findAllById( anyList() );
        verify( droneRepository, times( 0 ) ).save( any() );
    }

    @Test
    public void loadMedicationFailBecauseDroneState(){
        Drone drone = Drone.builder().batteryCapacity( 26.0 )
              .state( DroneState.LOADED ).build();
        ResponseDto<Boolean> response = droneService.loadMedications( drone, List.of(1L) );

        assertFalse( response.getData() );
        assertEquals( "Drone has invalid status", response.getMessage() );

        verify( medicationRepository, times( 0 ) ).findAllById( anyList() );
        verify( droneRepository, times( 0 ) ).save( any() );
    }

    @Test
    public void loadMedicationFailBecauseMedications(){
        Drone drone = Drone.builder().batteryCapacity( 26.0 )
                           .state( DroneState.IDLE ).build();
        when( medicationRepository.findAllById( anyList() ) ).thenReturn( List.of() );
        ResponseDto<Boolean> response = droneService.loadMedications( drone, List.of(1L, 2L, 3L) );



        assertFalse( response.getData() );
        assertEquals( "Medications not found", response.getMessage() );

        verify( medicationRepository, times( 1 ) ).findAllById( anyList() );
        verify( droneRepository, times( 0 ) ).save( any() );
    }

    @Test
    public void loadMedicationFailBecauseMedicationsWeight(){


        Medication medication = Medication.builder().weight( 20.0 ).build();
        when( medicationRepository.findAllById( anyList() ) ).thenReturn( List.of(medication) );

        Drone drone = Drone.builder().batteryCapacity( 26.0 )
                           .state( DroneState.IDLE ).weightLimit( 10.0 ).build();
        ResponseDto<Boolean> response = droneService.loadMedications( drone, List.of(1L) );

        assertFalse( response.getData() );
        assertEquals( "Medications weight exceeds drone capacity", response.getMessage() );

        verify( medicationRepository, times( 1 ) ).findAllById( anyList() );
        verify( droneRepository, times( 0 ) ).save( any() );
    }

    @Test
    public void loadMedicationSuccess(){


        Medication medication = Medication.builder().weight( 20.0 ).build();
        when( medicationRepository.findAllById( anyList() ) ).thenReturn( List.of(medication) );

        Drone drone = Drone.builder().batteryCapacity( 26.0 )
                           .state( DroneState.IDLE ).weightLimit( 50.0 ).build();
        ResponseDto<Boolean> response = droneService.loadMedications( drone, List.of(1L) );

        assertTrue( response.getData() );

        verify( medicationRepository, times( 1 ) ).findAllById( anyList() );
        verify( droneRepository, times( 1 ) ).save( any() );
    }

}
