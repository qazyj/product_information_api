package api.productinformation.entity.user;

import api.productinformation.entity.Type;
import api.productinformation.entity.UserState;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAdd {
    private String username;
    private Type userType;
    private UserState userState;

    public UserAdd(String username, Type userType, UserState userState) {
        this.username = username;
        this.userType = userType;
        this.userState = userState;
    }
}
