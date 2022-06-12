package api.productinformation.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NewUser {
    private String userName;
    private String userType;
    private String userState;

    public NewUser(String userName, String userType, String userState) {
        this.userName = userName;
        this.userType = userType;
        this.userState = userState;
    }
}
