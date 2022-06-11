package api.productinformation.controller;

import api.productinformation.entity.item.ItemDto;
import api.productinformation.entity.user.UserAdd;
import api.productinformation.entity.user.UserDto;
import api.productinformation.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @PostMapping("")
    public ResponseEntity<Object> saveUser(UserAdd userAdd){
        return userService.saveUser(userAdd);
    }

    @DeleteMapping("/{user_id}")
    public ResponseEntity<Object> deleteUser(@PathVariable("user_id") Long id){

        return userService.deleteUser(id);
    }

    @GetMapping("/itemlist/{user_id}")
    public ResponseEntity<Object> canBuyItemList(@PathVariable("user_id") Long id){
        return userService.canBuyItemList(id);
    }
}
