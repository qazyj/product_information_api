package api.productinformation.repository;

import api.productinformation.entity.Type;
import api.productinformation.entity.Item;
import api.productinformation.dto.item.*;

import java.util.List;
import java.util.Optional;

public interface ItemRepositoryCustom {
    List<ItemDto> findCanBuyItemList();
    List<ItemDto> findCanBuyItemListByType(Type type);
    Optional<Item> findByIdIncludeMinPromotion(Long id);
}
