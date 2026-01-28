package com.bookmyshow.location.service;

import com.bookmyshow.location.model.State;
import com.bookmyshow.location.repository.StateRepository;
import com.bookmyshow.main.dto.InputCommand;
import com.bookmyshow.main.dto.OutputCommand;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StateService {

    private final StateRepository stateRepository;

    public StateService(StateRepository stateRepository) {
        this.stateRepository = stateRepository;
    }

    @Transactional
    public OutputCommand createState(InputCommand inputCommand) {
        State state = (State) inputCommand.getInputData();
        state = stateRepository.save(state);
        return OutputCommand.builder().outputData(state).build();
    }

    public OutputCommand getStateById(InputCommand inputCommand) {
        Long id = (Long) inputCommand.getInputData();
        State state = stateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("State not found with id: " + id));
        return OutputCommand.builder().outputData(state).build();
    }

    public OutputCommand getAllStates(InputCommand inputCommand) {
        List<State> states = stateRepository.findAll();
        return OutputCommand.builder().outputData(states).build();
    }

    public OutputCommand updateState(InputCommand inputCommand) {
        State updatedState = (State) inputCommand.getInputData();

        if (updatedState.getId() == null) {
            throw new IllegalArgumentException("State ID is mandatory for update operation");
        }

        State state = stateRepository.findById(updatedState.getId())
                .map(existingState -> {
                    if (updatedState.getCountry() != null) {
                        existingState.setCountry(updatedState.getCountry());
                    }
                    if (updatedState.getStateName() != null) {
                        existingState.setStateName(updatedState.getStateName());
                    }
                    if (updatedState.getStateCode() != null) {
                        existingState.setStateCode(updatedState.getStateCode());
                    }
                    return stateRepository.save(existingState);
                })
                .orElseThrow(() -> new RuntimeException("State not found with id: " + updatedState.getId()));

        return OutputCommand.builder().outputData(state).build();
    }

    public OutputCommand deleteState(InputCommand inputCommand) {
        Long id = (Long) inputCommand.getInputData();
        stateRepository.deleteById(id);
        return OutputCommand.builder().outputData("State deleted successfully").build();
    }
}