package com.media.instagram.service;

import com.media.instagram.domain.User;
import com.media.instagram.domain.UserType;
import com.media.instagram.dto.UserDTO;
import com.media.instagram.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder bCryptPasswordEncoder;

    /**
     * 회원 등록
     * @param userDTO
     * @return
     */
    public User userEnroll(UserDTO userDTO) {
        //비밀번호 암호화
        userDTO.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        // 사용자 타입 설정. userType = INSTAGRAM
        userDTO.setUserType(UserType.INSTAGRAM);
        // User -> UserDTO
        User user = modelMapper.map(userDTO, User.class);
        // DB에 user 등록
        return userRepository.save(user);
    }

    /**
     * getUserByUserEmail
     * @param userEmail
     * @return
     */
    public UserDTO getUserByEmail(String userEmail) {
        UserDTO result = null;
        User user = userRepository.getByEmail(userEmail);
        if(user != null)
            result = modelMapper.map(user, UserDTO.class);
        return result;
    }

    public boolean availUserEmail(String userEmail) {
        boolean available = true;
        User user = userRepository.getByEmail(userEmail);
        if(user == null) available = false;
        return available;
    }

    public boolean matchPassword(String password, String password1) {
        return bCryptPasswordEncoder.matches(password, password);
    }
}
