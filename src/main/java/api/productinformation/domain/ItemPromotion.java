package api.productinformation.domain;

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
@NoArgsConstructor
public class ItemPromotion extends DateEntity {
    @Id
    @GeneratedValue
    @Column(name = "item_promotion_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "promotion_id")
    private Promotion promotion;

    private Long salePrice;

    //==생성자로 값 주입==//
    public ItemPromotion(Item item, Promotion promotion){
        // Item 연관 관계 매핑
        this.item = item;
        item.getItemPromotions().add(this);
        // Promotion 연관 관계 매핑
        this.promotion = promotion;
        promotion.getItemPromotions().add(this);

        this.salePrice = setSalePrice(item, promotion);
        setStartDate(promotion.getStartDate());
        setEndDate(promotion.getEndDate());
    }

    public Long setSalePrice(Item item, Promotion promotion) {
        if(promotion.getDiscountAmount() != null)
            return item.getItemPrice() - promotion.getDiscountAmount();
        else
            return item.getItemPrice() - (long)(item.getItemPrice()*promotion.getDiscountRate());
    }
}
