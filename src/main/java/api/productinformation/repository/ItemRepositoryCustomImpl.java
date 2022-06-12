package api.productinformation.repository;

import api.productinformation.entity.ItemType;
import api.productinformation.entity.UserType;
import api.productinformation.entity.Item;
import api.productinformation.dto.item.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class ItemRepositoryCustomImpl implements ItemRepositoryCustom{
    private final EntityManager em;


    @Override
    public List<Item> findCanBuyItemList() {
        LocalDate now = LocalDate.now();
        return em.createQuery("select i" +
                " from Item i" +
                " where i.startDate <= :now" +
                " and i.endDate >= :now", Item.class)
                .setParameter("now", now)
                .getResultList();
    }

    @Override
    public List<Item> findCanBuyItemListByType(UserType type) {
        LocalDate now = LocalDate.now();
        return em.createQuery("select i" +
                        " from Item i" +
                        " where i.itemType = :type" +
                        " and i.startDate <= :now" +
                        " and i.endDate >= :now", Item.class)
                .setParameter("type", ItemType.NORMAL)
                .setParameter("now", now)
                .getResultList();
    }

    @Override
    public Optional<Item> findByIdIncludeMinPromotion(Long id) {
        return Optional.ofNullable(em.createQuery("select distinct i from Item i" +
                        " join fetch i.itemPromotions itempromotions" +
                        " join fetch itempromotions.promotion p" +
                        " where i.id = :id", Item.class)
                .setParameter("id", id)
                .getSingleResult());
    }

    @Override
    public List<Item> findPromotionConnectableItem(LocalDate startDate, LocalDate endDate) {
        return em.createQuery("select i from Item i" +
                        " where (i.startDate between :startDate and :endDate)" +
                        " or (i.endDate between :startDate and :endDate)" +
                        " or (:startDate between i.startDate and i.endDate)" +
                        " or (:endDate between i.startDate and i.endDate)", Item.class)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getResultList();
    }
}
