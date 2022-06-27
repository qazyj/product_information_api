package api.productinformation.repository;

import api.productinformation.entity.OrderItem;

import java.util.List;

public interface OrderItemRepositoryCustom {
    List<OrderItem> findByIdWithItem(Long id);
}
