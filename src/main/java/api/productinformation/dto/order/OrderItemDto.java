package api.productinformation.dto.order;

import api.productinformation.dto.item.DeliveryItemDto;
import api.productinformation.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemDto {
    private Long orderId;
    private int totalPrice;
    private String deliveryState;
    private String orderDate;

    private List<DeliveryItemDto> items = new ArrayList<>();

    public static OrderItemDto from(Order order, List<DeliveryItemDto> deliveryItemDtoList) {
        return OrderItemDto.builder()
                .orderId(order.getId())
                .totalPrice(order.getTotalPrice())
                .deliveryState(order.getDeliveryState().toString())
                .orderDate(order.getOrderDate().toString())
                .items(deliveryItemDtoList)
                .build();
    }
}
