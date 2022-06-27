package api.productinformation.entity;

import api.productinformation.entity.enumType.DeliveryState;
import api.productinformation.entity.enumType.OrderState;
import api.productinformation.exception.errorcode.OrderErrorCode;
import api.productinformation.exception.handler.InvalidCancelException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderState orderState;

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    @Enumerated(EnumType.STRING)
    private DeliveryState deliveryState;

    @Embedded
    private Address address;

    //==연관관계 메서드==//
    public void setUser(User user) {
        this.user = user;
        user.getOrders().add(this);
    }

    public void addItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    //==생성 메서드==//
    public static Order createOrder(User user, OrderItem... orderItems) {
        Order order = new Order();
        order.setUser(user);
        for (OrderItem orderItem : orderItems) {
            order.addItem(orderItem);
        }
        order.orderState = OrderState.ORDER;
        order.orderDate = LocalDateTime.now();
        order.deliveryState = DeliveryState.READY;
        order.address = user.getAddress();
        return order;
    }

    //==비즈니스 로직==//
    /**
     * 주문 취소
     */
    public void cancel() {
        // 배송중인 아이템은 취소 불가
        if (deliveryState == DeliveryState.COMP)
        {
            throw new InvalidCancelException(OrderErrorCode.INVALID_CANCEL);
        }

        orderState = OrderState.CANCEL;

        for (OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
    }

    //==조회 로직==//
    /**
     * 전체 주문 가격 조회
     */
    public int getTotalPrice() {
        int totalPrice = 0;
        for (OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }
}
