package api.productinformation.entity;

import api.productinformation.entity.item.Item;
import api.productinformation.entity.promotion.Promotion;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Item Entity
 * Promotion Entity
 * N:M 매핑 => 1:N & N:1 매핑
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemPromotion extends DateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_promotion_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "promotion_id")
    private Promotion promotion;

    private Long salePrice;

    //==생성 메서드==//
    public static ItemPromotion createItemPromotion(Item item, Promotion promotion){
        ItemPromotion itemPromotion = new ItemPromotion();
        // Item 연관 관계 매핑
        itemPromotion.item = item;
        item.getItemPromotions().add(itemPromotion);
        // Promotion 연관 관계 매핑
        itemPromotion.promotion = promotion;
        promotion.getItemPromotions().add(itemPromotion);

        itemPromotion.salePrice = itemPromotion.setSalePrice(item, promotion);
        itemPromotion.setStartDate(promotion.getStartDate());
        itemPromotion.setEndDate(promotion.getEndDate());
        return itemPromotion;
    }

    public Long setSalePrice(Item item, Promotion promotion) {
        if(promotion.getDiscountAmount() != null)
            return item.getItemPrice() - promotion.getDiscountAmount();
        else
            return item.getItemPrice() - (long)(item.getItemPrice()*promotion.getDiscountRate());
    }
}
