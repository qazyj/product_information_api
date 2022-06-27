package api.productinformation.service;

import api.productinformation.dto.item.DeliveryItemDto;
import api.productinformation.dto.item.ItemDto;
import api.productinformation.dto.order.OrderItemDto;
import api.productinformation.dto.user.NewUser;
import api.productinformation.dto.user.UserDto;
import api.productinformation.dto.user.UserOrderItemDto;
import api.productinformation.entity.Order;
import api.productinformation.entity.OrderItem;
import api.productinformation.entity.enumType.UserType;
import api.productinformation.entity.User;
import api.productinformation.exception.errorcode.CommonErrorCode;
import api.productinformation.exception.handler.InvalidParameterException;
import api.productinformation.exception.handler.NotFoundResourceException;
import api.productinformation.repository.ItemRepository;
import api.productinformation.repository.OrderItemRepository;
import api.productinformation.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final OrderItemRepository orderItemRepository;

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
    public ResponseEntity<Object> findItemlistById(Long id) {
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

    @Transactional(readOnly = true)
    public ResponseEntity<Object> findOrderlistById(Long id) {
        // id가 null인 경우
        if(Objects.isNull(id)) {
            throw new InvalidParameterException(CommonErrorCode.INVALID_PARAMETER);
        }

        // 많은 데이터를 가져오기 전 User만 조회한 뒤, 유저가 있는지 확인한다. (유저가 없을 경우 큰 쿼리문을 사용하지 않도록 하기 위함)
        User user = userRepository.findById(id).orElseThrow(
                () -> new NotFoundResourceException(CommonErrorCode.NOT_FOUND_RESOURCE));

        // 탈퇴되어 있는 경우 배송조회 불가
        user.withdraw();

        // 쿼리를 2개로 나눈 뒤 (user,order) <-> (orderItem, item) 매핑
        User findUser = userRepository.findOrderlistById(id).get();
        for(Order order : findUser.getOrders()){
            order.setOrderItems(orderItemRepository.findByIdWithItem(order.getId()));
        }

        //dto 변환
        List<OrderItemDto> oid = new ArrayList<>();
        for(Order order : findUser.getOrders()){
            List<DeliveryItemDto> d = new ArrayList<>();
            for(OrderItem orderItem : order.getOrderItems()){
                d.add(DeliveryItemDto.from(orderItem));
            }
            oid.add(OrderItemDto.from(order, d));
        }
        UserOrderItemDto userOrderItemDto = UserOrderItemDto.from(findUser,oid);
        return new ResponseEntity<>(userOrderItemDto, HttpStatus.OK);
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
