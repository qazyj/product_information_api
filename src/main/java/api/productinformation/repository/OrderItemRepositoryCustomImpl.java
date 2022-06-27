package api.productinformation.repository;

import api.productinformation.entity.OrderItem;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
public class OrderItemRepositoryCustomImpl implements OrderItemRepositoryCustom{
    private final EntityManager em;

    @Override
    public List<OrderItem> findByIdWithItem(Long id) {
        return em.createQuery("select oi from OrderItem oi" +
                " join fetch oi.item i" +
                " where oi.order.id = :id", OrderItem.class)
                .setParameter("id", id)
                .getResultList();
    }
}
