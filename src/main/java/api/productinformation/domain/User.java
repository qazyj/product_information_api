package api.productinformation.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
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

    public User(String username, Type userType, UserState userState){
        this.username = username;
        this.userType = userType;
        this.userState = userState;
        this.userItem.addUsers(this);
    }
}
