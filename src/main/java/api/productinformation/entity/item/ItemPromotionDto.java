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
    private String itemType;
    private Long itemPrice;
    private Long salePrice;
    private PromotionDto promotionDto;

    public ItemPromotionDto(Long itemId, String itemName, Type itemType, Long itemPrice, Long salePrice, Promotion promotion) {
        this.itemId = itemId;
        this.itemName = itemName;
        if(itemType.equals(Type.NORMAL))
            this.itemType = "일반";
        else
            this.itemType = "기업회원상품";
        this.itemPrice = itemPrice;
        this.salePrice = salePrice;
        this.promotionDto = new PromotionDto(promotion);
    }

    public ItemPromotionDto(Item item, Promotion promotion){
        this.itemId = item.getId();
        this.itemName = item.getItemName();
        if(item.getItemType().equals(Type.NORMAL))
            this.itemType = "일반";
        else
            this.itemType = "기업회원상품";
        this.itemPrice = item.getItemPrice();
        this.promotionDto = new PromotionDto(promotion);
    }
}
