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

        // ?????? ????????????
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
        // id??? null??? ??????
        if(Objects.isNull(id)) {
            throw new InvalidParameterException(CommonErrorCode.INVALID_PARAMETER);
        }

        // ?????? ???????????? ???????????? ??? User??? ????????? ???, ????????? ????????? ????????????. (????????? ?????? ?????? ??? ???????????? ???????????? ????????? ?????? ??????)
        User user = userRepository.findById(id).orElseThrow(
                () -> new NotFoundResourceException(CommonErrorCode.NOT_FOUND_RESOURCE));

        // ???????????? ?????? ?????? ???????????? ??????
        user.withdraw();

        // ????????? 2?????? ?????? ??? (user,order) <-> (orderItem, item) ??????
        User findUser = userRepository.findOrderlistById(id).get();
        for(Order order : findUser.getOrders()){
            order.setOrderItems(orderItemRepository.findByIdWithItem(order.getId()));
        }

        //dto ??????
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
     * ?????? ?????? ?????? ?????? ?????? ?????????
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
