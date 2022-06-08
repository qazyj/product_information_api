package api.productinformation.service;

import api.productinformation.entity.UserItem;
import api.productinformation.entity.user.User;
import api.productinformation.entity.user.UserAdd;
import api.productinformation.entity.user.UserSearch;
import api.productinformation.repository.UserItemRepository;
import api.productinformation.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserItemRepository userItemRepository;

    @Transactional
    public void saveUser(UserAdd userAdd){
        Optional<UserItem> findUserItem = getUserItem(userAdd);
        User user = User.createUser(userAdd.getUsername(), userAdd.getUserType(), findUserItem.get());
        userRepository.save(user);
    }

    @Transactional
    public void deleteUser(UserSearch userSearch){
        Optional<User> user = userRepository.findById(userSearch.getId());
        userRepository.delete(user.get());
    }

    private Optional<UserItem> getUserItem(UserAdd userAdd) {
        if(userAdd.getUserType().equals("일반"))
            return userItemRepository.findById(1L);
        else
            return userItemRepository.findById(2L);
    }
}
