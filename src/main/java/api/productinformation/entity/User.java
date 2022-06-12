package api.productinformation.entity;

import api.productinformation.exception.errorcode.UserErrorCode;
import api.productinformation.exception.handler.ExitUserException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String userName;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    @Enumerated(EnumType.STRING)
    private UserState userState;


    //==생성 메서드==//
    public static User createUser(String userName, UserType userType, UserState userState){
        User user = new User();
        user.userName = userName;
        user.userType = userType;
        user.userState = userState;
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
    }
}
