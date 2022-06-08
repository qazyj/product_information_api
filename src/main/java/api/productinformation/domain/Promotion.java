package api.productinformation.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Promotion extends DateEntity{
    @Id
    @GeneratedValue
    @Column(name = "promotion_id")
    private Long id;

    @OneToMany(mappedBy = "promotion")
    private List<ItemPromotion> itemPromotions = new ArrayList<>();

    private String promotionName;

    private Integer discountAmount;

    private Double discountRate;

    //==생성 메서드==//
    public static Promotion createPromotion(String promotionName, Integer discountAmount, Double discountRate){
        Promotion promotion = new Promotion();
        promotion.promotionName = promotionName;
        if(discountAmount == null)
            promotion.discountRate = discountRate;
        else
            promotion.discountAmount = discountAmount;
        return promotion;
    }
}
