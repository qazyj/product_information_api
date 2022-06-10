package api.productinformation.entity.promotion;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class PromotionAdd {
    private String promotionName;
    private Integer discountAmount;
    private Double discountRate;
    private LocalDate startDate;
    private LocalDate endDate;

    public PromotionAdd(String promotionName, Integer discountAmount, Double discountRate, String startDate, String endDate) {
        this.promotionName = promotionName;
        this.discountAmount = discountAmount;
        this.discountRate = discountRate;
        this.startDate = LocalDate.parse(startDate, DateTimeFormatter.ofPattern("yyyy.M.d"));
        this.endDate = LocalDate.parse(endDate, DateTimeFormatter.ofPattern("yyyy.M.d"));
    }
}
