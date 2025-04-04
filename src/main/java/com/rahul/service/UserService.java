package com.rahul.service;

import com.rahul.model.User;

public interface UserService {
    public User findUserByJwtToken(String jwt) throws Exception;

    public User findUserByEmail(String email) throws Exception;

    void updatePassword(User user, String newPassword);

    void sendPasswordResetEmail(User user);

    void deleteUser(Long id);
}
