package api.productinformation.entity;

import api.productinformation.entity.enumType.UserState;
import api.productinformation.entity.enumType.UserType;
import api.productinformation.exception.errorcode.UserErrorCode;
import api.productinformation.exception.handler.ExitUserException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @OneToMany
    private List<Order> orders = new ArrayList<>();

    private String userName;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    @Enumerated(EnumType.STRING)
    private UserState userState;

    @Embedded
    private Address address;

    //==생성 메서드==//
    public static User createUser(String userName, UserType userType, UserState userState, Address address){
        User user = new User();
        user.userName = userName;
        user.userType = userType;
        user.userState = userState;
        user.address = address;
        return user;
    }

    //==비즈니스 로직==//
    /**
     * 회원 탈퇴
     */
    public void withdraw(){
        if(this.userState == UserState.UNUSE){
            throw new ExitUserException(UserErrorCode.EXIT_USER);
        }

        this.userState = UserState.UNUSE;
    }
}
