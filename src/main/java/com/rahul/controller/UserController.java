package com.rahul.controller;

import com.rahul.model.User;
import com.rahul.response.MessageResponse;
import com.rahul.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<User> findUserByJwtToken(@RequestHeader("Authorization") String jwt) throws Exception {
        User user=userService.findUserByJwtToken(jwt);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<MessageResponse> deleteUser(@RequestHeader("Authorization") String jwt,
                                                            @PathVariable Long id) throws Exception {
        User user=userService.findUserByJwtToken(jwt);
        userService.deleteUser(id);
        MessageResponse res= new MessageResponse();
        res.setMessage("User deleted successfully");
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
