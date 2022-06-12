package api.productinformation.dto.item;

import api.productinformation.entity.Item;
import api.productinformation.entity.Type;
import api.productinformation.entity.Promotion;
import api.productinformation.dto.promotion.PromotionDto;
import lombok.*;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemPromotionDto {
    private Long itemId;
    private String itemName;
    private Type itemType;
    private Long itemPrice;
    private Long salePrice;
    private LocalDate itemStartDate;
    private LocalDate itemEndDate;
    private PromotionDto promotionDto;

    public static ItemPromotionDto from(Item item, Promotion promotion) {
        return ItemPromotionDto.builder()
                .itemId(item.getId())
                .itemName(item.getItemName())
                .itemType(item.getItemType())
                .itemPrice(item.getItemPrice())
                .itemStartDate(item.getStartDate())
                .itemEndDate(item.getEndDate())
                .promotionDto(PromotionDto.from(promotion))
                .build();
    }

    public Long getSalePrice() {
        if(promotionDto == null){
            return itemPrice;
        } else if(promotionDto.getDiscountAmount() != null){
            return itemPrice - promotionDto.getDiscountAmount();
        } else {
            return itemPrice - (long)(itemPrice*promotionDto.getDiscountRate());
        }
    }
}