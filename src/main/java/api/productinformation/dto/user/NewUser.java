package api.productinformation.dto.user;

import api.productinformation.entity.Address;
import api.productinformation.entity.enumType.UserState;
import api.productinformation.entity.enumType.UserType;
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
    private Address address;

    @Enumerated(EnumType.STRING)
    private UserType realUserType;
    @Enumerated(EnumType.STRING)
    private UserState realUserState;

    public NewUser(String userName, String userType, String userState, String city, String street, String zipcode) {
        this.userName = userName;
        this.userType = userType;
        this.userState = userState;
        this.address = new Address(city, street, zipcode);
    }

    public void StringToUserType() {
        this.realUserType = UserType.nameOf(userType);
    }

    public void StringToUserState() {
        this.realUserState = UserState.nameOf(userState);
    }
}
