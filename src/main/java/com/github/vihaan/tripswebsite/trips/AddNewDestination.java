package com.github.vihaan.tripswebsite.trips;

import com.github.vihaan.tripswebsite.mappers.IMapper;
import com.github.vihaan.tripswebsite.trips.destinations.Destination;
import com.github.vihaan.tripswebsite.trips.destinations.DestinationDTO;
import com.github.vihaan.tripswebsite.validation.IValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class AddNewDestination {

    private final DestinationRepository destinationRepository;
    private final IMapper<Destination, DestinationDTO> destinationMapper;
    private final IValidation<DestinationDTO> destinationValidator;

    @Autowired
    public AddNewDestination(@Qualifier("destinationMapper") IMapper<Destination, DestinationDTO> destinationMapper,
                             @Qualifier("destinationValidation") IValidation<DestinationDTO> destinationValidator,
                             DestinationRepository destinationRepository){
        this.destinationMapper = destinationMapper;
        this.destinationValidator = destinationValidator;
        this.destinationRepository = destinationRepository;
    }

    public void addNewDestination(DestinationDTO destinationDTO){
        if(!destinationValidator.isValid(destinationDTO).isEmpty())
            return; // TODO
        Destination destination = destinationMapper.convertDtoToEntity(destinationDTO);
        destinationRepository.save(destination);
    }
}
