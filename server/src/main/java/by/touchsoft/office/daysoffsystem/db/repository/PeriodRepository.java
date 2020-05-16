package by.touchsoft.office.daysoffsystem.db.repository;

import by.touchsoft.office.daysoffsystem.db.repository.entity.PeriodEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PeriodRepository extends JpaRepository<PeriodEntity, String> {
    List<PeriodEntity> findAllByUserId(String id);
}