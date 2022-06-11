package api.productinformation.exception;

import api.productinformation.exception.errorcode.CommonErrorCode;
import api.productinformation.exception.errorcode.ErrorCode;
import api.productinformation.exception.handler.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundResourceException.class)
    public ResponseEntity<Object> handleNotFoundResourceException(NotFoundResourceException e) {
        ErrorCode errorCode = e.getErrorCode();
        return handleExceptionInternal(errorCode);
    }

    @ExceptionHandler(ExitUserException.class)
    public ResponseEntity<Object> handleExitUserException(ExitUserException e) {
        ErrorCode errorCode = e.getErrorCode();
        return handleExceptionInternal(errorCode);
    }

    @ExceptionHandler(InvalidStartdateAfterEnddateException.class)
    public ResponseEntity<Object> handleInvalidStartdateAfterEnddateException(InvalidStartdateAfterEnddateException e) {
        ErrorCode errorCode = e.getErrorCode();
        return handleExceptionInternal(errorCode);
    }

    @ExceptionHandler(InvalidDateTimeFormatException.class)
    public ResponseEntity<Object> handleInvalidDateTimeFormatException(InvalidDateTimeFormatException e) {
        ErrorCode errorCode =e.getErrorCode();
        return handleExceptionInternal(errorCode);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleInvalidParameter(IllegalArgumentException e) {
        ErrorCode errorCode = CommonErrorCode.INVALID_PARAMETER;
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
