package api.productinformation.dto.order;

import api.productinformation.dto.item.ItemDto;
import api.productinformation.entity.*;
import api.productinformation.entity.enumType.DeliveryState;
import api.productinformation.entity.enumType.OrderState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDto {
    private Long id;
    private String orderState;
    private String deliveryState;
    private String city;
    private String street;
    private String zipcode;
    private LocalDateTime orderDate;
    private List<OrderItemDto> orderItemDtoList = new ArrayList<>();

    public static OrderDto from (Order order) {
        return OrderDto.builder()
                .id(order.getId())
                .orderState(order.getOrderState().toString())
                .deliveryState(order.getDeliveryState().toString())
                .city(order.getAddress().getCity())
                .street(order.getAddress().getStreet())
                .zipcode(order.getAddress().getZipcode())
                .orderDate(order.getOrderDate())
                .build();
    }
}
