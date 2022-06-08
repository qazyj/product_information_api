package api.productinformation.controller;

import api.productinformation.entity.user.UserAdd;
import api.productinformation.service.UserService;
import lombok.RequiredArgsConstructor;
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
}
