package com.rahul.service;

import com.rahul.model.PasswordResetToken;
import com.rahul.repository.PasswordResetTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PasswordResetTokenServiceImp implements PasswordResetTokenService{

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Override
    public PasswordResetToken findByToken(String token) {
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token);
        return passwordResetToken;
    }

    @Override
    public void delete(PasswordResetToken resetToken) {
        passwordResetTokenRepository.delete(resetToken);
    }
}
