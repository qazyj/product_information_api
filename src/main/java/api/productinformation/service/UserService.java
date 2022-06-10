package api.productinformation.service;

import api.productinformation.entity.Type;
import api.productinformation.entity.item.ItemDto;
import api.productinformation.entity.user.User;
import api.productinformation.entity.user.UserAdd;
import api.productinformation.entity.user.UserDto;
import api.productinformation.repository.ItemRepository;
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
    private final ItemRepository itemRepository;

    @Transactional
    public UserDto saveUser(UserAdd userAdd){
        User savedUser = userRepository.save(User.createUser(userAdd.getUsername(),
                userAdd.getUserType(), userAdd.getUserState()));

        return new UserDto(savedUser);
    }

    @Transactional
    public void deleteUser(Long id){
        Optional<User> user = userRepository.findById(id);
        userRepository.delete(user.get());
    }

    @Transactional(readOnly = true)
    public List<ItemDto> canBuyItemList(Long id) {
        Optional<User> user = userRepository.findById(id);
        // user null일 경우 예외 처리 해야함
        // 유저가 탈퇴한 경우 예외 처리
        //if(user.get().getUserState().equals(UserState.UNUSE))
        if(user.get().getUserType().equals(Type.NORMAL))
            return itemRepository.findCanBuyItemListByType(Type.NORMAL);
        else
            return itemRepository.findCanBuyItemList();
    }
}
