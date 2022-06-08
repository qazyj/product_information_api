package api.productinformation.entity.item;

import api.productinformation.entity.Type;
import api.productinformation.entity.promotion.Promotion;
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
                            LocalDate itemStartDate, LocalDate itemEndDate, Promotion promotion) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemType = itemType;
        this.itemPrice = itemPrice;
        this.salePrice = salePrice;
        this.itemStartDate = itemStartDate;
        this.itemEndDate = itemEndDate;
        this.promotionDto = new PromotionDto(promotion);
    }

    public ItemPromotionDto(Item item, Promotion promotion){
        this.itemId = item.getId();
        this.itemName = item.getItemName();
        this.itemType = item.getItemType();
        this.itemPrice = item.getItemPrice();
        this.itemStartDate = item.getStartDate();
        this.itemEndDate = item.getEndDate();
        this.promotionDto = new PromotionDto(promotion);
    }
}
