package api.productinformation.service;

import api.productinformation.dto.RequestDto;
import api.productinformation.dto.order.OrderDto;
import api.productinformation.dto.user.UserDto;
import api.productinformation.entity.Item;
import api.productinformation.entity.Order;
import api.productinformation.entity.OrderItem;
import api.productinformation.entity.User;
import api.productinformation.exception.errorcode.CommonErrorCode;
import api.productinformation.exception.handler.InvalidParameterException;
import api.productinformation.repository.ItemRepository;
import api.productinformation.repository.OrderRepository;
import api.productinformation.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    /**
     * 주문
     */
    @Transactional
    public ResponseEntity<Object> order(Long userId, Long itemId, int count) {

        //엔티티 조회
        User user = userRepository.findById(userId).get();
        Item item = itemRepository.findById(itemId).get();

        //주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getItemPrice(), count);

        Order savedOrder = orderRepository.save(Order.createOrder(user, orderItem));

        return new ResponseEntity<>(OrderDto.from(savedOrder), HttpStatus.OK);
    }

    /**
     * 주문 취소
     */
    @Transactional
    public void cancelOrder(Long orderId) {
        //주문 엔티티 조회
        Optional<Order> order = orderRepository.findById(orderId);

        // null 일 경우
        if(order.isEmpty()) throw new InvalidParameterException(CommonErrorCode.INVALID_PARAMETER);

        //주문 취소
        order.get().cancel();
    }

    //검색
    @Transactional(readOnly = true)
    public List<Order> findOrders(Long id) {
        return null;
        //return orderRepository.findById(id).get();
    }
}
