package api.productinformation.exception.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {

    INVALID_USER_TYPE(400, HttpStatus.BAD_REQUEST,"userType is available only to 일반 and 기업회원"),
    INVALID_USER_STATE(400, HttpStatus.BAD_REQUEST,"userState is available only to 정상 and 탈퇴"),

    EXIT_USER(400, HttpStatus.BAD_REQUEST,"ExitingUser don't work.");

    private final int statusCode;
    private final HttpStatus httpStatus;
    private final String message;
}
