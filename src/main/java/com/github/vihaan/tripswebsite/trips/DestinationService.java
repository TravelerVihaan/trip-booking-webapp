package com.github.vihaan.tripswebsite.trips;

import com.github.vihaan.tripswebsite.mappers.IMapper;
import com.github.vihaan.tripswebsite.trips.destinations.Destination;
import com.github.vihaan.tripswebsite.trips.destinations.DestinationDTO;
import com.github.vihaan.tripswebsite.validation.IValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DestinationService {

    private DestinationRepository destinationRepository;
    private IMapper<Destination, DestinationDTO> destinationMapper;
    private final IValidation<DestinationDTO> destinationValidator;

    @Autowired
    public DestinationService(@Qualifier("destinationMapper") IMapper<Destination, DestinationDTO> destinationMapper,
                              @Qualifier("destinationValidation") IValidation<DestinationDTO> destinationValidator,
                              DestinationRepository destinationRepository){
        this.destinationMapper = destinationMapper;
        this.destinationRepository = destinationRepository;
        this.destinationValidator = destinationValidator;
    }

    public void addNewDestination(DestinationDTO destinationDTO){
        if(!destinationValidator.isValid(destinationDTO).isEmpty())
            return; // TODO
        Destination destination = destinationMapper.convertDtoToEntity(destinationDTO);
        destinationRepository.save(destination);
    }

    private DestinationDTO getDestination(String dest){
        Optional<Destination> destination = destinationRepository.findByDestination(dest);
        return null; //TODO
    }

    public List<DestinationDTO> getAllDestinations(){
        List<Destination> destinations = destinationRepository.findAll();
        return destinations
                .stream()
                .map(dest -> destinationMapper.convertEntityToDto(dest))
                .collect(Collectors.toList());
    }
}
