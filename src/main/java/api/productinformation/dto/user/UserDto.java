package api.productinformation.dto.user;

import api.productinformation.entity.ItemType;
import api.productinformation.entity.UserType;
import api.productinformation.entity.User;
import api.productinformation.entity.UserState;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private Long userId;
    private String userName;
    private String userType;
    private String userState;

    public static UserDto from(User user) {
        return UserDto.builder()
                .userId(user.getId())
                .userName(user.getUserName())
                .userType(user.getUserType().getValue())
                .userState(user.getUserState().getValue())
                .build();
    }
}
