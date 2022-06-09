package api.productinformation.entity.item;

import api.productinformation.entity.Type;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class ItemAdd {
    private String itemName;
    private String itemType;
    private Long itemPrice;
    private LocalDate startDate;
    private LocalDate endDate;

    public ItemAdd(String itemName, String itemType, Long itemPrice, String startDate, String endDate) {
        this.itemName = itemName;
        this.itemType = itemType;
        this.itemPrice = itemPrice;
        this.startDate = LocalDate.parse(startDate, DateTimeFormatter.ofPattern("yyyy.MM.dd"));
        this.endDate = LocalDate.parse(endDate, DateTimeFormatter.ofPattern("yyyy.MM.dd"));
    }
}
