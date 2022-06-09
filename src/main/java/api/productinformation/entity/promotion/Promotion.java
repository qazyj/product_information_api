package api.productinformation.entity.promotion;

import api.productinformation.entity.DateEntity;
import api.productinformation.entity.ItemPromotion;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.tomcat.jni.Local;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Promotion extends DateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "promotion_id")
    private Long id;

    @OneToMany(mappedBy = "promotion")
    private List<ItemPromotion> itemPromotions = new ArrayList<>();

    private String promotionName;

    private Integer discountAmount;

    private Double discountRate;

    //==생성 메서드==//
    public static Promotion createPromotion(String promotionName, Integer discountAmount, Double discountRate,
                                            String startDate, String endDate){
        Promotion promotion = new Promotion();
        promotion.promotionName = promotionName;
        if(discountAmount == null)
            promotion.discountRate = discountRate;
        else
            promotion.discountAmount = discountAmount;
        promotion.setStartDate(LocalDate.parse(startDate, DateTimeFormatter.ofPattern("yyyy.MM.dd")));
        promotion.setEndDate(LocalDate.parse(endDate, DateTimeFormatter.ofPattern("yyyy.MM.dd")));
        return promotion;
    }

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
