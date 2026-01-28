package com.world.controller;

import com.world.dto.request.EnterRequest;
import com.world.dto.request.SelectRequest;
import com.world.dto.response.*;
import com.world.entity.User;
import com.world.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/enter")
@AllArgsConstructor
public class EnterController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<ResponseContainer<UserResponse>> enterUser(@RequestBody EnterRequest request) {
        User user = userService.enterUser(request);
        UserResponse response = new UserResponse(
            user.getUsername(),
            user.getRole(),
            request.getCountryEnum()
        );

        return ResponseEntity.ok(new ResponseContainer<>(response));
    }

    @PostMapping("/country")
    public ResponseEntity<ResponseContainer<String>> selectCountry(
        @RequestBody SelectRequest request) {

        userService.selectCountry(request);
        return ResponseEntity.ok(new ResponseContainer<>("Страна успешно выбрана"));
    }
}

