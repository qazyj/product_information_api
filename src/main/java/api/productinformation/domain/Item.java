package api.productinformation.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Item extends DateEntity{
    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    @OneToMany(mappedBy = "item")
    private List<ItemPromotion> itemPromotions = new ArrayList<>();

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
}
