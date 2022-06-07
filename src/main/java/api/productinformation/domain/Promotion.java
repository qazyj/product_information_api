package api.productinformation.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.*;

@Entity
@Getter
public class Promotion extends DateEntity{
    @Id
    @GeneratedValue
    @Column(name = "promotion_id")
    private Long id;

    @OneToMany(mappedBy = "promotion")
    private List<Item> items = new ArrayList<>();

    private String promotionName;

    private Integer discountAmount;

    private Double discountRate;
}
