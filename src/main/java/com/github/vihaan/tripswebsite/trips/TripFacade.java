package com.github.vihaan.tripswebsite.trips;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TripFacade {

    private final TripBookingProcessor tripBookingProcessor;
    private final TripRepositoriesFacade tripRepositoriesFacade;

    @Autowired
    public TripFacade(TripBookingProcessor tripBookingProcessor,
                      TripRepositoriesFacade tripRepositoriesFacade) {
        this.tripBookingProcessor = tripBookingProcessor;
        this.tripRepositoriesFacade = tripRepositoriesFacade;
    }

    public List<String> executeTripBooking(TripDTO tripDTO){
        return tripBookingProcessor.processTripBooking(tripDTO);
    }
}
