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

    private String username;

    @Enumerated(EnumType.STRING)
    private Type userType;

    @Enumerated(EnumType.STRING)
    private UserState userState;
}
