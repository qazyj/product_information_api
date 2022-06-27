package api.productinformation.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    @JsonIgnore
    private Order order;

    private Long orderPrice; //주문 가격
    private int count; //주문 수량

    public void setOrder(Order order){
        this.order = order;
    }

    //==연관관계 메서드==//
    public void setItem(Item item) {
        this.item = item;
        item.getOrderItems().add(this);
    }

    //==생성 메서드==//
    public static OrderItem createOrderItem(Item item, Long orderPrice, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.item = item;
        orderItem.orderPrice = orderPrice;
        orderItem.count = count;

        item.removeStock(count);
        return orderItem;
    }

    //==비즈니스 로직==//
    public void cancel() {
        item.addStock(count);
    }

    //==조회 로직==//

    /**
     * 주문상품 전체 가격 조회
     */
    public Long getTotalPrice() {
        return getOrderPrice() * getCount();
    }
}

