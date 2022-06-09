package api.productinformation.service;

import api.productinformation.entity.item.ItemDto;
import api.productinformation.entity.user.User;
import api.productinformation.entity.user.UserAdd;
import api.productinformation.entity.user.UserDto;
import api.productinformation.entity.user.UserSearch;
import api.productinformation.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public UserDto saveUser(UserAdd userAdd){
        User savedUser = userRepository.save(User.createUser(userAdd.getUsername(),
                userAdd.getUserType(), userAdd.getUserState()));

        return new UserDto(savedUser);
    }

    @Transactional
    public void deleteUser(UserSearch userSearch){
        Optional<User> user = userRepository.findById(userSearch.getId());
        userRepository.delete(user.get());
    }

    @Transactional(readOnly = true)
    public List<ItemDto> canBuyItemList() {
        return null;
    }
}
