package api.productinformation.dto.user;

import api.productinformation.dto.order.OrderDto;
import api.productinformation.dto.order.OrderItemDto;
import api.productinformation.entity.Address;
import api.productinformation.entity.Order;
import api.productinformation.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserOrderItemDto {

    // user entity
    private Long userId;
    private String userName;
    private String addressCity;
    private String addressStreet;
    private String addressZipcode;

    private List<OrderItemDto> orders = new ArrayList<>();

    public static UserOrderItemDto from(User user, List<OrderItemDto> orderItemDtos) {
        return UserOrderItemDto.builder()
                .userId(user.getId())
                .userName(user.getUserName())
                .addressCity(user.getAddress().getCity())
                .addressStreet(user.getAddress().getStreet())
                .addressZipcode(user.getAddress().getZipcode())
                .orders(orderItemDtos)
                .build();
    }
}
