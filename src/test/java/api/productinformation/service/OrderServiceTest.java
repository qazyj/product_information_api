package api.productinformation.service;

import api.productinformation.dto.order.OrderDto;
import api.productinformation.entity.Address;
import api.productinformation.entity.Item;
import api.productinformation.entity.Order;
import api.productinformation.entity.User;
import api.productinformation.entity.enumType.ItemType;
import api.productinformation.entity.enumType.OrderState;
import api.productinformation.entity.enumType.UserState;
import api.productinformation.entity.enumType.UserType;
import api.productinformation.exception.handler.InvalidItemStockquantityException;
import api.productinformation.repository.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class OrderServiceTest {

    @Autowired
    EntityManager em;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;

    @Test
    public void 상품주문() throws Exception {
        //given
        User user = User.createUser("김영진", UserType.NORMAL, UserState.USE, new Address("인천", "남동구 논고개로", "21667"));

        Item item = Item.createItem("테스트 아이템", ItemType.NORMAL, 20000L, 10,
                LocalDate.of(2022, 1, 1),
                LocalDate.of(2022, 1, 1));

        int orderCount = 2;
        em.persist(user);
        em.persist(item);
        em.flush();
        em.clear();

        //when
        OrderDto orderId = (OrderDto) orderService.order(user.getId(), item.getId(), orderCount).getBody();
        em.flush();
        em.clear();

        //then
        Order getOrder = orderRepository.findById(orderId.getId()).get();

        assertThat(getOrder.getOrderState()).isEqualTo(OrderState.ORDER);
        assertThat(getOrder.getOrderItems().size()).isEqualTo(1);
        assertThat(getOrder.getTotalPrice()).isEqualTo(20000*2);
        assertThat(getOrder.getOrderItems().get(0).getItem().getStockQuantity()).isEqualTo(8);
    }

    @Test
    public void 상품주문_재고수량초과() throws Exception {
        //given
        User user = User.createUser("김영진", UserType.NORMAL, UserState.USE, new Address("인천", "남동구 논고개로", "21667"));

        Item item = Item.createItem("테스트 아이템", ItemType.NORMAL, 20000L, 10,
                LocalDate.of(2022, 1, 1),
                LocalDate.of(2022, 1, 1));

        int orderCount = 11;
        em.persist(user);
        em.persist(item);
        em.flush();
        em.clear();

        //when
        Assertions.assertThrows(InvalidItemStockquantityException.class, () -> {
            OrderDto orderId = (OrderDto) orderService.order(user.getId(), item.getId(), orderCount).getBody();
        });

        //then

        //fail("재고 수량 부족 예외가 발행해야 한다.");
    }

    @Test
    public void 주문취소() throws Exception {
        //given
        User user = User.createUser("김영진", UserType.NORMAL, UserState.USE, new Address("인천", "남동구 논고개로", "21667"));

        Item item = Item.createItem("테스트 아이템", ItemType.NORMAL, 20000L, 10,
                LocalDate.of(2022, 1, 1),
                LocalDate.of(2022, 1, 1));

        int orderCount = 2;
        em.persist(user);
        em.persist(item);
        em.flush();
        em.clear();
        OrderDto order = (OrderDto) orderService.order(user.getId(), item.getId(), orderCount).getBody();


        //when
        orderService.cancelOrder(order.getId());

        //then
        Order getOrder = orderRepository.findById(order.getId()).get();

        assertThat(getOrder.getOrderState()).isEqualTo(OrderState.CANCEL);
        assertThat(getOrder.getOrderItems().get(0).getItem().getStockQuantity()).isEqualTo(10);
    }
}