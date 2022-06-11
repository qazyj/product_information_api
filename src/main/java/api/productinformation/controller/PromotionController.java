package api.productinformation.controller;

import api.productinformation.entity.RequestDto;
import api.productinformation.entity.promotion.PromotionAdd;
import api.productinformation.entity.promotion.PromotionDto;
import api.productinformation.service.PromotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/promotions")
public class PromotionController {
    private final PromotionService promotionService;

    @PostMapping("")
    public ResponseEntity<Object> savePromotion(@RequestBody PromotionAdd promotionAdd){
        promotionAdd.StringToLocalDate();
        return promotionService.savePromotion(promotionAdd);
    }

    @DeleteMapping("")
    public ResponseEntity<Object> deleteUser(@RequestBody RequestDto requestDto){
        return promotionService.deletePromotion(requestDto.getId());
    }
}
