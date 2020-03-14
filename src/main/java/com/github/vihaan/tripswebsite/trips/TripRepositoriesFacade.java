package com.github.vihaan.tripswebsite.trips;

import com.github.vihaan.tripswebsite.mappers.IMapper;
import com.github.vihaan.tripswebsite.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
class TripRepositoriesFacade {

    private TripRepository tripRepository;
    private DestinationRepository destinationRepository;
    private IMapper<Trip, TripDTO> tripMapper;
    private IMapper<Destination, DestinationDTO> destinationMapper;
    private TripBooking tripBooking;

    @Autowired
    TripRepositoriesFacade(@Qualifier("destinationMapper") IMapper<Destination, DestinationDTO> destinationMapper,
                                  @Qualifier("tripMapper")IMapper<Trip, TripDTO> tripMapper,
                                  TripRepository tripRepository,
                                  DestinationRepository destinationRepository,
                                  TripBooking tripBooking) {
        this.tripRepository = tripRepository;
        this.destinationRepository = destinationRepository;
        this.tripMapper = tripMapper;
        this.destinationMapper = destinationMapper;
        this.tripBooking = tripBooking;
    }

    IMapper<Trip, TripDTO> getTripMapper(){ return tripMapper;}
    IMapper<Destination, DestinationDTO>  getDestinationMapper(){ return destinationMapper;}

    List<Destination> getAllDestinations(){
        return destinationRepository.findAll();
    }

    List<DestinationDTO> getAllDestinationDtos(){
        return destinationRepository.findAll()
                .stream()
                .map(destination -> destinationMapper.convertEntityToDto(destination))
                .collect(Collectors.toList());
    }

    List<Trip> getAllTrips(){
        return tripRepository.findAll();
    }

    Optional<TripDTO> getTripByVoucherNumber(String voucherNumber){
        try {
            Trip trip = tripRepository.findByVoucherNumberLike(voucherNumber).orElseThrow(NoSuchElementException::new);
            return Optional.of(tripMapper.convertEntityToDto(trip));
        }catch(NoSuchElementException e){
            return Optional.empty();
        }
    }

    Optional<DestinationDTO> getDestinationDtoByName(String destinationName){
          try {
                Destination destination = destinationRepository.findByDestination(destinationName).orElseThrow(NoSuchElementException::new);
                return Optional.of(destinationMapper.convertEntityToDto(destination));
            }catch(NoSuchElementException e){
                return Optional.empty();
            }
    }

    Optional<Destination> getDestinationByName(String destinationName){
        try {
            Destination destination = destinationRepository.findByDestination(destinationName).orElseThrow(NoSuchElementException::new);
            return Optional.of(destination);
        }catch(NoSuchElementException e){
            return Optional.empty();
        }
    }

    List<TripDTO> getAllTripDtos(){
        return tripRepository.findAll()
                .stream()
                .map(destination -> tripMapper.convertEntityToDto(destination))
                .collect(Collectors.toList());
    }

    Optional<Trip> getTripById(long id){
        return tripRepository.findById(id);
    }

    TripDTO getTripDtoById(long id){
        Trip trip = tripRepository.findById(id).orElseThrow();
        return tripMapper.convertEntityToDto(trip);
    }

    void saveTrip(Trip trip){
        tripRepository.save(trip);
    }

    List<String> doExecuteTripBooking(TripDTO tripDTO){
        return tripBooking.executeBooking(tripDTO);
    }

    void saveDestination(Destination destination){
        destinationRepository.save(destination);
    }
}
