package api.productinformation.controller;

import api.productinformation.entity.item.ItemAdd;
import api.productinformation.entity.item.ItemDto;
import api.productinformation.entity.item.ItemPromotionDto;
import api.productinformation.entity.item.ItemSearch;
import api.productinformation.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {
    private final ItemService itemService;

    @PostMapping("")
    public ItemDto saveItem(ItemAdd itemAdd){
        return itemService.saveItem(itemAdd);
    }

    @DeleteMapping("")
    public String deleteItem(ItemSearch itemSearch){
        itemService.deleteItem(itemSearch);
        return "ok";
    }

    @GetMapping("/promotion/{item_id}")
    public Optional<ItemPromotionDto> findItemPromotionById(@PathVariable("item_id") Long id) {
        return itemService.findItemPromotionById(id);
    }
}
