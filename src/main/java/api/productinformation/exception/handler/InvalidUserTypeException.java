package api.productinformation.exception.handler;

import api.productinformation.exception.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class InvalidUserTypeException extends RuntimeException{

    private final ErrorCode errorCode;
}
