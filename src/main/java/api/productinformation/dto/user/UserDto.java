package api.productinformation.dto.user;

import api.productinformation.entity.Address;
import api.productinformation.entity.User;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private Long userId;
    private String userName;
    private String userType;
    private String userState;
    private Address userAddress;

    public static UserDto from(User user) {
        return UserDto.builder()
                .userId(user.getId())
                .userName(user.getUserName())
                .userType(user.getUserType().getValue())
                .userState(user.getUserState().getValue())
                .userAddress(user.getAddress())
                .build();
    }
}
