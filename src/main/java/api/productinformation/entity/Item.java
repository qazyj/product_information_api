package api.productinformation.entity;

import api.productinformation.entity.enumType.ItemType;
import api.productinformation.exception.errorcode.ItemErrorCode;
import api.productinformation.exception.handler.InvalidItemStockquantityException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item extends DateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<ItemPromotion> itemPromotions = new ArrayList<>();

    private String itemName;

    @Enumerated(EnumType.STRING)
    private ItemType itemType;

    private Long itemPrice;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<OrderItem> orderItems = new ArrayList<>();

    private int stockQuantity;

    //==생성 메서드==//
    public static Item createItem(String itemName, ItemType itemType, Long itemPrice, int stockQuantity,
                                  LocalDate startDate, LocalDate endDate){
        Item item = new Item();
        item.itemName = itemName;
        item.itemType = itemType;
        item.itemPrice = itemPrice;
        item.stockQuantity = stockQuantity;
        item.setStartDate(startDate);
        item.setEndDate(endDate);
        return item;
    }

    //==비즈니스 로직==//
    /**
     * stock 증가
     */
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    /**
     * stock 감소
     */
    public void removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity;
        if (restStock < 0) {
            throw new InvalidItemStockquantityException(ItemErrorCode.INVALID_ITEM_STOCKQUANTITY);
        }
        this.stockQuantity = restStock;
    }
}
