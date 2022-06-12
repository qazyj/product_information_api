package api.productinformation.service;

import api.productinformation.entity.Type;
import api.productinformation.entity.UserState;
import api.productinformation.entity.user.User;
import api.productinformation.entity.user.UserAdd;
import api.productinformation.entity.user.UserDto;
import api.productinformation.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.Optional;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class UserServiceTest {
    @Autowired EntityManager em;
    @Autowired UserService userService;
    @Autowired UserRepository userRepository;

    @Test
    public void 유저_등록() throws Exception {
        //given
        UserAdd userAdd = new UserAdd("kyj", "일반", "탈퇴");
        UserDto userDto = (UserDto) userService.saveUser(userAdd).getBody();

        //when
        User findUser = userRepository.findById(userDto.getUserId()).get();

        //then
        assertThat(userDto.getUserId()).isEqualTo(findUser.getId());
        assertThat(userAdd.getUsername()).isEqualTo(findUser.getUsername());
        if(findUser.getUserType().equals(Type.NORMAL))
            assertThat(userDto.getUserType()).isEqualTo("일반");
        else
            assertThat(userDto.getUserType()).isEqualTo("기업회원");
        if(findUser.getUserState().equals(UserState.USE))
            assertThat(userDto.getUserState()).isEqualTo("정상");
        else
            assertThat(userDto.getUserState()).isEqualTo("탈퇴");
    }

    @Test
    public void 유저_등록_후_삭제() throws Exception {
        //given
        // InitDB 클래스에서 저장한 userItem
        UserAdd userAdd = new UserAdd("kyj", "일반", "탈퇴");
        UserDto userDto = (UserDto) userService.saveUser(userAdd).getBody();
        userService.deleteUser(userDto.getUserId());

        //when
        Optional<User> findUser = userRepository.findById(userDto.getUserId());

        //then
        assertThat(findUser).isEqualTo(Optional.empty());
    }
}