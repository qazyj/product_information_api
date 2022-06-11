package api.productinformation.controller;

import api.productinformation.entity.RequestDto;
import api.productinformation.entity.item.ItemDto;
import api.productinformation.entity.user.UserAdd;
import api.productinformation.entity.user.UserDto;
import api.productinformation.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @PostMapping("")
    public ResponseEntity<Object> saveUser(@RequestBody UserAdd userAdd){
        return userService.saveUser(userAdd);
    }

    @DeleteMapping("")
    public ResponseEntity<Object> deleteUser(@RequestBody RequestDto requestDto){

        return userService.deleteUser(requestDto.getId());
    }

    @GetMapping("/itemlist")
    public ResponseEntity<Object> canBuyItemList(@RequestBody RequestDto requestDto){
        return userService.canBuyItemList(requestDto.getId());
    }
}
