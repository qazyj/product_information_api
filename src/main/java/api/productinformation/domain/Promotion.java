package api.productinformation.domain;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
public class Promotion extends DateEntity{
    @Id
    @GeneratedValue
    @Column(name = "promotion_id")
    private Long id;

    private String promotionName;

    private Integer discountAmount;

    private Double discountRate;
}
