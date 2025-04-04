package com.rahul.service;

import com.rahul.model.PasswordResetToken;

public interface PasswordResetTokenService {
    public PasswordResetToken findByToken(String token);

    public void delete(PasswordResetToken resetToken);

}
