package api.productinformation.service;

import api.productinformation.dto.item.ItemDto;
import api.productinformation.dto.item.ItemPromotionDto;
import api.productinformation.dto.item.NewItem;
import api.productinformation.entity.Item;
import api.productinformation.entity.ItemPromotion;
import api.productinformation.exception.errorcode.CommonErrorCode;
import api.productinformation.exception.handler.InvalidDateTimeFormatException;
import api.productinformation.exception.handler.InvalidParameterException;
import api.productinformation.exception.handler.InvalidStartdateAfterEnddateException;
import api.productinformation.exception.handler.NotFoundResourceException;
import api.productinformation.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.Comparator;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    public ResponseEntity<Object> saveItem(NewItem newItem){
        checkArgsIsNull(newItem);

        try {
            newItem.StringToLocalDate();
        }  catch (DateTimeParseException ex) {
            throw new InvalidDateTimeFormatException(CommonErrorCode.INVALID_DATETIME_FORMAT);
        }

        if(newItem.getStartDateLocalType().isAfter(newItem.getEndDateLocalType())){
            throw new InvalidStartdateAfterEnddateException(CommonErrorCode.INVALID_STARTDATE_AFTER_ENDDATE);
        }

        Item savedItem = itemRepository.save(Item.createItem(newItem.getItemName(), newItem.getItemType(), newItem.getItemPrice(),
                newItem.getStartDateLocalType(), newItem.getEndDateLocalType()));

        return new ResponseEntity<>(ItemDto.from(savedItem), HttpStatus.OK);
    }

    public ResponseEntity<Object> deleteItem(Long id){
        if(id==null) throw new InvalidParameterException(CommonErrorCode.INVALID_PARAMETER);

        Item item = itemRepository.findById(id).orElseThrow(
                () -> new NotFoundResourceException(CommonErrorCode.NOT_FOUND_RESOURCE));

        itemRepository.delete(item);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Object> findItemPromotionById(Long id) {
        if(id==null) throw new InvalidParameterException(CommonErrorCode.INVALID_PARAMETER);

        // 많은 연관관계를 한번에 fetch join 하기 전 item이 있는 지 먼저 확인
        Item item = itemRepository.findById(id).orElseThrow(
                () -> new NotFoundResourceException(CommonErrorCode.NOT_FOUND_RESOURCE));

        Item findItem = itemRepository.findByIdIncludeMinPromotion(id).orElseThrow(
                () -> new NotFoundResourceException(CommonErrorCode.NOT_FOUND_RESOURCE));

        sortSalePrice(findItem);
        ItemPromotionDto itemPromotionDto = getItemPromotionDto(findItem);

        return new ResponseEntity<>(itemPromotionDto, HttpStatus.OK);
    }

    private void checkArgsIsNull(NewItem newItem) {
        if(newItem.getItemName() == null ||
                newItem.getItemPrice() == null ||
                newItem.getItemType() == null ||
                newItem.getStartDate() == null ||
                newItem.getEndDate() == null){
            throw new InvalidParameterException(CommonErrorCode.INVALID_PARAMETER);
        }
    }

    /**
     * @return 정렬된 가격 순으로 아이템 날짜와 프로모션 날짜가 겹치면 dto 변환 후
     * 단, 없을 경우 null 리턴
     */
    private ItemPromotionDto getItemPromotionDto(Item findItem) {

        for(ItemPromotion itemPromotion : findItem.getItemPromotions()){
            if(itemPromotion.getSalePrice() <= 0L) continue;

            if((findItem.getStartDate().compareTo(itemPromotion.getStartDate()) >= 0
                    && findItem.getStartDate().compareTo(itemPromotion.getEndDate()) < 0) ||
                    (findItem.getEndDate().compareTo(itemPromotion.getStartDate()) >= 0
                            && findItem.getEndDate().compareTo(itemPromotion.getEndDate()) < 0) ||
                    (itemPromotion.getStartDate().compareTo(findItem.getStartDate()) >= 0
                            && itemPromotion.getStartDate().compareTo(findItem.getEndDate()) < 0) ||
                    (itemPromotion.getEndDate().compareTo(findItem.getStartDate()) >= 0
                            && itemPromotion.getEndDate().compareTo(findItem.getEndDate()) < 0)) {
                return ItemPromotionDto.from(findItem, itemPromotion.getPromotion());
            }
        }

        // 없는 경우 프로모션 없으면 예외
        return ItemPromotionDto.from(findItem, null);
    }

    private void sortSalePrice(Item findItem) {
        Collections.sort(findItem.getItemPromotions(), new Comparator<ItemPromotion>() {
            @Override
            public int compare(ItemPromotion o1, ItemPromotion o2) {
                return o1.getSalePrice().intValue() - o2.getSalePrice().intValue();
            }
        });
    }
}
