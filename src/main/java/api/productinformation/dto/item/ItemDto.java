package api.productinformation.dto.item;

import api.productinformation.entity.Item;
import api.productinformation.entity.Type;
import lombok.*;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemDto {
    private Long id;
    private String itemName;
    private Type itemType;
    private Long itemPrice;
    private LocalDate startDate;
    private LocalDate endDate;

    public String getItemType() {
        if(itemType.equals(Type.NORMAL)) {
            return "일반";
        }

        return "기업회원상품";
    }

    public static ItemDto from (Item item) {
        return ItemDto.builder()
                .id(item.getId())
                .itemName(item.getItemName())
                .itemType(item.getItemType())
                .itemPrice(item.getItemPrice())
                .startDate(item.getStartDate())
                .endDate(item.getEndDate())
                .build();
    }

}