package api.productinformation.repository;

import api.productinformation.entity.Type;
import api.productinformation.entity.item.ItemDto;
import api.productinformation.entity.item.ItemPromotionDto;

import java.util.List;

public interface ItemRepositoryCustom {
    List<ItemDto> findCanBuyItemList();
    List<ItemDto> findCanBuyItemListByType(Type type);
    List<ItemPromotionDto> findByIdIncludeMinPromotion(Long id);
}
