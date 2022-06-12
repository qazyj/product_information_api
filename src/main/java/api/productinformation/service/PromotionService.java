package api.productinformation.service;

import api.productinformation.entity.promotion.Promotion;
import api.productinformation.entity.promotion.PromotionAdd;
import api.productinformation.entity.promotion.PromotionDto;
import api.productinformation.entity.user.UserAdd;
import api.productinformation.entity.user.UserDto;
import api.productinformation.exception.errorcode.CommonErrorCode;
import api.productinformation.exception.handler.InvalidDateTimeFormatException;
import api.productinformation.exception.handler.InvalidParameterException;
import api.productinformation.exception.handler.InvalidStartdateAfterEnddateException;
import api.productinformation.exception.handler.NotFoundResourceException;
import api.productinformation.repository.PromotionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeParseException;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PromotionService {
    private final PromotionRepository promotionRepository;

    public ResponseEntity<Object> savePromotion(PromotionAdd promotionAdd){
        checkArgsIsNull(promotionAdd);

        try {
            promotionAdd.StringToLocalDate();
        }  catch (DateTimeParseException ex) {
            throw new InvalidDateTimeFormatException(CommonErrorCode.INVALID_DATETIME_FORMAT);
        }
        promotionAdd.StringToLocalDate();

        if(promotionAdd.getStartDateLocalType().isAfter(promotionAdd.getEndDateLocalType())){
            throw new InvalidStartdateAfterEnddateException(CommonErrorCode.INVALID_STARTDATE_AFTER_ENDDATE);
        }

        Promotion savedPromotion = promotionRepository.save(Promotion.createPromotion(
                promotionAdd.getPromotionName(),
                promotionAdd.getDiscountAmount(),
                promotionAdd.getDiscountRate(),
                promotionAdd.getStartDateLocalType(),
                promotionAdd.getEndDateLocalType()));

        return new ResponseEntity<>(new PromotionDto(savedPromotion), HttpStatus.OK);
    }

    private void checkArgsIsNull(PromotionAdd promotionAdd) {
        if(promotionAdd.getPromotionName() == null ||
                promotionAdd.getEndDate() == null ||
                promotionAdd.getStartDate() == null ||
                (promotionAdd.getDiscountAmount() == null && promotionAdd.getDiscountRate() == null)) {
            throw new InvalidParameterException(CommonErrorCode.INVALID_PARAMETER);
        }
    }

    public ResponseEntity<Object> deletePromotion(Long id){
        if(id==null) throw new InvalidParameterException(CommonErrorCode.INVALID_PARAMETER);

        Promotion promotion = promotionRepository.findById(id).orElseThrow(
                () -> new NotFoundResourceException(CommonErrorCode.NOT_FOUND_RESOURCE));

        promotionRepository.delete(promotion);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
