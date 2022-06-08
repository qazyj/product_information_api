package api.productinformation.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.*;

/**
 * User Entity
 * Item Entity
 * N:M 매핑 => 1:N & N:1 매핑
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserItem {
    // 1 : 일반 회원
    // 2 : 기업 회원
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_item_id")
    private Long userItemId;

    @OneToMany(mappedBy = "userItem")
    private List<User> users = new ArrayList<>();

    @OneToMany(mappedBy = "userItem")
    private List<Item> items = new ArrayList<>();

    //==연관관계 메서드==//
    public void addUsers(User user){
        this.users.add(user);
        user.setUserItem(this);
    }

    public void addItems(Item item){
        this.items.add(item);
        item.setUserItem(this);
    }
}
