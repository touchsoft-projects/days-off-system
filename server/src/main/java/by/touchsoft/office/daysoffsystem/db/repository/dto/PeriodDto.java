package by.touchsoft.office.daysoffsystem.db.repository.dto;

import by.touchsoft.office.daysoffsystem.db.repository.entity.PeriodEntity;
import by.touchsoft.office.daysoffsystem.enumerations.PeriodType;

/**
 * This class is used to interact with view and hides unwanted data of {@link PeriodEntity} entity.
 */
public class PeriodDto {

    private String id;
    private String userId;
    private String startDate;
    private String endDate;
    private PeriodType periodType;

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(final String userId) {
        this.userId = userId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(final String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(final String endDate) {
        this.endDate = endDate;
    }

    public PeriodType getPeriodType() {
        return periodType;
    }

    public void setPeriodType(final PeriodType periodType) {
        this.periodType = periodType;
    }
}
