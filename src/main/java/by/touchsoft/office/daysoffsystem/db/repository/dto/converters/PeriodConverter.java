package by.touchsoft.office.daysoffsystem.db.repository.dto.converters;

import by.touchsoft.office.daysoffsystem.db.repository.dto.dtoEntity.PeriodDto;
import by.touchsoft.office.daysoffsystem.db.repository.entity.PeriodEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Class converts {@link PeriodEntity} into {@link PeriodDto} or vice versa to work with a view.
 */
@Component
public class PeriodConverter extends DtoEntityConverter<PeriodDto, PeriodEntity> {

    /**
     * This method converts {@link PeriodEntity} into {@link PeriodDto}.
     *
     * @param periodEntity - source {@link PeriodEntity} entity object.
     * @return converted {@link PeriodDto} object.
     */
    @Override
    public PeriodDto convertToDto(final PeriodEntity periodEntity) {
        final PeriodDto periodDto = new PeriodDto();

        periodDto.setId(periodEntity.getId());
        periodDto.setUserId(periodEntity.getUserId());
        periodDto.setStartDate(periodEntity.getStartDate().toString());
        periodDto.setEndDate(periodEntity.getEndDate().toString());
        periodDto.setPeriodType(periodEntity.getPeriodType());
        return periodDto;
    }

    /**
     * This method converts {@link PeriodDto} into {@link PeriodEntity}.
     *
     * @param periodDto - source {@link PeriodDto}.
     * @return converted {@link PeriodEntity} entity.
     */
    @Override
    public PeriodEntity convertToEntity(final PeriodDto periodDto) {
        final PeriodEntity periodEntity = new PeriodEntity();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        periodEntity.setId(periodDto.getId());
        periodEntity.setUserId(periodDto.getUserId());
        periodEntity.setStartDate(LocalDate.parse(periodDto.getStartDate(), formatter));
        periodEntity.setEndDate(LocalDate.parse(periodDto.getEndDate(), formatter));
        periodEntity.setPeriodType(periodDto.getPeriodType());
        return periodEntity;
    }

}
