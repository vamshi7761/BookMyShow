
package com.bookmyshow.showtime.service;

import com.bookmyshow.main.dto.InputCommand;
import com.bookmyshow.main.dto.OutputCommand;
import com.bookmyshow.showtime.dto.ShowSeatTypeDto;
import com.bookmyshow.showtime.exception.ShowSeatTypeValidationException;
import com.bookmyshow.showtime.model.Show;
import com.bookmyshow.showtime.model.ShowSeatType;
import com.bookmyshow.showtime.repository.ShowRepository;
import com.bookmyshow.showtime.repository.ShowSeatTypeRepository;
import com.bookmyshow.showtime.validations.ShowSeatTypeValidation;
import com.bookmyshow.theatre.model.SeatType;
import com.bookmyshow.theatre.repository.SeatTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ShowSeatTypeServiceImpl implements ShowSeatTypeService {

    private final ShowSeatTypeRepository showSeatTypeRepository;
    private final ShowRepository showRepository;
    private final SeatTypeRepository seatTypeRepository;

    @Override
    public OutputCommand createShowSeatType(InputCommand request) {
        ShowSeatTypeDto dto = (ShowSeatTypeDto) request.getInputData();
        ShowSeatTypeValidation.validateOnCreate(dto);

        Show show = showRepository.findById(dto.getShowId())
                .orElseThrow(() -> new ShowSeatTypeValidationException("Show not found with ID: " + dto.getShowId()));

        SeatType seatType = seatTypeRepository.findById(dto.getSeatTypeId())
                .orElseThrow(() -> new ShowSeatTypeValidationException("SeatType not found with ID: " + dto.getSeatTypeId()));

        // Ensure no duplicate pricing row for (show, seatType)
        boolean exists = showSeatTypeRepository.existsByShow_IdAndSeatType_Id(show.getId(), seatType.getId());
        if (exists) {
            throw new ShowSeatTypeValidationException("Price already exists for this seat type in the show");
        }

        ShowSeatType sst = new ShowSeatType();
        sst.setShow(show);
        sst.setSeatType(seatType);
        sst.setPrice(dto.getPrice());

        ShowSeatType saved = showSeatTypeRepository.save(sst);
        return OutputCommand.builder()
                .outputData(saved)
                .build();
    }

    @Override
    public OutputCommand updateShowSeatType(InputCommand request) {
        ShowSeatTypeDto dto = (ShowSeatTypeDto) request.getInputData();
        ShowSeatTypeValidation.validateOnUpdate(dto);

        ShowSeatType existing = showSeatTypeRepository.findById(dto.getShowSeatTypeId())
                .orElseThrow(() -> new ShowSeatTypeValidationException("ShowSeatType not found with ID: " + dto.getShowSeatTypeId()));

        // Allow changing showId or seatTypeId if needed, but enforce uniqueness
        if (dto.getShowId() != null || dto.getSeatTypeId() != null) {
            Long showIdToUse = (dto.getShowId() != null) ? dto.getShowId() : existing.getShow().getId();
            Long seatTypeIdToUse = (dto.getSeatTypeId() != null) ? dto.getSeatTypeId() : existing.getSeatType().getId();

            // Fetch entities if changed
            Show showToUse = existing.getShow();
            if (!Objects.equals(showToUse.getId(), showIdToUse)) {
                showToUse = showRepository.findById(showIdToUse)
                        .orElseThrow(() -> new ShowSeatTypeValidationException("Show not found with ID: " + showIdToUse));
            }

            SeatType seatTypeToUse = existing.getSeatType();
            if (!Objects.equals(seatTypeToUse.getId(), seatTypeIdToUse)) {
                seatTypeToUse = seatTypeRepository.findById(seatTypeIdToUse)
                        .orElseThrow(() -> new ShowSeatTypeValidationException("SeatType not found with ID: " + seatTypeIdToUse));
            }

            // If combination changes, ensure uniqueness (ignore current row)
            boolean duplicate = showSeatTypeRepository.existsByShow_IdAndSeatType_Id(showIdToUse, seatTypeIdToUse);
            if (duplicate && (!Objects.equals(existing.getShow().getId(), showIdToUse)
                    || !Objects.equals(existing.getSeatType().getId(), seatTypeIdToUse))) {
                throw new ShowSeatTypeValidationException("Another price already exists for the given (show, seatType)");
            }

            existing.setShow(showToUse);
            existing.setSeatType(seatTypeToUse);
        }

        if (dto.getPrice() != null) {
            if (dto.getPrice() <= 0) {
                throw new ShowSeatTypeValidationException("price must be greater than 0");
            }
            existing.setPrice(dto.getPrice());
        }

        ShowSeatType updated = showSeatTypeRepository.save(existing);
        return OutputCommand.builder()
                .outputData(updated)
                .build();
    }

    @Override
    public OutputCommand deleteShowSeatType(InputCommand request) {
        ShowSeatTypeDto dto = (ShowSeatTypeDto) request.getInputData();
        ShowSeatTypeValidation.validateOnGet(dto);

        ShowSeatType existing = showSeatTypeRepository.findById(dto.getShowSeatTypeId())
                .orElseThrow(() -> new ShowSeatTypeValidationException("ShowSeatType not found with ID: " + dto.getShowSeatTypeId()));

        showSeatTypeRepository.deleteById(existing.getId());
        return OutputCommand.builder()
                .outputData("ShowSeatType deleted successfully")
                .build();
    }

    @Override
    public OutputCommand getShowSeatType(InputCommand request) {
        ShowSeatTypeDto dto = (ShowSeatTypeDto) request.getInputData();
        ShowSeatTypeValidation.validateOnGet(dto);

        ShowSeatType existing = showSeatTypeRepository.findById(dto.getShowSeatTypeId())
                .orElseThrow(() -> new ShowSeatTypeValidationException("ShowSeatType not found with ID: " + dto.getShowSeatTypeId()));

        return OutputCommand.builder()
                .outputData(existing)
                .build();
    }

    @Override
    public OutputCommand listSeatTypesForShow(InputCommand request) {
        ShowSeatTypeDto dto = (ShowSeatTypeDto) request.getInputData();
        ShowSeatTypeValidation.validateListByShow(dto);

        // Validate show existence (optional but recommended)
        showRepository.findById(dto.getShowId())
                .orElseThrow(() -> new ShowSeatTypeValidationException("Show not found with ID: " + dto.getShowId()));

        List<ShowSeatType> list = showSeatTypeRepository.findByShow_Id(dto.getShowId());
        return OutputCommand.builder()
                .outputData(list)
                .build();
    }
}
