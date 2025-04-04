package com.rahul.controller;

import com.rahul.config.JwtProvider;
import com.rahul.model.Cart;
import com.rahul.model.PasswordResetToken;
import com.rahul.model.USER_ROLE;
import com.rahul.model.User;
import com.rahul.repository.CartRepository;
import com.rahul.repository.UserRepository;
import com.rahul.request.LoginRequest;
import com.rahul.request.PasswordResetRequest;
import com.rahul.response.ApiResponse;
import com.rahul.response.AuthResponse;
import com.rahul.service.CustomerUserDetailsService;
import com.rahul.service.PasswordResetTokenService;
import com.rahul.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private CustomerUserDetailsService customerUserDetailsService;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private PasswordResetTokenService passwordResetTokenService;
    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws Exception {
        User isEmailExit=userRepository.findByEmail(user.getEmail());
        if(isEmailExit!=null){
            throw new Exception("email is laready used with another account");
        }
        User newUser= new User();
        newUser.setEmail(user.getEmail());
        newUser.setFullname(user.getFullname());
        newUser.setRole(user.getRole());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser=userRepository.save(newUser);

        Cart cart= new Cart();
        cart.setCustomer(savedUser);
        cartRepository.save(cart);

        Authentication authentication= new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt=jwtProvider.generateToken(authentication);

        AuthResponse authResponse= new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("Register success");
        authResponse.setRole(savedUser.getRole());

        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse>signin(@RequestBody LoginRequest req){
        String username=req.getEmail();
        String password=req.getPassword();

        Authentication authentication=authenticate(username, password);

        Collection<? extends GrantedAuthority> authorities=authentication.getAuthorities();
        String role=authorities.isEmpty()?null:authorities.iterator().next().getAuthority();

        String jwt=jwtProvider.generateToken(authentication);

        AuthResponse authResponse= new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("Login success");
        authResponse.setRole(USER_ROLE.valueOf(role));

        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    private Authentication authenticate(String username, String password) {
        UserDetails userDetails=customerUserDetailsService.loadUserByUsername(username);
        if(userDetails==null){
            throw new BadCredentialsException("Invalid user...");
        }
        if(!passwordEncoder.matches(password, userDetails.getPassword())){
            throw new BadCredentialsException("Invalid password...");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

    }

    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponse> resetPassword(@RequestBody PasswordResetRequest passwordResetRequest) throws Exception{

        PasswordResetToken passwordResetToken = passwordResetTokenService.findByToken(passwordResetRequest.getToken());
        if(passwordResetToken==null){
            throw new Exception("Token is Null");
        }
        if(passwordResetToken.isExpired()){
            passwordResetTokenService.delete(passwordResetToken);
            throw new Exception("Token get Expired___");
        }

        User user = passwordResetToken.getUser();
        userService.updatePassword(user, passwordResetRequest.getPassword());

        passwordResetTokenService.delete(passwordResetToken);

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Password Changed Successfully");
        apiResponse.setStatus(true);

        return ResponseEntity.ok(apiResponse);

    }

    @PostMapping("/reset-password-request")
    public ResponseEntity<ApiResponse> resetPassword(@RequestParam("email") String email) throws Exception{
        User user = userService.findUserByEmail(email);

        if(user==null){
            throw new Exception("User not Found");
        }
        userService.sendPasswordResetEmail(user);

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Password reset email is sent to your email, check your email");
        apiResponse.setStatus(true);

        return ResponseEntity.ok(apiResponse);
    }

}
