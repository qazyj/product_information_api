package api.productinformation.dto.promotion;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor
public class NewPromotion {
    private String promotionName;
    private Integer discountAmount;
    private Double discountRate;
    private String startDate;
    private String endDate;

    @DateTimeFormat(pattern = "yyyy.M.d")
    private LocalDate startDateLocalType;
    @DateTimeFormat(pattern = "yyyy.M.d")
    private LocalDate endDateLocalType;

    public NewPromotion(String promotionName, Integer discountAmount, Double discountRate, String startDate, String endDate) {
        this.promotionName = promotionName;
        this.discountAmount = discountAmount;
        this.discountRate = discountRate;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void StringToLocalDate() {
        this.startDateLocalType = LocalDate.parse(startDate, DateTimeFormatter.ofPattern("yyyy.M.d"));
        this.endDateLocalType = LocalDate.parse(endDate, DateTimeFormatter.ofPattern("yyyy.M.d"));
    }
}
