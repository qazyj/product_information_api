package api.productinformation.entity.item;

import api.productinformation.entity.ItemPromotion;
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
    private LocalDate itemStartDate;
    private LocalDate itemEndDate;
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
        this.itemStartDate = item.getStartDate();
        this.itemEndDate = item.getEndDate();
        if(promotion!=null) {
            this.salePrice = setSalePrice(item, promotion);
            this.promotionDto = new PromotionDto(promotion);
        } else {
            this.salePrice = this.itemPrice;
        }
    }

    public Long setSalePrice(Item item, Promotion promotion) {
        if(promotion.getDiscountAmount() != null)
            return item.getItemPrice() - promotion.getDiscountAmount();
        else
            return item.getItemPrice() - (long)(item.getItemPrice()*promotion.getDiscountRate());
    }
}
