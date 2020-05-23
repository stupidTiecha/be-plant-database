package com.plant.database.api;

import com.plant.database.model.bean.Response;
import com.plant.database.model.bean.User;
import com.plant.database.model.dto.RegisterReq;
import com.plant.database.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * UserApi
 *
 * @author chenjingyu
 * @date 2020/5/16
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("user")
public class UserApi {

    private final UserService userService;

    public UserApi(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("login")
    public Response logIn (@RequestParam("userName") String userName, @RequestParam("password") String password) {
       User user = userService.logIn(userName,password);
     return new Response(user);
    }

    @PostMapping("register")
    public Response register (@RequestBody RegisterReq registerReq) {
        User user = userService.register(registerReq);
        return new Response(user);
    }
}
