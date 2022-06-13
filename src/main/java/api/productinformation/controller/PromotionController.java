package api.productinformation.controller;

import api.productinformation.dto.RequestDto;
import api.productinformation.service.PromotionService;
import api.productinformation.dto.promotion.NewPromotion;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/promotions")
public class PromotionController {
    private final PromotionService promotionService;

    @PostMapping("")
    public ResponseEntity<Object> savePromotion(@RequestBody NewPromotion newPromotion){
        return promotionService.savePromotion(newPromotion);
    }

    @DeleteMapping("")
    public ResponseEntity<Object> deleteUser(@RequestBody RequestDto requestDto){
        return promotionService.deletePromotion(requestDto.getId());
    }
}
