package api.productinformation.dto.item;

import api.productinformation.entity.Item;
import lombok.*;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemDto {
    private Long id;
    private String itemName;
    private String itemType;
    private Long itemPrice;
    private int stockQuantity;
    private LocalDate startDate;
    private LocalDate endDate;

    public static ItemDto from (Item item) {
        return ItemDto.builder()
                .id(item.getId())
                .itemName(item.getItemName())
                .itemType(item.getItemType().getValue())
                .itemPrice(item.getItemPrice())
                .stockQuantity(item.getStockQuantity())
                .startDate(item.getStartDate())
                .endDate(item.getEndDate())
                .build();
    }
}