package api.productinformation.exception.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {

    EXIT_USER(400, HttpStatus.BAD_REQUEST,"ExitingUser can't search the item");

    private final int statusCode;
    private final HttpStatus httpStatus;
    private final String message;
}
