package api.productinformation.dto.user;

import api.productinformation.entity.Type;
import api.productinformation.entity.User;
import api.productinformation.entity.UserState;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private Long userId;
    private String userName;
    private Type userType;
    private UserState userState;

    public String getUserType() {
        if(userType.equals(Type.NORMAL)) {
            return "일반";
        }

        return "기업회원";
    }

    public String getUserState() {
        if(userState.equals(UserState.USE)) {
            return "정상";
        }

        return "탈퇴";
    }

    public static UserDto from(User user) {
        return UserDto.builder()
                .userId(user.getId())
                .userName(user.getUsername())
                .userType(user.getUserType())
                .userState(user.getUserState())
                .build();
    }
}
