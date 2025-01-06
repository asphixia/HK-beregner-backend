package com.example.hksalarycalculatorbackend.service;

import com.example.hksalarycalculatorbackend.model.Roles;
import com.example.hksalarycalculatorbackend.model.User;
import com.example.hksalarycalculatorbackend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User getUserByUsername(String username){
        return userRepository.findByUsername(username);
    }

    /*
    public User getUserByUuid(UUID id){
        Optional<User> user = Optional.ofNullable(userRepository.findUserByUUID(id));
        return user.orElse(null);
    }

     */

    public User updateUser(UUID id, User updatedUser) throws Exception {
        return userRepository.findById(id).map(user -> {
            user.setUsername(updatedUser.getUsername());
            user.setRole(updatedUser.getRole());
            user.setEmail(updatedUser.getEmail());
            return userRepository.save(user);
        }).orElseThrow(() -> new Exception("User with ID " + id + " not found."));
    }

    public void deleteUser(String username){
        Optional<User> userOptional = Optional.ofNullable(userRepository.findByUsername(username));
        if(userOptional.isPresent()){
            userRepository.delete(userOptional.get());
        }
    }

    public List<User> getAllUsers(){
        Iterable<User> users = userRepository.findAll();
        List<User> userList = new ArrayList<>();
        users.forEach(userList::add);
        return userList;
    }
    public Roles getUserRole(String username){
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }
        return user.getRole();
    }
}
