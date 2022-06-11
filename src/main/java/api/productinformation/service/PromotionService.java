package api.productinformation.service;

import api.productinformation.entity.promotion.Promotion;
import api.productinformation.entity.promotion.PromotionAdd;
import api.productinformation.entity.promotion.PromotionDto;
import api.productinformation.entity.user.UserDto;
import api.productinformation.exception.errorcode.CommonErrorCode;
import api.productinformation.exception.handler.NotFoundResourceException;
import api.productinformation.repository.PromotionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PromotionService {
    private final PromotionRepository promotionRepository;

    @Transactional
    public ResponseEntity<Object> savePromotion(PromotionAdd promotionAdd){
        Promotion savedPromotion = promotionRepository.save(Promotion.createPromotion(
                promotionAdd.getPromotionName(),
                promotionAdd.getDiscountAmount(),
                promotionAdd.getDiscountRate(),
                promotionAdd.getStartDateLocalType(),
                promotionAdd.getEndDateLocalType()));
        return new ResponseEntity<>(new PromotionDto(savedPromotion), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<Object> deletePromotion(Long id){
        Promotion promotion = promotionRepository.findById(id).orElseThrow(
                () -> new NotFoundResourceException(CommonErrorCode.NOT_FOUND_RESOURCE));

        promotionRepository.delete(promotion);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
