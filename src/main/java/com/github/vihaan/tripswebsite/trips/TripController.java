package com.github.vihaan.tripswebsite.trips;

import com.github.vihaan.tripswebsite.controller.IControllersStrings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(IControllersStrings.TRIPS_MAPPING)
public class TripController {

    // TODO delete this
    private final TripRepositoriesFacade tripRepositoriesFacade;
    private final TripFacade tripFacade;

    @Autowired
    public TripController(TripRepositoriesFacade tripRepositoriesFacade,
                          TripFacade tripFacade){
        this.tripRepositoriesFacade = tripRepositoriesFacade;
        this.tripFacade = tripFacade;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TripDTO>> getAllTrips(){
        List<TripDTO> trips = tripRepositoriesFacade.getAllTripDtos();
        return ResponseEntity.ok(trips);
    }

    @GetMapping(path = "/{tripNumber}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TripDTO> getTrip(@PathVariable String tripNumber){
        return ResponseEntity.of(tripRepositoriesFacade.getTripByVoucherNumber(tripNumber));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> addTripBooking(@RequestBody TripDTO trip){
        List<String> errors = tripFacade.executeTripBooking(trip);
        if(errors.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList());
        }
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }


}
