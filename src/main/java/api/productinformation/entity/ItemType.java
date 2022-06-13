package api.productinformation.entity;

import api.productinformation.exception.errorcode.CommonErrorCode;
import api.productinformation.exception.errorcode.ItemErrorCode;
import api.productinformation.exception.handler.InvalidDateTimeFormatException;
import api.productinformation.exception.handler.InvalidItemTypeException;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ItemType {
    NORMAL("일반"),
    CORPORATE("기업회원상품");

    private String value;

    public static ItemType nameOf(String name) {
        for (ItemType value : ItemType.values()) {
            if (value.value.equals(name)) {
                return value;
            }
        }

        throw new InvalidItemTypeException(ItemErrorCode.INVALID_ITEM_TYPE);
    }
}