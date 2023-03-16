package by.touchsoft.office.daysoffsystem.db.service;

import by.touchsoft.office.daysoffsystem.db.repository.PeriodRepository;
import by.touchsoft.office.daysoffsystem.db.repository.UserRepository;
import by.touchsoft.office.daysoffsystem.db.repository.dto.PeriodDto;
import by.touchsoft.office.daysoffsystem.db.repository.entity.PeriodEntity;
import by.touchsoft.office.daysoffsystem.db.repository.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * This class interacts with database and controls a {@link PeriodEntity} entity
 * by {@link PeriodRepository} interface.
 */
@Service
@Transactional
public class PeriodService {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PeriodRepository periodRepository;

    public PeriodService() {
    }

    public String addAnyPeriod(PeriodDto periodDto) {
        PeriodEntity periodEntity = new PeriodEntity();
        copyToEntity(periodDto, periodEntity);
        periodEntity = periodRepository.save(periodEntity);
        return periodEntity.getId();
    }

    public String addPeriodByEmail(PeriodDto periodDto, String email) {
        UserEntity userEntity = userRepository.findByEmail(email);
        if (userEntity != null) {
            String userId = userEntity.getId();
            if (Objects.equals(userId, periodDto.getUserId())) {
                PeriodEntity periodEntity = new PeriodEntity();
                copyToEntity(periodDto, periodEntity);
                periodEntity = periodRepository.save(periodEntity);
                return periodEntity.getId();
            }
        }
        return null;
    }

    public List<PeriodDto> getAll() {
        List<PeriodEntity> periodEntities = periodRepository.findAll();
        return toPeriodDtos(periodEntities);
    }

    public List<PeriodDto> getByUserId(final String id) {
        List<PeriodEntity> periodEntities = periodRepository.findAllByUserId(id);
        return toPeriodDtos(periodEntities);
    }

    public boolean updateAnyPeriod(PeriodDto periodDto) {
        Optional<PeriodEntity> optional = periodRepository.findById(periodDto.getId());
        if (optional.isPresent()) {
            PeriodEntity periodEntity = optional.get();
            copyToEntity(periodDto, periodEntity);
            periodRepository.save(periodEntity);
            return true;
        }
        return false;
    }

    public boolean updatePeriodByEmail(PeriodDto periodDto, String email) {
        Optional<PeriodEntity> optional = periodRepository.findById(periodDto.getId());
        if (optional.isPresent()) {
            UserEntity userEntity = userRepository.findByEmail(email);
            if (userEntity != null) {
                PeriodEntity periodEntity = optional.get();
                if (Objects.equals(periodEntity.getUserId(), periodDto.getUserId())
                        && Objects.equals(userEntity.getId(), periodDto.getUserId())) {
                    copyToEntity(periodDto, periodEntity);
                    return true;
                }
            }
        }
        return true;
    }

    public void deleteById(final String id) {
        periodRepository.deleteById(id);
    }

    private void copyToEntity(
            final PeriodDto periodDto,
            final PeriodEntity periodEntity
    ) {
        periodEntity.setStartDate(LocalDate.parse(periodDto.getStartDate(), FORMATTER));
        periodEntity.setEndDate(LocalDate.parse(periodDto.getEndDate(), FORMATTER));
        periodEntity.setPeriodType(periodDto.getPeriodType());
        periodEntity.setUserId(periodDto.getUserId());
    }

    private PeriodDto toPeriodDto(
            final PeriodEntity periodEntity
    ) {
        PeriodDto periodDto = new PeriodDto();
        periodDto.setId(periodEntity.getId());
        periodDto.setStartDate(periodEntity.getStartDate().toString());
        periodDto.setEndDate(periodEntity.getEndDate().toString());
        periodDto.setPeriodType(periodEntity.getPeriodType());
        periodDto.setUserId(periodEntity.getUserId());

        return periodDto;
    }

    private List<PeriodDto> toPeriodDtos(List<PeriodEntity> periodEntities) {
        List<PeriodDto> periodDtos = Collections.emptyList();
        if (!periodEntities.isEmpty()) {
            periodDtos = new ArrayList<>(periodEntities.size());
            for (PeriodEntity periodEntity : periodEntities) {
                periodDtos.add(toPeriodDto(periodEntity));
            }
        }
        return periodDtos;
    }
}
