package api.productinformation.controller;

import api.productinformation.entity.item.ItemAdd;
import api.productinformation.entity.item.ItemDto;
import api.productinformation.entity.item.ItemPromotionDto;
import api.productinformation.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {
    private final ItemService itemService;

    @PostMapping("")
    public ResponseEntity<Object> saveItem(ItemDto itemDto){
        return itemService.saveItem(itemDto);
    }

    @DeleteMapping("/{item_id}")
    public ResponseEntity<Object> deleteItem(@PathVariable("item_id") Long id){
        return itemService.deleteItem(id);
    }

    @GetMapping("/promotion/{item_id}")
    public ResponseEntity<Object> findItemPromotionById(@PathVariable("item_id") Long id) {
        return itemService.findItemPromotionById(id);
    }
}
