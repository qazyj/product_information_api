package api.productinformation.controller;

import api.productinformation.entity.promotion.PromotionAdd;
import api.productinformation.entity.promotion.PromotionDto;
import api.productinformation.entity.promotion.PromotionSearch;
import api.productinformation.service.PromotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/promotions")
public class PromotionController {
    private final PromotionService promotionService;

    @PostMapping("")
    public PromotionDto savePromotion(PromotionAdd promotionAdd){
        return promotionService.savePromotion(promotionAdd);
    }

    @DeleteMapping("")
    public String deleteUser(PromotionSearch promotionSearch){
        promotionService.deletePromotion(promotionSearch);
        return "ok";
    }
}
