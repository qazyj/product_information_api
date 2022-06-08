package api.productinformation.controller;

import api.productinformation.entity.promotion.PromotionAdd;
import api.productinformation.entity.promotion.PromotionSearch;
import api.productinformation.entity.user.UserAdd;
import api.productinformation.entity.user.UserSearch;
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
    public String savePromotion(PromotionAdd promotionAdd){
        promotionService.savePromotion(promotionAdd);
        return "ok";
    }

    @DeleteMapping("")
    public String deleteUser(PromotionSearch promotionSearch){
        promotionService.deletePromotion(promotionSearch);
        return "ok";
    }
}
