package api.productinformation.repository;

import api.productinformation.entity.Type;
import api.productinformation.entity.item.ItemDto;
import api.productinformation.entity.item.ItemPromotionDto;
import api.productinformation.entity.promotion.Promotion;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

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
    public List<ItemDto> findCanBuyItemListByType(Type type) {
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
    public List<ItemPromotionDto> findByIdIncludeMinPromotion(Long id) {
        return em.createQuery("select new api.productinformation.entity.item.ItemPromotionDto(i.id, i.itemName, i.itemType, i.itemPrice, ip.salePrice, p) from Item i" +
                        " join fetch i.itemPromotions ip" +
                        " join fetch ip.promotion p" +
                        " where i.id = :id" +
                        " and ip.salePrice > 0" +
                        " and ((i.startDate between p.startDate and p.endDate)" +
                        " or (i.endDate between p.startDate and p.endDate))", ItemPromotionDto.class)
                .setParameter("id", id)
                .getResultList();
    }
}
