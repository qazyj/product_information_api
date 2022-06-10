package api.productinformation.controller;

import api.productinformation.entity.item.ItemAdd;
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
    public String saveItem(ItemAdd itemAdd){
        itemService.saveItem(itemAdd);
        return "ok";
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
