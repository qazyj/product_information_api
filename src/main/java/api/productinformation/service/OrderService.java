package api.productinformation.service;

import api.productinformation.dto.RequestDto;
import api.productinformation.dto.item.ItemPromotionDto;
import api.productinformation.dto.order.OrderDto;
import api.productinformation.dto.user.UserDto;
import api.productinformation.entity.Item;
import api.productinformation.entity.Order;
import api.productinformation.entity.OrderItem;
import api.productinformation.entity.User;
import api.productinformation.exception.errorcode.CommonErrorCode;
import api.productinformation.exception.handler.InvalidParameterException;
import api.productinformation.exception.handler.NotFoundResourceException;
import api.productinformation.repository.ItemRepository;
import api.productinformation.repository.OrderRepository;
import api.productinformation.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final ItemService itemService;

    /**
     * 주문
     */
    @Transactional
    public ResponseEntity<Object> order(Long userId, Long itemId, int count) {

        //엔티티 조회z
        User user = userRepository.findById(userId).get();
        Item item = itemRepository.findByIdIncludePromotion(itemId).orElseThrow(
                () -> new NotFoundResourceException(CommonErrorCode.NOT_FOUND_RESOURCE));

        itemService.sortSalePrice(item);

        // 유저 탈퇴 예외처리 (탈퇴되어 있는 경우 주문을 할 수 없다.)
        user.withdraw();

        // 적용할 수 있는 프로모션 중 가장 많이 세일하는 프로모션을 적용
        // if 적용할 수 있는 프로모션이 없는 경우, 본래 price 값
        ItemPromotionDto dto = itemService.getItemPromotionDto(item);
        Long price = (Objects.isNull(dto.getSalePrice()))? dto.getItemPrice():dto.getSalePrice();

        //주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, price, count);

        Order savedOrder = orderRepository.save(Order.createOrder(user, orderItem));

        return new ResponseEntity<>(OrderDto.from(savedOrder), HttpStatus.OK);
    }

    /**
     * 주문 취소
     */
    @Transactional
    public ResponseEntity<Object> cancelOrder(Long orderId) {
        //주문 엔티티 조회
        Optional<Order> order = orderRepository.findById(orderId);

        // null 일 경우
        if(order.isEmpty()) throw new InvalidParameterException(CommonErrorCode.INVALID_PARAMETER);

        //주문 취소
        order.get().cancel();

        return new ResponseEntity<>("ok", HttpStatus.OK);
    }
}
