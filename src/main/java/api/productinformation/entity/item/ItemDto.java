package api.productinformation.entity.item;

import api.productinformation.entity.ItemPromotion;
import api.productinformation.entity.Type;
import api.productinformation.entity.UserItem;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class ItemDto {
    private Long id;
    private String itemName;
    private String itemType;
    private Long itemPrice;
    private LocalDate startDate;
    private LocalDate endDate;

    public ItemDto(Long id, String itemName, String itemType, Long itemPrice, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.itemName = itemName;
        this.itemType = itemType;
        this.itemPrice = itemPrice;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public ItemDto(Item item){
        this.id = item.getId();
        this.itemName = item.getItemName();
        this.itemType = item.getItemType().toString();
        this.itemPrice = item.getItemPrice();
        this.startDate = item.getStartDate();
        this.endDate = item.getEndDate();
    }
}
