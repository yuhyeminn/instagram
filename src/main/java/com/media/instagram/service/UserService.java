package com.media.instagram.service;

import com.media.instagram.domain.User;
import com.media.instagram.dto.UserDTO;

import java.util.Map;

public interface UserService {

    UserDTO userEnroll(UserDTO userDTO);

    UserDTO getUserByEmail(String email);

    boolean matchPassword(String password, String password1);

    boolean availableUser(UserDTO userDTO);

    String signUpEmailSend(UserDTO enrolledUser);

    void userInformUpdate(UserDTO enrolledUser);

    boolean authValidation(Map<String,Object> param);

    void completeUserAuth(Long userId);

}
