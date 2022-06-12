package api.productinformation.dto.promotion;

import api.productinformation.entity.Promotion;
import lombok.*;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PromotionDto {
    private Long id;
    private String promotionName;
    private Integer discountAmount;
    private Double discountRate;
    private LocalDate startDate;
    private LocalDate endDate;

    public static PromotionDto from (Promotion promotion) {
        if(Objects.isNull(promotion)) return null;
        return PromotionDto.builder()
                .id(promotion.getId())
                .promotionName(promotion.getPromotionName())
                .discountAmount(promotion.getDiscountAmount())
                .discountAmount(promotion.getDiscountAmount())
                .startDate(promotion.getStartDate())
                .endDate(promotion.getEndDate())
                .build();
    }
}