package api.productinformation.controller;

import api.productinformation.dto.RequestDto;
import api.productinformation.dto.user.NewUser;
import api.productinformation.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @PostMapping("")
    public ResponseEntity<Object> saveUser(@RequestBody NewUser newUser){
        return userService.saveUser(newUser);
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
