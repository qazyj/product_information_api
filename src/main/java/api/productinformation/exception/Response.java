package api.productinformation.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;


@Getter
@Builder
@RequiredArgsConstructor
public class Response {

    private final int statusCode;
    private final HttpStatus httpStatus;
    private final String message;
}
