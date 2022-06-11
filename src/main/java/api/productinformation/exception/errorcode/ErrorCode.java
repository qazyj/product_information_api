package api.productinformation.exception.errorcode;

import org.springframework.http.HttpStatus;

public interface ErrorCode {
    String name();
    int getStatusCode();
    HttpStatus getHttpStatus();
    String getMessage();
}