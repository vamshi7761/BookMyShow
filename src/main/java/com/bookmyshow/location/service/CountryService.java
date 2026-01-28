package com.bookmyshow.location.service;

import com.bookmyshow.location.model.Country;
import com.bookmyshow.location.repository.CountryRepository;
import com.bookmyshow.main.dto.InputCommand;
import com.bookmyshow.main.dto.OutputCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CountryService {


    private final CountryRepository countryRepository;

    @Autowired
    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public OutputCommand createCountry(InputCommand inputCommand) {
        Country country = (Country) inputCommand.getInputData();
        country = countryRepository.save(country);
        return OutputCommand.builder().outputData(country).build();
    }

    public OutputCommand getCountryById(InputCommand inputCommand) {
        Long id = (Long) inputCommand.getInputData();
        Country country = countryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Country not found with id: " + id));
        return OutputCommand.builder().outputData(country).build();
    }


    public OutputCommand getAllCountries(InputCommand inputCommand) {
        List<Country> countries = countryRepository.findAll();
        return OutputCommand.builder().outputData(countries).build();
    }

    public OutputCommand updateCountry(InputCommand inputCommand) {
        Country updatedCountry = (Country) inputCommand.getInputData();
        Country country = countryRepository.findById(updatedCountry.getId())
                .map(existingCountry -> {
                    if(updatedCountry.getCountryCode() != null)
                    {
                        existingCountry.setCountryCode(updatedCountry.getCountryCode());
                    }
                    if(updatedCountry.getCountryName() != null)
                    {
                        existingCountry.setCountryName(updatedCountry.getCountryName());
                    }
                    return countryRepository.save(existingCountry);
                })
                .orElseThrow(() -> new RuntimeException("Country not found with id: " + updatedCountry.getId()));
        return OutputCommand.builder().outputData(country).build();
    }

    public OutputCommand deleteCountry(InputCommand inputCommand) {
        Long id = (Long) inputCommand.getInputData();
        countryRepository.deleteById(id);
        return OutputCommand.builder().outputData("Country deleted successfully").build();
    }
}