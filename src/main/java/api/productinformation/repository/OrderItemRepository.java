package api.productinformation.repository;

import api.productinformation.entity.Order;
import api.productinformation.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderItemRepository  extends JpaRepository<OrderItem, Long>, OrderItemRepositoryCustom {
    /*@Query( "select oi from OrderItem oi" +
            " join fetch oi.item i" +
            " where oi.order.id = :id")
    List<OrderItem> findByIdWithItem(Long id);*/
}
