package com.bookmyshow.location.service;

import com.bookmyshow.location.model.City;
import com.bookmyshow.location.repository.CityRepository;
import com.bookmyshow.main.dto.InputCommand;
import com.bookmyshow.main.dto.OutputCommand;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CityService {

    private final CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Transactional
    public OutputCommand createCity(InputCommand inputCommand) {
        City city = (City) inputCommand.getInputData();
        city = cityRepository.save(city);
        return OutputCommand.builder().outputData(city).build();
    }

    public OutputCommand getCityById(InputCommand inputCommand) {
        Long id = (Long) inputCommand.getInputData();
        City city = cityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("City not found with id: " + id));
        return OutputCommand.builder().outputData(city).build();
    }

    public OutputCommand getAllCities(InputCommand inputCommand) {
        List<City> cities = cityRepository.findAll();
        return OutputCommand.builder().outputData(cities).build();
    }

    public OutputCommand updateCity(InputCommand inputCommand) {
        City updatedCity = (City) inputCommand.getInputData();

        if (updatedCity.getId() == null) {
            throw new IllegalArgumentException("City ID is mandatory for update operation");
        }

        City city = cityRepository.findById(updatedCity.getId())
                .map(existingCity -> {
                    if (updatedCity.getState() != null) {
                        existingCity.setState(updatedCity.getState());
                    }
                    if (updatedCity.getName() != null) {
                        existingCity.setName(updatedCity.getName());
                    }
                    if (updatedCity.getCityCode() != null) {
                        existingCity.setCityCode(updatedCity.getCityCode());
                    }
                    return cityRepository.save(existingCity);
                })
                .orElseThrow(() -> new RuntimeException("City not found with id: " + updatedCity.getId()));

        return OutputCommand.builder().outputData(city).build();
    }

    public OutputCommand deleteCity(InputCommand inputCommand) {
        Long id = (Long) inputCommand.getInputData();
        cityRepository.deleteById(id);
        return OutputCommand.builder().outputData("City deleted successfully").build();
    }



}