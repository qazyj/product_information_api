package api.productinformation.service;

import api.productinformation.entity.user.User;
import api.productinformation.entity.user.UserAdd;
import api.productinformation.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public void saveUser(UserAdd userAdd){
        User user = User.createUser(userAdd.getUsername(), userAdd.getUserType(), userAdd.getUserState());
        userRepository.save(user);
    }
}
