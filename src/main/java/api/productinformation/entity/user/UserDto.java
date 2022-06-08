package api.productinformation.entity.user;

import api.productinformation.entity.Type;
import api.productinformation.entity.UserState;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
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
        this.userType = user.getUserType().toString();
        this.userState = user.getUserState().toString();
    }
}