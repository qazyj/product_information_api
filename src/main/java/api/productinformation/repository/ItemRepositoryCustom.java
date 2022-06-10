package api.productinformation.repository;

import api.productinformation.entity.Type;
import api.productinformation.entity.item.Item;
import api.productinformation.entity.item.ItemDto;

import java.util.List;
import java.util.Optional;

public interface ItemRepositoryCustom {
    List<ItemDto> findCanBuyItemList();
    List<ItemDto> findCanBuyItemListByType(Type type);
    Optional<Item> findByIdIncludeMinPromotion(Long id);
}
