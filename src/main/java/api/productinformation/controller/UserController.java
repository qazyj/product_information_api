package api.productinformation.controller;

import api.productinformation.entity.user.UserAdd;
import api.productinformation.entity.user.UserSearch;
import api.productinformation.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @PostMapping("")
    public String saveUser(UserAdd userAdd){
        userService.saveUser(userAdd);
        return "ok";
    }

    @DeleteMapping("")
    public String deleteUser(UserSearch userSearch){
        userService.deleteUser(userSearch);
        return "ok";
    }
}
