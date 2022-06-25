package api.productinformation.controller;

import api.productinformation.dto.RequestDto;
import api.productinformation.dto.item.NewItem;
import api.productinformation.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {
    private final ItemService itemService;

    @PostMapping("")
    public ResponseEntity<Object> saveItem(@RequestBody NewItem newItem){
        return itemService.saveItem(newItem);
    }

    @DeleteMapping("")
    public ResponseEntity<Object> deleteItem(@RequestBody RequestDto requestDto){
        return itemService.deleteItem(requestDto.getId());
    }

    @GetMapping("/promotion/{id}")
    public ResponseEntity<Object> findItemPromotionById(@RequestParam("id") Long id) {
        return itemService.findItemPromotionById(id);
    }
}
