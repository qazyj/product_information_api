package api.productinformation.dto.item;

import api.productinformation.entity.Item;
import api.productinformation.entity.ItemType;
import api.productinformation.entity.UserType;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
    private LocalDate startDate;
    private LocalDate endDate;

    public static ItemDto from (Item item) {
        return ItemDto.builder()
                .id(item.getId())
                .itemName(item.getItemName())
                .itemType(item.getItemType().getValue())
                .itemPrice(item.getItemPrice())
                .startDate(item.getStartDate())
                .endDate(item.getEndDate())
                .build();
    }
}