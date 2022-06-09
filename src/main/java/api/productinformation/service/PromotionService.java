package api.productinformation.service;

import api.productinformation.entity.promotion.Promotion;
import api.productinformation.entity.promotion.PromotionAdd;
import api.productinformation.entity.promotion.PromotionDto;
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
    public PromotionDto savePromotion(PromotionAdd promotionAdd){
        Promotion savedPromotion = promotionRepository.save(Promotion.createPromotion(
                promotionAdd.getPromotionName(),
                promotionAdd.getDiscountAmount(),
                promotionAdd.getDiscountRate(),
                promotionAdd.getStartDate(),
                promotionAdd.getEndDate()));
        return new PromotionDto(savedPromotion);
    }

    @Transactional
    public void deletePromotion(PromotionSearch promotionSearch){
        Optional<Promotion> promotion = promotionRepository.findById(promotionSearch.getId());
        promotionRepository.delete(promotion.get());
    }
}
