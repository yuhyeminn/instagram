package com.media.instagram.controller;

import com.media.instagram.domain.User;
import com.media.instagram.dto.UserDTO;
import com.media.instagram.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(value="/users")
public class UserController {

    private final UserService userService;

    /**
     * 회원가입 메서드
     * @param userDTO
     * @return
     */
    @PostMapping("")
    public ResponseEntity<Void> userEnroll(@RequestBody @Valid UserDTO userDTO){
        try{
            // 이메일 중복 확인
            if(userService.availUserEmail(userDTO.getEmail())){
                //사용자 등록
                User user = userService.userEnroll(userDTO);
            }

            return new ResponseEntity<>(HttpStatus.OK);
            
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 로그인 메서드
     * @param email
     * @param password
     * @return
     */
    @GetMapping("/login")
    public ResponseEntity<Void> userLogin(@RequestParam String email, @RequestParam String password){
        try{
            
            UserDTO login_user = userService.getUserByEmail(email);
            if(login_user == null){
                // 해당 이메일 등록되어 있지 않음
                // 메시지 처리를 어떻게 할것인가?
                // System.out.println("해당 이메일 없음");
            }else{
                //패스워드 비교
                if(userService.matchPassword(password, login_user.getPassword())){
                    //로그인 성공
                }else{
                    //비밀번호 오류
                }
            }

            return new ResponseEntity<>(HttpStatus.OK);
            
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


}
