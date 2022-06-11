package api.productinformation.exception.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum PromotionErrorCode implements ErrorCode {
    ;

    private final int statusCode;
    private final HttpStatus httpStatus;
    private final String message;
}
