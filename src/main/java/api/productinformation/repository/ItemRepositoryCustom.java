package api.productinformation.repository;

import api.productinformation.entity.enumType.UserType;
import api.productinformation.entity.Item;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ItemRepositoryCustom {
    List<Item> findCanBuyItemList();
    List<Item> findCanBuyItemListByType(UserType type);
    Optional<Item> findByIdIncludePromotion(Long id);
    List<Item> findPromotionConnectableItem(LocalDate startDate, LocalDate endDate);
}
