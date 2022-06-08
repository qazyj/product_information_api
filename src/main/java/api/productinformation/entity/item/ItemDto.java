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
    private Type itemType;
    private Long itemPrice;
    private LocalDate startDate;
    private LocalDate endDate;

    public ItemDto(Long id, String itemName, Type itemType, Long itemPrice, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.itemName = itemName;
        this.itemType = itemType;
        this.itemPrice = itemPrice;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
