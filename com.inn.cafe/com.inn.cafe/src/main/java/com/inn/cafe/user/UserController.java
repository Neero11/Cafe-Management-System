package com.inn.cafe.user;

import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
    private  final UserService userService;
    private final PasswordEncoder passwordEncoder;
    @GetMapping
    public ResponseEntity<List<UserModel>> getAll(){

        return ResponseEntity.ok(userService.findAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<UserModel> getUser(@PathVariable String id){
        return ResponseEntity.ok(userService.findById(id));
    }
    @PostMapping("/user")
    public ResponseEntity<UserModel>  createUser(@RequestBody UserModel user){
     user.setPassword(passwordEncoder.encode(user.getPassword()));
        return  ResponseEntity.ok(userService.save(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserModel>  update(@PathVariable String id  , @RequestBody UserModel user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return  ResponseEntity.ok(userService.update(id, user));
    }
    @DeleteMapping("/{id}")
    public  ResponseEntity<UserModel> delete(@PathVariable String id){
        return  ResponseEntity.ok(userService.delete(id));
    }
}
