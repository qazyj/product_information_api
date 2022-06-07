package api.productinformation.domain;

import lombok.Getter;

import javax.persistence.*;

/**
 * Item Entity
 * Promotion Entity
 * N:M 매핑 => 1:N & N:1 매핑
 */
@Entity
@Getter
public class ItemPromotion {
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
}
