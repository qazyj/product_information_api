package api.productinformation.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item extends DateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<ItemPromotion> itemPromotions = new ArrayList<>();

    private String itemName;

    @Enumerated(EnumType.STRING)
    private ItemType itemType;

    private Long itemPrice;

    //==생성 메서드==//
    public static Item createItem(String itemName, ItemType itemType, Long itemPrice,
                                  LocalDate startDate, LocalDate endDate){
        Item item = new Item();
        item.itemName = itemName;
        item.itemType = itemType;
        item.itemPrice = itemPrice;
        item.setStartDate(startDate);
        item.setEndDate(endDate);
        return item;
    }
}
