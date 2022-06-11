package api.productinformation.entity.user;

import api.productinformation.entity.Type;
import api.productinformation.entity.UserState;
import lombok.Data;
import lombok.Getter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
public class UserDto {
    private Long userId;
    private String username;
    private String userType;
    private String userState;

    public UserDto(Long userId, String username, String userType, String userState) {
        this.userId = userId;
        this.username = username;
        this.userType = userType;
        this.userState = userState;
    }

    public UserDto(User user){
        this.userId = user.getId();
        this.username = user.getUsername();
        if(user.getUserType().equals(Type.NORMAL))
            this.userType = "일반";
        else
            this.userType = "기업회원";
        if(user.getUserState().equals(UserState.USE))
            this.userState = "정상";
        else
            this.userState = "탈퇴";
    }
}
