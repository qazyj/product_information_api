package api.productinformation.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item extends DateEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    public static Item createItem(String itemName, String itemType, Long itemPrice, UserItem userItem,
                                  LocalDate startDate, LocalDate endDate){
        Item item = new Item();
        item.itemName = itemName;
        if(itemType.equals("일반"))
            item.itemType = Type.NORMAL;
        else
            item.itemType = Type.CORPORATE;
        item.itemPrice = itemPrice;
        userItem.addItems(item);
        item.setStartDate(startDate);
        item.setEndDate(endDate);
        return item;
    }
}
