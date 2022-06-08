package api.productinformation.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_item_id")
    private UserItem userItem;

    private String username;

    @Enumerated(EnumType.STRING)
    private Type userType;

    @Enumerated(EnumType.STRING)
    private UserState userState;

    public void setUserItem(UserItem userItem){
        this.userItem = userItem;
    }

    //==생성 메서드==//
    public static User createUser(String username, String userType, UserItem userItem){
        User user = new User();
        user.username = username;
        if(userType.equals("일반"))
            user.userType = Type.NORMAL;
        else
            user.userType = Type.CORPORATE;
        user.userState = UserState.USE;
        user.userItem = userItem;
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
