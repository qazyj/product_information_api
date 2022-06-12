package api.productinformation.entity;

import javax.persistence.MappedSuperclass;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@MappedSuperclass
public class DateEntity {
    private LocalDate startDate;
    private LocalDate endDate;

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = LocalDate.parse(startDate, DateTimeFormatter.ofPattern("yyyy.M.d"));
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = LocalDate.parse(endDate, DateTimeFormatter.ofPattern("yyyy.M.d"));
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
