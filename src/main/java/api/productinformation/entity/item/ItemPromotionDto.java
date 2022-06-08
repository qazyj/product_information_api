package api.productinformation.entity.item;

import api.productinformation.entity.Type;
import api.productinformation.entity.promotion.PromotionDto;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ItemPromotionDto {
    private Long itemId;
    private String itemName;
    private Type itemType;
    private Long itemPrice;
    private Long salePrice;
    private LocalDate itemStartDate;
    private LocalDate itemEndDate;
    private PromotionDto promotionDto;

    public ItemPromotionDto(Long itemId, String itemName, Type itemType, Long itemPrice, Long salePrice,
                            LocalDate itemStartDate, LocalDate itemEndDate, PromotionDto promotionDto) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemType = itemType;
        this.itemPrice = itemPrice;
        this.salePrice = salePrice;
        this.itemStartDate = itemStartDate;
        this.itemEndDate = itemEndDate;
        this.promotionDto = promotionDto;
    }
}
