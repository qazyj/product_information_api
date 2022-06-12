package api.productinformation.dto.user;

import api.productinformation.entity.ItemType;
import api.productinformation.entity.UserState;
import api.productinformation.entity.UserType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@NoArgsConstructor
public class NewUser {
    private String userName;
    private String userType;
    private String userState;

    @Enumerated(EnumType.STRING)
    private UserType realUserType;
    @Enumerated(EnumType.STRING)
    private UserState realUserState;

    public NewUser(String userName, String userType, String userState) {
        this.userName = userName;
        this.userType = userType;
        this.userState = userState;
    }

    public void StringToUserType() {
        this.realUserType = UserType.nameOf(userType);
    }

    public void StringToUserState() {
        this.realUserState = UserState.nameOf(userState);
    }
}
