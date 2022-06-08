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

    //==생성 메서드==//
    public static Item createItem(String itemName, String itemType, Long itemPrice, UserItem userItem){
        Item item = new Item();
        item.itemName = itemName;
        if(itemType.equals("일반"))
            item.itemType = Type.NORMAL;
        else
            item.itemType = Type.CORPORATE;
        item.itemPrice = itemPrice;
        userItem.addItems(item);
        return item;
    }
}
