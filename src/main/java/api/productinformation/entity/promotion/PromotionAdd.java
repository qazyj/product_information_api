package api.productinformation.entity.promotion;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PromotionAdd {
    private String promotionName;
    private Integer discountAmount;
    private Double discountRate;
    private LocalDate startDate;
    private LocalDate endDate;

    public PromotionAdd(String promotionName, Integer discountAmount, Double discountRate, LocalDate startDate, LocalDate endDate) {
        this.promotionName = promotionName;
        this.discountAmount = discountAmount;
        this.discountRate = discountRate;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
