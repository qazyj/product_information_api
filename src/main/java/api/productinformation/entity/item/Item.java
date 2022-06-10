package api.productinformation.entity.item;

import api.productinformation.entity.DateEntity;
import api.productinformation.entity.ItemPromotion;
import api.productinformation.entity.Type;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

    @OneToMany(mappedBy = "item")
    private List<ItemPromotion> itemPromotions = new ArrayList<>();

    private String itemName;

    @Enumerated(EnumType.STRING)
    private Type itemType;

    private Long itemPrice;

    //==생성 메서드==//
    public static Item createItem(String itemName, String itemType, Long itemPrice,
                                  String startDate, String endDate){
        Item item = new Item();
        item.itemName = itemName;
        if(itemType.equals("일반"))
            item.itemType = Type.NORMAL;
        else
            item.itemType = Type.CORPORATE;
        item.itemPrice = itemPrice;
        item.setStartDate(LocalDate.parse(startDate, DateTimeFormatter.ofPattern("yyyy.M.d")));
        item.setEndDate(LocalDate.parse(endDate, DateTimeFormatter.ofPattern("yyyy.M.d")));
        return item;
    }

    public static Item createItem(String itemName, String itemType, Long itemPrice,
                                  LocalDate startDate, LocalDate endDate){
        Item item = new Item();
        item.itemName = itemName;
        if(itemType.equals("일반"))
            item.itemType = Type.NORMAL;
        else
            item.itemType = Type.CORPORATE;
        item.itemPrice = itemPrice;
        item.setStartDate(startDate);
        item.setEndDate(endDate);
        return item;
    }
}
