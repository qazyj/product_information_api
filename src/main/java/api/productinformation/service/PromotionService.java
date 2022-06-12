package api.productinformation.service;

import api.productinformation.dto.promotion.PromotionDto;
import api.productinformation.entity.Item;
import api.productinformation.entity.ItemPromotion;
import api.productinformation.entity.Promotion;
import api.productinformation.dto.promotion.NewPromotion;
import api.productinformation.exception.errorcode.CommonErrorCode;
import api.productinformation.exception.handler.InvalidDateTimeFormatException;
import api.productinformation.exception.handler.InvalidParameterException;
import api.productinformation.exception.handler.InvalidStartdateAfterEnddateException;
import api.productinformation.exception.handler.NotFoundResourceException;
import api.productinformation.repository.ItemPromotionRepository;
import api.productinformation.repository.ItemRepository;
import api.productinformation.repository.PromotionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class PromotionService {
    private final PromotionRepository promotionRepository;
    private final ItemRepository itemRepository;
    private final ItemPromotionRepository itemPromotionRepository;

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

        List<Item> promotionConnectableItem =
                itemRepository.findPromotionConnectableItem(savedPromotion.getStartDate(), savedPromotion.getEndDate());

        // 아이템과 프로모션 중 기간이 겹치면 매핑
        for(Item item : promotionConnectableItem){
            itemPromotionRepository.save(ItemPromotion.createItemPromotion(item, savedPromotion));
        }

        return new ResponseEntity<>(PromotionDto.from(savedPromotion), HttpStatus.OK);
    }

    private void checkArgsIsNull(NewPromotion newPromotion) {
        if(Objects.isNull(newPromotion.getPromotionName()) ||
                Objects.isNull(newPromotion.getEndDate()) ||
                        Objects.isNull(newPromotion.getStartDate()) ||
                (Objects.isNull(newPromotion.getDiscountAmount()) && Objects.isNull(newPromotion.getDiscountRate()))) {
            throw new InvalidParameterException(CommonErrorCode.INVALID_PARAMETER);
        }
    }

    public ResponseEntity<Object> deletePromotion(Long id){
        if(Objects.isNull(id)) throw new InvalidParameterException(CommonErrorCode.INVALID_PARAMETER);

        Promotion promotion = promotionRepository.findById(id).orElseThrow(
                () -> new NotFoundResourceException(CommonErrorCode.NOT_FOUND_RESOURCE));

        promotionRepository.delete(promotion);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
