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

    @GetMapping("/itemlist/{id}")
    public ResponseEntity<Object> findItemlistById(@RequestParam("id") Long id){

        return userService.findItemlistById(id);
    }

    @GetMapping("/orderlist/{id}")
    public ResponseEntity<Object> findOrderlistById(@RequestParam("id") Long id){
        return userService.findOrderlistById(id);
    }
}
