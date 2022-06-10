package api.productinformation.service;

import api.productinformation.entity.ItemPromotion;
import api.productinformation.entity.item.*;
import api.productinformation.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Comparator;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    @Transactional
    public ItemDto saveItem(ItemAdd itemAdd){

        Item savedItem = itemRepository.save(Item.createItem(itemAdd.getItemName(), itemAdd.getItemType(), itemAdd.getItemPrice(),
                itemAdd.getStartDate(), itemAdd.getEndDate()));
        return new ItemDto(savedItem);
    }

    @Transactional
    public void deleteItem(ItemSearch itemSearch){
        Optional<Item> item = itemRepository.findById(itemSearch.getId());
        itemRepository.delete(item.get());
    }

    @Transactional(readOnly = true)
    public ItemPromotionDto findItemPromotionById(Long id) {
        Optional<Item> findItem = itemRepository.findByIdIncludeMinPromotion(id);
        //if(findItem.isEmpty()) 널인경우 예외

        sortSalePrice(findItem);

        return getItemPromotionDto(findItem);
    }

    /**
     * @return 정렬된 가격 순으로 아이템 날짜와 프로모션 날짜가 겹치면 dto 변환 후
     * 단, 없을 경우 null 리턴
     */
    private ItemPromotionDto getItemPromotionDto(Optional<Item> findItem) {
        for(ItemPromotion itemPromotion : findItem.get().getItemPromotions()){
            if(itemPromotion.getSalePrice() <= 0L) continue;

            if((findItem.get().getStartDate().isAfter(itemPromotion.getStartDate())
                    && findItem.get().getStartDate().isBefore(itemPromotion.getEndDate())) ||
                    (findItem.get().getEndDate().isAfter(itemPromotion.getStartDate())
                            && findItem.get().getEndDate().isBefore(itemPromotion.getEndDate()))) {
                return new ItemPromotionDto(findItem.get(), itemPromotion.getPromotion());
            }
        }
        // 없는 경우 프로모션 없으면 예외
        return null;
    }

    private void sortSalePrice(Optional<Item> findItem) {
        Collections.sort(findItem.get().getItemPromotions(), new Comparator<ItemPromotion>() {
            @Override
            public int compare(ItemPromotion o1, ItemPromotion o2) {
                return o1.getSalePrice().intValue() - o2.getSalePrice().intValue();
            }
        });
    }
}
