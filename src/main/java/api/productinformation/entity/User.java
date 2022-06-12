package api.productinformation.entity;

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

    private String username;

    @Enumerated(EnumType.STRING)
    private Type userType;

    @Enumerated(EnumType.STRING)
    private UserState userState;


    //==생성 메서드==//
    public static User createUser(String username, String userType, String userState){
        User user = new User();
        user.username = username;
        if(userType.equals("일반"))
            user.userType = Type.NORMAL;
        else
            user.userType = Type.CORPORATE;

        if(userState.equals("정상"))
            user.userState = UserState.USE;
        else
            user.userState = UserState.UNUSE;
        return user;
    }

    //==비즈니스 로직==//
    /**
     * 회원 탈퇴
     */
    public void withdraw(){
        if(this.userState == UserState.UNUSE){
            throw new IllegalStateException("이미 탈퇴한 회원 입니다.");
        }

        this.userState = UserState.UNUSE;
    }
}
