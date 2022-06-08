package api.productinformation.service;

import api.productinformation.entity.UserItem;
import api.productinformation.entity.item.Item;
import api.productinformation.entity.item.ItemAdd;
import api.productinformation.entity.item.ItemSearch;
import api.productinformation.entity.promotion.Promotion;
import api.productinformation.entity.promotion.PromotionAdd;
import api.productinformation.entity.promotion.PromotionSearch;
import api.productinformation.repository.PromotionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PromotionService {
    private final PromotionRepository promotionRepository;

    @Transactional
    public void savePromotion(PromotionAdd promotionAdd){
        Promotion promotion = Promotion.createPromotion(
                promotionAdd.getPromotionName(),
                promotionAdd.getDiscountAmount(),
                promotionAdd.getDiscountRate(),
                promotionAdd.getStartDate(),
                promotionAdd.getEndDate());
        promotionRepository.save(promotion);
    }

    @Transactional
    public void deletePromotion(PromotionSearch promotionSearch){
        Optional<Promotion> promotion = promotionRepository.findById(promotionSearch.getId());
        promotionRepository.delete(promotion.get());
    }
}