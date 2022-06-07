package api.productinformation.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.*;

@Entity
@Getter
public class UserItem {
    // 0 : 일반 회원
    // 1 : 기업 회원
    @Id
    @GeneratedValue
    @Column(name = "user_item_id")
    private Long userItemId;

    @OneToMany(mappedBy = "userItem")
    private List<User> users = new ArrayList<>();

    @OneToMany(mappedBy = "userItem")
    private List<Item> items = new ArrayList<>();
}
