package api.productinformation.dto.item;

import api.productinformation.entity.enumType.ItemType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor
public class NewItem {
    private String itemName;
    private String itemType;
    private Long itemPrice;
    private int stockQuantity;
    private String startDate;
    private String endDate;

    @Enumerated(EnumType.STRING)
    private ItemType realItemType;
    @DateTimeFormat(pattern = "yyyy.M.d")
    private LocalDate startDateLocalType;
    @DateTimeFormat(pattern = "yyyy.M.d")
    private LocalDate endDateLocalType;

    public NewItem(String itemName, String itemType, Long itemPrice, int stockQuantity, String startDate, String endDate) {
        this.itemName = itemName;
        this.itemType = itemType;
        this.itemPrice = itemPrice;
        this.stockQuantity = stockQuantity;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void StringToItemType(){
        this.realItemType = ItemType.nameOf(itemType);
    }

    public void StringToLocalDate() {
        this.startDateLocalType = LocalDate.parse(startDate, DateTimeFormatter.ofPattern("yyyy.M.d"));
        this.endDateLocalType = LocalDate.parse(endDate, DateTimeFormatter.ofPattern("yyyy.M.d"));
    }
}
