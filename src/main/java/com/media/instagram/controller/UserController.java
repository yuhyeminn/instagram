package com.media.instagram.controller;

import com.media.instagram.domain.UserAuthStatus;
import com.media.instagram.dto.UserDTO;
import com.media.instagram.exception.UserException;
import com.media.instagram.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(value="/user")
public class UserController {

    private final UserService userService;

    /**
     * 회원가입 메서드
     * @param userDTO
     * @return
     */
    @PostMapping("")
    public ResponseEntity userEnroll(@RequestBody @Valid UserDTO userDTO, BindingResult bindingResult){
        //회원 가입 정보(UserDTO)입력 예외 처리
        if(bindingResult.hasErrors()){
            //오류 내용
            List<ObjectError> list = bindingResult.getAllErrors();
            StringBuffer errorLog = new StringBuffer();
            for(ObjectError e : list){
                errorLog.append(e.toString());
            }
            return new ResponseEntity(errorLog, HttpStatus.BAD_REQUEST);
        }

        try{
            UserDTO enrolledUser = null;

            // 이메일, 사용자 닉네임 중복일 경우
            if(!userService.availableUser(userDTO)){
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
            // 사용자 정보 db 등록
            enrolledUser = userService.userEnroll(userDTO);
            // 인증키 발급 및 인증 요청 이메일 발송
            userService.signUpEmailSend(enrolledUser);
            // 인증키 컬럼 db 저장
            userService.userInformUpdate(enrolledUser);

            return new ResponseEntity(HttpStatus.OK);

        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 사용자 이메일 인증 (email, authKey)
     * @param
     * @return
     */
    @PostMapping("/{userId}/emailconfirm")
    public ResponseEntity emailConfirm(@PathVariable Long userId, @RequestBody String authKey){
        try{

            Map<String, Object> param = new HashMap<>();
            param.put("userId", userId);
            param.put("authKey", authKey);
            // 등록된 사용자인지 검사 & 인증키 비교
            boolean matchedAuthKey = userService.authValidation(param);

            // 인증 여부 변경
            if(matchedAuthKey){
                // 인증 여부 컬럼 변경
                userService.completeUserAuth(userId);
            }

            return new ResponseEntity("auth complete",HttpStatus.OK);

        }catch (UserException e){
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 로그인 메서드
     * @param userDTO
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity userLogin(@RequestBody UserDTO userDTO, HttpSession session){
        try{
            // 이메일로 일치 회원 검사 (userType - instagram)
            UserDTO login_user = userService.getUserByEmail(userDTO.getEmail());
            // 등록된 회원 && 인증 완료한 회원
            if(login_user != null && UserAuthStatus.Y.equals(login_user.getAuthStatus())){
                //패스워드 비교
                if(userService.matchPassword(userDTO.getPassword(), login_user.getPassword())){
                    //로그인 성공
                    session.setAttribute("login_user", login_user);
                    return new ResponseEntity(login_user, HttpStatus.OK);
                }
            }
            // 인증 완료되지 않은 회원의 경우
            return new ResponseEntity("Unauthenticated user", HttpStatus.BAD_REQUEST);

        }catch (Exception e){
            e.printStackTrace();
            // 등록되지 않은 회원의 경우
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
