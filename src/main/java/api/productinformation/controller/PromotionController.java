package api.productinformation.controller;

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
    public ResponseEntity<Object> savePromotion(PromotionAdd promotionAdd){
        return promotionService.savePromotion(promotionAdd);
    }

    @DeleteMapping("/{promotion_id}")
    public ResponseEntity<Object> deleteUser(@PathVariable("promotion_id") Long id){
        return promotionService.deletePromotion(id);
    }
}
