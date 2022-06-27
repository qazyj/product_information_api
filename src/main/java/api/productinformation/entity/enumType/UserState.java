package api.productinformation.entity.enumType;

import api.productinformation.exception.errorcode.UserErrorCode;
import api.productinformation.exception.handler.InvalidUserStateException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserState {
    USE("정상"),
    UNUSE("탈퇴");

    private String value;

    public static UserState nameOf(String name) {
        for (UserState value : UserState.values()) {
            if (value.value.equals(name)) {
                return value;
            }
        }

        throw new InvalidUserStateException(UserErrorCode.INVALID_USER_STATE);
    }
}
