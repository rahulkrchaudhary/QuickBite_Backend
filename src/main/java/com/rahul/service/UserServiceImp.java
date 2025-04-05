package com.rahul.service;

import com.rahul.config.JwtProvider;
import com.rahul.model.PasswordResetToken;
import com.rahul.model.User;
import com.rahul.repository.PasswordResetTokenRepository;
import com.rahul.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Service
public class UserServiceImp implements UserService{

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;
    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${fronted.url}")
    private String frontedUrl;

    @Override
    public User findUserByJwtToken(String jwt) throws Exception {
        String email=jwtProvider.getEmailFromJwtToken(jwt);
        User user = findUserByEmail(email);
        return user;
    }

    @Override
    public User findUserByEmail(String email) throws Exception {
        User user= userRepository.findByEmail(email);
        if(user==null){
            throw new Exception("user not found");
        }
        return user;
    }

    @Override
    public void updatePassword(User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    @Override
    public void sendPasswordResetEmail(User user) {

        String resetToken = generateRandomToken();
        Date expiryDate = calculateExpiryDate();
//        LocalDateTime expiryDate = calculateExpiryDate();


        PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setUser(user);
        passwordResetToken.setToken(resetToken);
        passwordResetToken.setExpiryDate(expiryDate);

        passwordResetTokenRepository.save(passwordResetToken);

        sendEmail(
                user.getEmail(),
                "Password Reset",
                "Click the following link to reset your password: " + frontedUrl + "/account/reset-password?token=" + resetToken
        );


    }

    private void sendEmail(String email, String subject, String message) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setTo(email);
        simpleMailMessage.setFrom("quickbites@gmail.com");
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(message);

        javaMailSender.send(simpleMailMessage);
    }

    private Date calculateExpiryDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE, 10);
        return calendar.getTime();
    }


    private String generateRandomToken() {
        return UUID.randomUUID().toString();
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

}

