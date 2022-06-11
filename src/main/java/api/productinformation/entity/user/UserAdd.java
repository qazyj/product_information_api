package api.productinformation.entity.user;

import api.productinformation.entity.Type;
import api.productinformation.entity.UserState;
import lombok.Getter;
import lombok.Setter;

@Getter
public class UserAdd {
    private String username;
    private String userType;
    private String userState;

    public UserAdd(String username, String userType, String userState) {
        this.username = username;
        this.userType = userType;
        this.userState = userState;
    }
}
