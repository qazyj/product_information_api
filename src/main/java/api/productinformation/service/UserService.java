package api.productinformation.service;

import api.productinformation.dto.user.NewUser;
import api.productinformation.dto.user.UserDto;
import api.productinformation.entity.UserType;
import api.productinformation.entity.UserState;
import api.productinformation.entity.User;
import api.productinformation.exception.errorcode.CommonErrorCode;
import api.productinformation.exception.errorcode.UserErrorCode;
import api.productinformation.exception.handler.ExitUserException;
import api.productinformation.exception.handler.InvalidParameterException;
import api.productinformation.exception.handler.NotFoundResourceException;
import api.productinformation.repository.ItemRepository;
import api.productinformation.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    public ResponseEntity<Object> saveUser(NewUser newUser){
        checkArgsIsNull(newUser);
        newUser.StringToUserState();
        newUser.StringToUserType();

        User savedUser = userRepository.save(User.createUser(newUser.getUserName(),
                newUser.getRealUserType(), newUser.getRealUserState()));

        return new ResponseEntity<>(UserDto.from(savedUser), HttpStatus.OK);
    }

    public ResponseEntity<Object> deleteUser(Long id){
        if(id==null) throw new InvalidParameterException(CommonErrorCode.INVALID_PARAMETER);

        User user = userRepository.findById(id).orElseThrow(
                () -> new NotFoundResourceException(CommonErrorCode.NOT_FOUND_RESOURCE));

        userRepository.delete(user);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Object> canBuyItemList(Long id) {
        if(id==null) throw new InvalidParameterException(CommonErrorCode.INVALID_PARAMETER);

        User user = userRepository.findById(id).orElseThrow(
                () -> new NotFoundResourceException(CommonErrorCode.NOT_FOUND_RESOURCE));

        // 탈퇴 예외처리
        user.withdraw();

        if(user.getUserType().equals(UserType.NORMAL))
            return new ResponseEntity<>(itemRepository.findCanBuyItemListByType(UserType.NORMAL), HttpStatus.OK);
        else
            return new ResponseEntity<>(itemRepository.findCanBuyItemList(), HttpStatus.OK);
    }

    private void checkArgsIsNull(NewUser newUser) {
        if(newUser.getUserState() == null ||
                newUser.getUserType() == null ||
                newUser.getUserName() == null) {
            throw new InvalidParameterException(CommonErrorCode.INVALID_PARAMETER);
        }
    }
}
