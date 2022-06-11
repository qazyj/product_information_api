package api.productinformation.controller;

import api.productinformation.entity.RequestDto;
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
    public ResponseEntity<Object> saveItem(@RequestBody ItemAdd itemAdd){
        return itemService.saveItem(itemAdd);
    }

    @DeleteMapping("")
    public ResponseEntity<Object> deleteItem(@RequestBody RequestDto requestDto){
        return itemService.deleteItem(requestDto.getId());
    }

    @GetMapping("/promotion")
    public ResponseEntity<Object> findItemPromotionById(@RequestBody RequestDto requestDto) {
        return itemService.findItemPromotionById(requestDto.getId());
    }
}
