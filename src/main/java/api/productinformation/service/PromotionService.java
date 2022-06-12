package api.productinformation.service;

import api.productinformation.entity.Promotion;
import api.productinformation.dto.promotion.NewPromotion;
import api.productinformation.dto.promotion.PromotionDto;
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

@Service
@Transactional
@RequiredArgsConstructor
public class PromotionService {
    private final PromotionRepository promotionRepository;

    public ResponseEntity<Object> savePromotion(NewPromotion newPromotion){
        checkArgsIsNull(newPromotion);

        try {
            newPromotion.StringToLocalDate();
        }  catch (DateTimeParseException ex) {
            throw new InvalidDateTimeFormatException(CommonErrorCode.INVALID_DATETIME_FORMAT);
        }
        newPromotion.StringToLocalDate();

        if(newPromotion.getStartDateLocalType().isAfter(newPromotion.getEndDateLocalType())){
            throw new InvalidStartdateAfterEnddateException(CommonErrorCode.INVALID_STARTDATE_AFTER_ENDDATE);
        }

        Promotion savedPromotion = promotionRepository.save(Promotion.createPromotion(
                newPromotion.getPromotionName(),
                newPromotion.getDiscountAmount(),
                newPromotion.getDiscountRate(),
                newPromotion.getStartDateLocalType(),
                newPromotion.getEndDateLocalType()));

        return new ResponseEntity<>(PromotionDto.from(savedPromotion), HttpStatus.OK);
    }

    private void checkArgsIsNull(NewPromotion newPromotion) {
        if(newPromotion.getPromotionName() == null ||
                newPromotion.getEndDate() == null ||
                newPromotion.getStartDate() == null ||
                (newPromotion.getDiscountAmount() == null && newPromotion.getDiscountRate() == null)) {
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
