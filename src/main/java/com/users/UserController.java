package com.users;

import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {
	
	private UserService userService;
	
	UserController(UserService userService) {
		this.userService = userService;
	}
	
	@PostMapping("/save")
    public ResponseEntity<UserModel> saveUser(@RequestBody @Valid UserDto userDto) {
        var userModel = new UserModel();
        BeanUtils.copyProperties(userDto, userModel);
        userModel.setPassword(new BCryptPasswordEncoder().encode(userDto.getPassword()));
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(userModel));
    }
}
