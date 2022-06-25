package api.productinformation.entity.enumType;

import api.productinformation.exception.errorcode.UserErrorCode;
import api.productinformation.exception.handler.InvalidUserTypeException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserType {
    NORMAL("일반"),
    CORPORATE("기업회원");

    private String value;

    public static UserType nameOf(String name) {
        for (UserType value : UserType.values()) {
            if (value.value.equals(name)) {
                return value;
            }
        }

        throw new InvalidUserTypeException(UserErrorCode.INVALID_USER_TYPE);
    }
}
