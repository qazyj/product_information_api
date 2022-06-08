package api.productinformation.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SequenceGenerator(
        name = "PROMOTION_SEQ_GENERATOR",
        sequenceName = "PROMOTION_SEQ", // 매핑할 데이터베이스 시퀀스 이름
        initialValue = 1,
        allocationSize = 1)
public class Promotion extends DateEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "PROMOTION_SEQ_GENERATOR")
    @Column(name = "promotion_id")
    private Long id;

    @OneToMany(mappedBy = "promotion")
    private List<ItemPromotion> itemPromotions = new ArrayList<>();

    private String promotionName;

    private Integer discountAmount;

    private Double discountRate;

    //==생성 메서드==//
    public static Promotion createPromotion(String promotionName, Integer discountAmount, Double discountRate,
                                            LocalDate startDate, LocalDate endDate){
        Promotion promotion = new Promotion();
        promotion.promotionName = promotionName;
        if(discountAmount == null)
            promotion.discountRate = discountRate;
        else
            promotion.discountAmount = discountAmount;
        promotion.setStartDate(startDate);
        promotion.setEndDate(endDate);
        return promotion;
    }
}
