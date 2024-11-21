package com.example.hksalarycalculatorbackend.controller;

import com.example.hksalarycalculatorbackend.model.User;
import com.example.hksalarycalculatorbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/createUser")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable String username){
        User user = userService.getUserByUuid(username);
        if(user != null){
            return new ResponseEntity<>(user, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getUserByUsername")
    public ResponseEntity<User> getUserByUsername(@RequestParam String username){
        User user = userService.getUserByUsername(username);
        if(user != null){
            return new ResponseEntity<>(user, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getALlUsers")
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> userList = userService.getAllUsers();
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @PutMapping("/updateUser/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable String username, @RequestBody User updatedUser){
        User user = userService.updateUser(username, updatedUser);
        if(user != null){
            return new ResponseEntity<>(user, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/deleteUser/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable String username){
        userService.deleteUser(username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
