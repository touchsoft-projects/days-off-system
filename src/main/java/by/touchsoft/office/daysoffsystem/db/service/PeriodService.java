package by.touchsoft.office.daysoffsystem.db.service;

import by.touchsoft.office.daysoffsystem.db.repository.PeriodRepository;
import by.touchsoft.office.daysoffsystem.db.repository.dto.converters.PeriodConverter;
import by.touchsoft.office.daysoffsystem.db.repository.dto.dtoEntity.PeriodDto;
import by.touchsoft.office.daysoffsystem.db.repository.entity.PeriodEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * This class interacts with database and controls a {@link PeriodEntity} entity by {@link PeriodRepository} interface.
 */
@Service
@Transactional
public class PeriodService {

    private final PeriodRepository periodRepository;
    private final PeriodConverter periodConverter;

    public PeriodService(final PeriodRepository periodRepository, final PeriodConverter periodConverter) {
        this.periodRepository = periodRepository;
        this.periodConverter = periodConverter;
    }

    public List<PeriodDto> getAll() {
        return periodConverter.convertToDto(periodRepository.findAll());
    }

    public List<PeriodDto> getByUserId(final int id) {
        return periodConverter.convertToDto(periodRepository.findAllByUserId(id));
    }

    public void deleteById(final int id) {
        periodRepository.deleteById(id);
    }

    public void save(final PeriodDto periodDto) {
        periodRepository.save(periodConverter.convertToEntity(periodDto));
    }

    public void deleteAllByUserId(final int id) {
        periodRepository.deleteAllByUserId(id);
    }
}
