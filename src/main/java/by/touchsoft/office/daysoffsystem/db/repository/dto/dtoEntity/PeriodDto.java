package by.touchsoft.office.daysoffsystem.db.repository.dto.dtoEntity;

import by.touchsoft.office.daysoffsystem.db.repository.entity.PeriodEntity;
import by.touchsoft.office.daysoffsystem.enumerations.PeriodType;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * This class is used to interact with view and hides unwanted data of {@link PeriodEntity} entity.
 */
public class PeriodDto {

    private int id;
    private int userId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private String startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private String endDate;
    private PeriodType periodType;

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(final int userId) {
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
