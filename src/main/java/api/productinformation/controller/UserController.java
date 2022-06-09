package api.productinformation.controller;

import api.productinformation.entity.item.ItemDto;
import api.productinformation.entity.user.User;
import api.productinformation.entity.user.UserAdd;
import api.productinformation.entity.user.UserDto;
import api.productinformation.entity.user.UserSearch;
import api.productinformation.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @PostMapping("")
    public UserDto saveUser(UserAdd userAdd){
        return userService.saveUser(userAdd);
    }

    @DeleteMapping("")
    public String deleteUser(UserSearch userSearch){
        userService.deleteUser(userSearch);
        return "ok";
    }
}
