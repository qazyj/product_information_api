package api.productinformation.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Item extends DateEntity{g
    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "promotion_id")
    private Promotion promotion;

    private String itemName;

    @Enumerated(EnumType.STRING)
    private Type itemType;

    private Long itemPrice;

}
