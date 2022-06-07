package api.productinformation.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Item extends DateEntity{
    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "promotion_id")
    private Promotion promotion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_item_id")
    private UserItem userItem;

    private String itemName;

    @Enumerated(EnumType.STRING)
    private Type itemType;

    private Long itemPrice;

    public void setUserItem(UserItem userItem){
        this.userItem = userItem;
    }

    public Item(Promotion promotion, String itemName, Type itemType, Long itemPrice) {
        this.promotion = promotion;
        this.itemName = itemName;
        this.itemType = itemType;
        this.itemPrice = itemPrice;
        this.userItem.addItems(this);
    }
}
