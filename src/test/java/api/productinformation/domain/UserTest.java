package api.productinformation.domain;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(false)
class UserTest {
    @Autowired EntityManager em;


    @Test
    public void 유저_PK값_테스트() {
        UserItem useritem1 = new UserItem();
        UserItem useritem2 = new UserItem();
        UserItem useritem3 = new UserItem();
        UserItem useritem4 = new UserItem();
        em.persist(useritem1);
        em.persist(useritem2);
        em.persist(useritem3);
        em.persist(useritem4);


        UserItem findUserItem = em.find(UserItem.class, 1L);
        User user1 = User.createUser("aa", "일반", findUserItem);
        User user2 = User.createUser("aab", "일반", findUserItem);
        User user3 = User.createUser("aab", "일반", findUserItem);
        User user4 = User.createUser("aab", "일반", findUserItem);

        UserItem useritem5 = new UserItem();
        em.persist(useritem5);


        em.persist(user1);
        em.persist(user2);
        em.persist(user3);
        em.persist(user4);
        User user5 = User.createUser("aab", "일반", findUserItem);
        em.persist(user5);
    }

    @Test
    public void 유저_엔티티_등록() throws Exception {
        //given
        UserItem useritem = new UserItem();
        em.persist(useritem);
        em.flush();
        em.clear();

        //when
        UserItem findUserItem = em.find(UserItem.class, 1L);
        User user = User.createUser("aa", "일반", findUserItem);

        //then
        assertThat(user.getUserType()).isEqualTo(Type.NORMAL);
        assertThat(user.getUsername()).isEqualTo("aa");
        assertThat(user.getUserState()).isEqualTo(UserState.USE);
    }

    @Test
    public void 유저_탈퇴() throws Exception {
        //given
        UserItem useritem = new UserItem();
        em.persist(useritem);
        em.flush();
        em.clear();

        //when
        UserItem findUserItem = em.find(UserItem.class, 1L);
        User user = User.createUser("aa", "일반", findUserItem);
        em.persist(user);
        user.withdraw();
        em.flush();
        em.clear();
        User findUser = em.find(User.class, 2L);

        //then
        assertThat(user.getUserState()).isEqualTo(UserState.UNUSE);
        assertThat(findUser.getUserState()).isEqualTo(UserState.UNUSE);

    }

    @Test
    public void 유저_엔티티_유저아이템_엔티티_연관관계() {
        //given
        UserItem userItem = new UserItem();
        UserItem userItem2 = new UserItem();
        em.persist(userItem);
        em.persist(userItem2);
        em.flush();
        em.clear();

        //when
        UserItem findUserItem = em.find(UserItem.class, 1L);
        User user = User.createUser("aa", "일반", findUserItem);

        //then
        assertThat(user.getUserItem().getUserItemId()).isEqualTo(findUserItem.getUserItemId());
    }

    @Test
    public void 유저_유저아이템_아이템_엔티티_연관관계() throws Exception {
        //given
        UserItem userItem = new UserItem();
        UserItem userItem2 = new UserItem();
        em.persist(userItem);
        em.persist(userItem2);
        em.flush();
        em.clear();

        //when
        UserItem findUserItem = em.find(UserItem.class, 1L);
        User user = User.createUser("aa", "일반", findUserItem);
        Item item = Item.createItem("bb", "일반", 20000L, findUserItem,
                LocalDate.of(2022,1,1),
                LocalDate.of(2023,1,1));

        System.out.println("localdate = " + item.getStartDate());
        List<Item> items = user.getUserItem().getItems();
        List<User> users = item.getUserItem().getUsers();

        //then
        assertThat(items.size()).isEqualTo(1);
        assertThat(users.size()).isEqualTo(1);
    }
}