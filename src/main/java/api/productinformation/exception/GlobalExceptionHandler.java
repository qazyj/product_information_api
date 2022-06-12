package api.productinformation.exception;

import api.productinformation.exception.errorcode.CommonErrorCode;
import api.productinformation.exception.errorcode.ErrorCode;
import api.productinformation.exception.handler.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(InvalidItemTypeException.class)
    public ResponseEntity<Object> handleInvalidItemTypeException(InvalidItemTypeException e) {
        ErrorCode errorCode = e.getErrorCode();
        log.error("InvalidItemTypeException", e);
        return handleExceptionInternal(errorCode);
    }

    @ExceptionHandler(InvalidUserStateException.class)
    public ResponseEntity<Object> handleInvalidUserStateException(InvalidUserStateException e) {
        ErrorCode errorCode = e.getErrorCode();
        log.error("InvalidUserStateException", e);
        return handleExceptionInternal(errorCode);
    }

    @ExceptionHandler(InvalidUserTypeException.class)
    public ResponseEntity<Object> handleInvalidUserTypeException(InvalidUserTypeException e) {
        ErrorCode errorCode = e.getErrorCode();
        log.error("InvalidUserTypeException", e);
        return handleExceptionInternal(errorCode);
    }

    @ExceptionHandler(NotFoundResourceException.class)
    public ResponseEntity<Object> handleNotFoundResourceException(NotFoundResourceException e) {
        ErrorCode errorCode = e.getErrorCode();
        log.error("NotFoundResourceException", e);
        return handleExceptionInternal(errorCode);
    }

    @ExceptionHandler(ExitUserException.class)
    public ResponseEntity<Object> handleExitUserException(ExitUserException e) {
        ErrorCode errorCode = e.getErrorCode();
        log.error("ExitUserException", e);
        return handleExceptionInternal(errorCode);
    }

    @ExceptionHandler(InvalidStartdateAfterEnddateException.class)
    public ResponseEntity<Object> handleInvalidStartdateAfterEnddateException(InvalidStartdateAfterEnddateException e) {
        ErrorCode errorCode = e.getErrorCode();
        log.error("InvalidStartdateAfterEnddateException", e);
        return handleExceptionInternal(errorCode);
    }

    @ExceptionHandler(InvalidDateTimeFormatException.class)
    public ResponseEntity<Object> handleInvalidDateTimeFormatException(InvalidDateTimeFormatException e) {
        ErrorCode errorCode =e.getErrorCode();
        log.error("InvalidDateTimeFormatException", e);
        return handleExceptionInternal(errorCode);
    }

    @ExceptionHandler(InvalidParameterException.class)
    public ResponseEntity<Object> handleInvalidParameter(InvalidParameterException e) {
        ErrorCode errorCode = e.getErrorCode();
        log.error("InvalidParameterException", e);
        return handleExceptionInternal(errorCode);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleAllException(Exception ex) {
        ErrorCode errorCode = CommonErrorCode.INTERNAL_SERVER_ERROR;
        return handleExceptionInternal(errorCode);
    }

    private ResponseEntity<Object> handleExceptionInternal(ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(makeErrorResponse(errorCode));
    }

    private Response makeErrorResponse(ErrorCode errorCode) {
        return Response.builder()
                .httpStatus(errorCode.getHttpStatus())
                .statusCode(errorCode.getStatusCode())
                .message(errorCode.getMessage())
                .build();
    }
}
