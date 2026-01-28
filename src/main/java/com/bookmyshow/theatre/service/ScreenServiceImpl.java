package com.bookmyshow.theatre.service;

import com.bookmyshow.main.dto.InputCommand;
import com.bookmyshow.main.dto.OutputCommand;
import com.bookmyshow.theatre.dto.ScreenDto;
import com.bookmyshow.theatre.enums.Feature;
import com.bookmyshow.theatre.enums.TheatreStatus;
import com.bookmyshow.theatre.exception.ScreenValidationException;
import com.bookmyshow.theatre.model.Screen;
import com.bookmyshow.theatre.model.Theatre;
import com.bookmyshow.theatre.repository.ScreenRepository;
import com.bookmyshow.theatre.repository.TheatreRepository;
import com.bookmyshow.theatre.service.ScreenService;
import com.bookmyshow.theatre.validations.ScreenValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScreenServiceImpl implements ScreenService {

    private final ScreenRepository screenRepository;
    private final TheatreRepository theatreRepository;

    @Override
    public OutputCommand createScreen(InputCommand request) {


            ScreenDto screenDto = (ScreenDto) request.getInputData();

            ScreenValidation.validateScreenOnCreate(screenDto);

            Screen screen = new Screen();

            Long theatreId = screenDto.getTheatreId();

            Theatre theatre = theatreRepository.findById(theatreId)
                    .orElseThrow(() -> new ScreenValidationException("Theatre not found with ID: " + theatreId));

            screen.setTheatre(theatre);
            screen.setName(screenDto.getName());

            List<Feature> featureList = new ArrayList<>();

            for(String featureStr : screenDto.getFeatures()) {
                try {
                    Feature feature = Feature.valueOf(featureStr.toUpperCase());
                    featureList.add(feature);
                } catch (IllegalArgumentException e) {
                    throw new ScreenValidationException("Invalid feature: " + featureStr);
                }
            }
            screen.setFeatures(featureList);
            screen.setStatus(TheatreStatus.valueOf(screenDto.getStatus()));

            Screen savedScreen = screenRepository.save(screen);
            return OutputCommand.builder()
                    .outputData(savedScreen)
                    .build();
    }

    @Override
    public OutputCommand updateScreen(InputCommand request) {
            ScreenDto screenDto = (ScreenDto) request.getInputData();

            ScreenValidation.validateScreenOnGet(screenDto);

            Screen existingScreen = screenRepository.findById(screenDto.getScreenId())
                    .orElseThrow(() -> new ScreenValidationException("Screen not found with ID: " + screenDto.getScreenId()));

            if (screenDto.getTheatreId() != null) {
                Theatre theatre = theatreRepository.findById(screenDto.getTheatreId())
                        .orElseThrow(() -> new ScreenValidationException("Theatre not found with ID: " + screenDto.getTheatreId()));
                existingScreen.setTheatre(theatre);
            }
            if (screenDto.getName() != null && !screenDto.getName().trim().isEmpty()) {
                existingScreen.setName(screenDto.getName());
            }

            Screen updatedScreen = screenRepository.save(existingScreen);
            return OutputCommand.builder()
                    .outputData(updatedScreen)
                    .build();
    }

    @Override
    public OutputCommand deleteScreen(InputCommand request) {

            ScreenDto screenDto = (ScreenDto) request.getInputData();

            ScreenValidation.validateScreenOnGet(screenDto);

            Screen existingScreen = screenRepository.findById(screenDto.getScreenId())
                    .orElseThrow(() -> new ScreenValidationException("Screen not found with ID: " + screenDto.getScreenId()));


            screenRepository.deleteById(((ScreenDto) request.getInputData()).getScreenId());
            return OutputCommand.builder()
                    .outputData("Screen deleted successfully")
                    .build();
    }

    @Override
    public OutputCommand getScreen(InputCommand request) {

            ScreenDto screenDto = (ScreenDto) request.getInputData();

            ScreenValidation.validateScreenOnGet(screenDto);

            Screen existingScreen = screenRepository.findById(screenDto.getScreenId())
                    .orElseThrow(() -> new ScreenValidationException("Screen not found with ID: " + screenDto.getScreenId()));

            return OutputCommand.builder()
                    .outputData(existingScreen)
                    .build();

    }
}