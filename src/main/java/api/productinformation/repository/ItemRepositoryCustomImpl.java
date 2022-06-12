package api.productinformation.repository;

import api.productinformation.entity.UserType;
import api.productinformation.entity.Item;
import api.productinformation.dto.item.*;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class ItemRepositoryCustomImpl implements ItemRepositoryCustom{
    private final EntityManager em;

    @Override
    public List<ItemDto> findCanBuyItemList() {
        LocalDate now = LocalDate.now();
        return em.createQuery("select new api.productinformation.entity.item.ItemDto(i.id, i.itemName, i.itemType, i.itemPrice, i.startDate, i.endDate)" +
                " from Item i" +
                " where i.startDate <= :now" +
                " and i.endDate >= :now", ItemDto.class)
                .setParameter("now", now)
                .getResultList();
    }

    @Override
    public List<ItemDto> findCanBuyItemListByType(UserType type) {
        LocalDate now = LocalDate.now();
        return em.createQuery("select new api.productinformation.entity.item.ItemDto(i.id, i.itemName, i.itemType, i.itemPrice, i.startDate, i.endDate)" +
                        " from Item i" +
                        " where i.itemType = :type" +
                        " and i.startDate <= :now" +
                        " and i.endDate >= :now", ItemDto.class)
                .setParameter("type", type)
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
}
