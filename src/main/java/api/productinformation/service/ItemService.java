package api.productinformation.service;

import api.productinformation.entity.item.Item;
import api.productinformation.entity.item.ItemAdd;
import api.productinformation.entity.item.ItemDto;
import api.productinformation.entity.item.ItemSearch;
import api.productinformation.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
