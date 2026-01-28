package com.bookmyshow.theatre.service;

import com.bookmyshow.location.model.City;
import com.bookmyshow.location.repository.CityRepository;
import com.bookmyshow.main.dto.InputCommand;
import com.bookmyshow.main.dto.OutputCommand;
import com.bookmyshow.theatre.dto.TheatreDto;
import com.bookmyshow.theatre.enums.TheatreStatus;
import com.bookmyshow.theatre.exception.TheatreValidationException;
import com.bookmyshow.theatre.model.Theatre;
import com.bookmyshow.theatre.repository.TheatreRepository;
import com.bookmyshow.theatre.validations.TheatreValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class TheatreServiceImpl implements TheatreService {

    private final TheatreRepository theatreRepository;
    private final CityRepository cityRepository;

    @Autowired
    public TheatreServiceImpl(TheatreRepository theatreRepository, CityRepository cityRepository) {
        this.theatreRepository = theatreRepository;
        this.cityRepository = cityRepository;
    }

    @Override
    @Transactional
    public OutputCommand createTheatre(InputCommand request) {

            TheatreDto theatreDto = (TheatreDto) request.getInputData();

            TheatreValidation.validateOnTheatreCreate(theatreDto);

            Optional<City> cityOpt = cityRepository.findById(theatreDto.getCityId());
            if (cityOpt.isEmpty()) {
                throw new TheatreValidationException("City not found with Id: " + theatreDto.getCityId());
            }

            Theatre theatre = new Theatre();
            theatre.setCity(cityOpt.get());
            theatre.setTheatreName(theatreDto.getName());
            theatre.setAddress(theatreDto.getAddress());
            theatre.setStatus(TheatreStatus.valueOf(theatreDto.getStatus()));

            Theatre savedTheatre = theatreRepository.save(theatre);
            return new OutputCommand(savedTheatre);

    }

    @Override
    public OutputCommand updateTheatre(InputCommand request) {
            TheatreDto theatreDto = (TheatreDto) request.getInputData();

            TheatreValidation.validateOnGetTheatre(theatreDto);

            Optional<Theatre> theatreOpt = theatreRepository.findById(theatreDto.getTheatreId());

            if (theatreOpt.isEmpty()) {
                throw new TheatreValidationException("Theatre not found with Id: " + theatreDto.getTheatreId());
            }

            Theatre theatre = theatreOpt.get();

            if (theatreDto.getCityId() != null) {
                Optional<City> cityOpt = cityRepository.findById(theatreDto.getCityId());
                if (cityOpt.isEmpty()) {
                    throw new TheatreValidationException("City not found with Id: " + theatreDto.getCityId());
                }
                theatre.setCity(cityOpt.get());
            }

            if (theatreDto.getName() != null) {
                theatre.setTheatreName(theatreDto.getName());
            }

            if (theatreDto.getAddress() != null) {
                theatre.setAddress(theatreDto.getAddress());
            }

            if(theatreDto.getStatus() != null) {
                theatre.setStatus(TheatreStatus.valueOf(theatreDto.getStatus()));
            }

            Theatre updatedTheatre = theatreRepository.save(theatre);
            return new OutputCommand("Theatre updated successfully");
    }

    @Override
    public OutputCommand deleteTheatre(InputCommand request) {

            TheatreDto theatreDto = (TheatreDto) request.getInputData();

            TheatreValidation.validateOnGetTheatre(theatreDto);

            Optional<Theatre> theatreOpt = theatreRepository.findById(theatreDto.getTheatreId());

            if (theatreOpt.isEmpty()) {
                throw new TheatreValidationException("Theatre not found with Id: " + theatreDto.getTheatreId());
            }

            theatreRepository.deleteById(theatreDto.getTheatreId());
            return new OutputCommand( "Theatre deleted successfully");
    }

    @Override
    public OutputCommand getTheatre(InputCommand request) {

            TheatreDto theatreDto = (TheatreDto) request.getInputData();

            TheatreValidation.validateOnGetTheatre(theatreDto);

            Optional<Theatre> theatreOpt = theatreRepository.findById(theatreDto.getTheatreId());

            if (theatreOpt.isEmpty()) {
                throw new TheatreValidationException("Theatre not found with Id: " + theatreDto.getTheatreId());
            }

            return new OutputCommand(theatreOpt.get());
    }
}