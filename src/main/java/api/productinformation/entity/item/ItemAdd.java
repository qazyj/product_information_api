package api.productinformation.entity.item;

import api.productinformation.entity.Type;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ItemAdd {
    private String itemName;
    private String itemType;
    private Long itemPrice;
    private LocalDate startDate;
    private LocalDate endDate;

    public ItemAdd(String itemName, String itemType, Long itemPrice, LocalDate startDate, LocalDate endDate) {
        this.itemName = itemName;
        this.itemType = itemType;
        this.itemPrice = itemPrice;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
