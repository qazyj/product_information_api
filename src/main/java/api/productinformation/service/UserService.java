package api.productinformation.service;

import api.productinformation.entity.Type;
import api.productinformation.entity.UserState;
import api.productinformation.entity.item.ItemDto;
import api.productinformation.entity.user.User;
import api.productinformation.entity.user.UserAdd;
import api.productinformation.entity.user.UserDto;
import api.productinformation.exception.errorcode.CommonErrorCode;
import api.productinformation.exception.errorcode.UserErrorCode;
import api.productinformation.exception.handler.ExitUserException;
import api.productinformation.exception.handler.NotFoundResourceException;
import api.productinformation.repository.ItemRepository;
import api.productinformation.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    public ResponseEntity<Object> saveUser(UserAdd userAdd){

        User savedUser = userRepository.save(User.createUser(userAdd.getUsername(),
                userAdd.getUserType(), userAdd.getUserState()));

        return new ResponseEntity<>(new UserDto(savedUser), HttpStatus.OK);
    }

    public ResponseEntity<Object> deleteUser(Long id){
        User user = userRepository.findById(id).orElseThrow(
                () -> new NotFoundResourceException(CommonErrorCode.NOT_FOUND_RESOURCE));

        userRepository.delete(user);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Object> canBuyItemList(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new NotFoundResourceException(CommonErrorCode.NOT_FOUND_RESOURCE));

        if (user.getUserState() == UserState.UNUSE) {
            throw new ExitUserException(UserErrorCode.EXIT_USER);
        }

        if(user.getUserType().equals(Type.NORMAL))
            return new ResponseEntity<>(itemRepository.findCanBuyItemListByType(Type.NORMAL), HttpStatus.OK);
        else
            return new ResponseEntity<>(itemRepository.findCanBuyItemList(), HttpStatus.OK);
    }
}
