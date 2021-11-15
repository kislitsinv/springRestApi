package xyz.vkislitsin.backend.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.vkislitsin.backend.controllers.request.UserAddRequest;
import xyz.vkislitsin.backend.controllers.request.UserGetRequest;
import xyz.vkislitsin.backend.models.UserModel;
import xyz.vkislitsin.backend.services.IUserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
public class UserController {

    private final IUserService iUserService;

    @RequestMapping(value = {"/", "/profile"})
    @ResponseBody
    public ResponseEntity sendViaResponseEntity() {
        return new ResponseEntity(HttpStatus.FORBIDDEN);
    }

    @GetMapping("/users")
    public List<UserModel> showAllUsers(@RequestBody(required = false) UserGetRequest request) throws Exception {
        boolean sort = false;
        if (request != null && request.getSort()) {
            sort = true;
        }
        return iUserService.getAllUsers(sort);
    }

    @PostMapping("/add")
    public UserModel addNewUser(@RequestBody UserAddRequest userAddRequest) throws Exception {
        if (userAddRequest.getUser() == null) {
            throw new Exception("No payload provided: you must transfer the username");
        }
        return iUserService.addNewUser(userAddRequest.getUser());
    }
}
