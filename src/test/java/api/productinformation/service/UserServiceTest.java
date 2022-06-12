package api.productinformation.service;

import api.productinformation.entity.Type;
import api.productinformation.entity.UserState;
import api.productinformation.entity.User;
import api.productinformation.dto.user.*;
import api.productinformation.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
        NewUser newUser = new NewUser("kyj", "일반", "탈퇴");
        UserDto userDto = (UserDto) userService.saveUser(newUser).getBody();

        //when
        User findUser = userRepository.findById(userDto.getUserId()).get();

        //then
        assertThat(userDto.getUserId()).isEqualTo(findUser.getId());
        assertThat(newUser.getUsername()).isEqualTo(findUser.getUsername());
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
        NewUser newUser = new NewUser("kyj", "일반", "탈퇴");
        UserDto userDto = (UserDto) userService.saveUser(newUser).getBody();
        userService.deleteUser(userDto.getUserId());

        //when
        Optional<User> findUser = userRepository.findById(userDto.getUserId());

        //then
        assertThat(findUser).isEqualTo(Optional.empty());
    }
}