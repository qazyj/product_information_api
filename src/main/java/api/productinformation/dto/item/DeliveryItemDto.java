package api.productinformation.dto.item;

import api.productinformation.entity.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliveryItemDto {
    private String itemName;
    private Long ordinaryPrice;
    private Long orderPrice;
    private int orderCount;

    public static DeliveryItemDto from(OrderItem orderItem) {
        return DeliveryItemDto.builder()
                .itemName(orderItem.getItem().getItemName())
                .ordinaryPrice(orderItem.getItem().getItemPrice())
                .orderPrice(orderItem.getOrderPrice())
                .orderCount(orderItem.getCount())
                .build();
    }
}
