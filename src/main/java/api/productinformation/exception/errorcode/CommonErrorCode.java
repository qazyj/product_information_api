package api.productinformation.exception.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommonErrorCode implements ErrorCode{

    OK(200, HttpStatus.OK, "Success"),

    NOT_FOUND_RESOURCE(404, HttpStatus.NOT_FOUND, "Resource Not Exist"),

    INVALID_PARAMETER(400,HttpStatus.BAD_REQUEST, "Invalid parameter included"),

    INVALID_DATETIME_FORMAT(400,HttpStatus.BAD_REQUEST, "Check Date Format (yyyy.M.d)"),

    INVALID_STARTDATE_AFTER_ENDDATE(400,HttpStatus.BAD_REQUEST, "Check StartDate After EndDate"),

    INTERNAL_SERVER_ERROR(500, HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");

    private final int statusCode;
    private final HttpStatus httpStatus;
    private final String message;
}
