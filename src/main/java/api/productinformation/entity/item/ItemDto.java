package api.productinformation.entity.item;

import api.productinformation.entity.Type;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ItemDto {
    private Long id;
    private String itemName;
    private String itemType;
    private Long itemPrice;
    private LocalDate startDate;
    private LocalDate endDate;

    public ItemDto(Long id, String itemName, Type itemType, Long itemPrice, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.itemName = itemName;
        if(itemType.equals(Type.NORMAL))
            this.itemType = "일반";
        else
            this.itemType = "기업회원상품";
        this.itemPrice = itemPrice;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public ItemDto(Item item){
        this.id = item.getId();
        this.itemName = item.getItemName();
        if(item.getItemType().equals(Type.NORMAL))
            this.itemType = "일반";
        else
            this.itemType = "기업회원상품";
        this.itemPrice = item.getItemPrice();
        this.startDate = item.getStartDate();
        this.endDate = item.getEndDate();
    }
}
