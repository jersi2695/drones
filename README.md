# Drones service

# Run Service

Run DronesApplication class in order to initialize the application

# API

To register a new drone into the application use this endpoint:

```POST 'http://localhost:8080/drones'```
```
{
    "serialNumber" : "ABC243",
    "model" : "LIGHTWEIGHT",
    "weightLimit" : 50,
    "batteryCapacity" : 100,
    "state": "IDLE"
} 
```

To load a drone with medication items use this endpoint:

```POST 'http://localhost:8080/drones/{droneId}/medications'```

Send a list of medication ids
```
[
   1
] 
```

To check loaded medication items for a given drone

```GET 'http://localhost:8080/drones/{droneId}/medications'```

To check available drones for loading

```GET 'http://localhost:8080/drones/IDLE'```

To check drone battery level for a given drone

```GET 'http://localhost:8080/drones/{droneId}/battery'```