package api.productinformation.entity;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class UserTest {
    @Autowired EntityManager em;
    
    @Test
    public void 유저_엔티티_등록() throws Exception {
        //given

        //when
        User user = User.createUser("aa", UserType.NORMAL, UserState.USE);

        //then
        assertThat(user.getUserType()).isEqualTo(UserType.NORMAL);
        assertThat(user.getUserName()).isEqualTo("aa");
        assertThat(user.getUserState()).isEqualTo(UserState.USE);
    }

    @Test
    public void 유저_탈퇴() throws Exception {
        //given
        User user = User.createUser("aa", UserType.NORMAL, UserState.USE);
        em.persist(user);
        user.withdraw();
        em.flush();
        em.clear();

        //when
        User findUser = em.find(User.class, user.getId());

        //then
        assertThat(user.getUserState()).isEqualTo(UserState.UNUSE);
        assertThat(findUser.getUserState()).isEqualTo(UserState.UNUSE);

    }
}