package api.productinformation.exception.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum OrderErrorCode  implements ErrorCode {

    INVALID_CANCEL(400, HttpStatus.BAD_REQUEST,"Items in delivery cannot be canceled");

    private final int statusCode;
    private final HttpStatus httpStatus;
    private final String message;
}