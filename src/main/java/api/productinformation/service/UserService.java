package api.productinformation.service;

import api.productinformation.dto.item.ItemDto;
import api.productinformation.dto.user.NewUser;
import api.productinformation.dto.user.UserDto;
import api.productinformation.entity.enumType.UserType;
import api.productinformation.entity.User;
import api.productinformation.exception.errorcode.CommonErrorCode;
import api.productinformation.exception.handler.InvalidParameterException;
import api.productinformation.exception.handler.NotFoundResourceException;
import api.productinformation.repository.ItemRepository;
import api.productinformation.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    public ResponseEntity<Object> saveUser(NewUser newUser){
        checkArgsIsNull(newUser);

        // UserState - String -> UserState eunm
        newUser.StringToUserState();

        // UserType - String -> UserType eunm
        newUser.StringToUserType();

        User savedUser = userRepository.save(User.createUser(newUser.getUserName(),
                newUser.getRealUserType(), newUser.getRealUserState(), newUser.getAddress()));

        return new ResponseEntity<>(UserDto.from(savedUser), HttpStatus.OK);
    }

    public ResponseEntity<Object> deleteUser(Long id){
        if(Objects.isNull(id)) {
            throw new InvalidParameterException(CommonErrorCode.INVALID_PARAMETER);
        }

        User user = userRepository.findById(id).orElseThrow(
                () -> new NotFoundResourceException(CommonErrorCode.NOT_FOUND_RESOURCE));

        userRepository.delete(user);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Object> canBuyItemList(Long id) {
        if(Objects.isNull(id)) {
            throw new InvalidParameterException(CommonErrorCode.INVALID_PARAMETER);
        }

        User user = userRepository.findById(id).orElseThrow(
                () -> new NotFoundResourceException(CommonErrorCode.NOT_FOUND_RESOURCE));

        // 탈퇴 예외처리
        user.withdraw();

        if(user.getUserType().equals(UserType.NORMAL)) {
            List<ItemDto> result = itemRepository.findCanBuyItemListByType(UserType.NORMAL).stream()
                    .map(ItemDto::from).collect(Collectors.toList());
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        else {
            List<ItemDto> result = itemRepository.findCanBuyItemList().stream()
                    .map(ItemDto::from).collect(Collectors.toList());
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }

    /**
     * 입력 받지 못한 컬럼 값이 있는지
     * @param newUser
     */
    private void checkArgsIsNull(NewUser newUser) {
        if(Objects.isNull(newUser.getUserState()) ||
                Objects.isNull(newUser.getUserType()) ||
                Objects.isNull(newUser.getUserName())){

            throw new InvalidParameterException(CommonErrorCode.INVALID_PARAMETER);
        }
    }
}
