package api.productinformation.dto.item;

import api.productinformation.entity.Item;
import api.productinformation.entity.Promotion;
import api.productinformation.dto.promotion.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemPromotionDto {
    private Long itemId;
    private String itemName;
    private String itemType;
    private Long itemPrice;
    private Long salePrice;
    private int stockQuantity;
    private LocalDate itemStartDate;
    private LocalDate itemEndDate;
    private PromotionDto promotionDto;

    public static ItemPromotionDto from(Item item, Promotion promotion) {
        return ItemPromotionDto.builder()
                .itemId(item.getId())
                .itemName(item.getItemName())
                .itemType(item.getItemType().getValue())
                .itemPrice(item.getItemPrice())
                .stockQuantity(item.getStockQuantity())
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