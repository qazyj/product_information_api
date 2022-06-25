package api.productinformation.exception.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ItemErrorCode implements ErrorCode {

    INVALID_ITEM_TYPE(400, HttpStatus.BAD_REQUEST,"itemType is available only to 일반 and 기업회원상품"),
    INVALID_ITEM_STOCKQUANTITY(400, HttpStatus.BAD_REQUEST, "item's quantity is not enough");

    private final int statusCode;
    private final HttpStatus httpStatus;
    private final String message;
}
