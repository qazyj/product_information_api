package api.productinformation.service;

import api.productinformation.entity.UserItem;
import api.productinformation.entity.item.Item;
import api.productinformation.entity.item.ItemAdd;
import api.productinformation.entity.item.ItemSearch;
import api.productinformation.entity.user.User;
import api.productinformation.entity.user.UserAdd;
import api.productinformation.entity.user.UserSearch;
import api.productinformation.repository.ItemRepository;
import api.productinformation.repository.UserItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final UserItemRepository userItemRepository;

    @Transactional
    public void saveItem(ItemAdd itemAdd){
        Optional<UserItem> findUserItem = getUserItem(itemAdd);
        Item item = Item.createItem(itemAdd.getItemName(), itemAdd.getItemType(), itemAdd.getItemPrice(),
                findUserItem.get(), itemAdd.getStartDate(), itemAdd.getEndDate());
        itemRepository.save(item);
    }

    @Transactional
    public void deleteUser(ItemSearch itemSearch){
        Optional<Item> item = itemRepository.findById(itemSearch.getId());
        itemRepository.delete(item.get());
    }

    private Optional<UserItem> getUserItem(ItemAdd itemAdd) {
        if(itemAdd.getItemType().equals("일반"))
            return userItemRepository.findById(1L);
        else
            return userItemRepository.findById(2L);
    }
}
