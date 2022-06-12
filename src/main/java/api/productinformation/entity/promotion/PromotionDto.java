package api.productinformation.entity.promotion;

import api.productinformation.entity.ItemPromotion;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class PromotionDto {
    private Long id;
    private String promotionName;
    private Integer discountAmount;
    private Double discountRate;
    private LocalDate startDate;
    private LocalDate endDate;

    public PromotionDto(Long id, String promotionName, Integer discountAmount, Double discountRate, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.promotionName = promotionName;
        this.discountAmount = discountAmount;
        this.discountRate = discountRate;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public PromotionDto(Promotion promotion){
        this.id = promotion.getId();
        this.promotionName = promotion.getPromotionName();
        this.discountAmount = promotion.getDiscountAmount();
        this.discountRate = promotion.getDiscountRate();
        this.startDate = promotion.getStartDate();
        this.endDate = promotion.getEndDate();
    }
}
