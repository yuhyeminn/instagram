package com.media.instagram.serviceimpl;

import com.media.instagram.domain.User;
import com.media.instagram.domain.UserAuthStatus;
import com.media.instagram.domain.UserType;
import com.media.instagram.dto.UserDTO;
import com.media.instagram.exception.UserException;
import com.media.instagram.repository.UserRepository;
import com.media.instagram.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.security.auth.message.AuthStatus;
import javax.transaction.Transactional;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder bCryptPasswordEncoder;
    private final JavaMailSender javaMailSender;


    /**
     * 회원 등록
     * @param userDTO
     * @return
     */
    public UserDTO userEnroll(UserDTO userDTO) {
        UserDTO result = null;
        //비밀번호 암호화
        userDTO.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        // 사용자 타입 설정. userType = INSTAGRAM
        userDTO.setUserType(UserType.INSTAGRAM);
        // 인증 여부 설정
        userDTO.setAuthStatus(UserAuthStatus.N);
        // User -> UserDTO
        // User 테이블에 추가
        User user = userRepository.save(modelMapper.map(userDTO, User.class));

        result = modelMapper.map(user, UserDTO.class);

        return result;
    }

    /**
     * getUserByUserEmail
     * @param userEmail
     * @return
     */
    public UserDTO getUserByEmail(String userEmail) {
        UserDTO result = null;
        User user = userRepository.getByEmailAndUserType(userEmail,UserType.INSTAGRAM);
        if(user != null)
            result = modelMapper.map(user, UserDTO.class);
        else
            throw new UserException("unregistered user");
        return result;
    }

    /**
     * 로그인 비밀번호 일치 여부 검사
     * @param password
     * @param password1
     * @return
     */
    public boolean matchPassword(String password, String password1) {
        boolean result = false;
        result = bCryptPasswordEncoder.matches(password, password1);
        if(!result){
            throw new UserException("unmatched password");
        }
        return result;
    }

    /**
     * 회원가입 이메일 및 닉네임 중복 검사
     * @param userDTO
     * @return
     */
    @Override
    public boolean availableUser(UserDTO userDTO) {
        boolean result = true;
        long count ;
        // email, userType으로 중복 데이터가 있는지 검사
        count = userRepository.countByEmailAndUserType(userDTO.getEmail(), UserType.INSTAGRAM);
        if(count > 0) {
            result = false;
            // 이메일 중복. 예외 던지기
            throw new UserException("duplicate email");
        }

        // nickname 중복 검사
        count = userRepository.countByNickname(userDTO.getNickname());
        if(count > 0){
            result = false;
            // 닉네임 중복. 예외 던지기
            throw new UserException("duplicate nickname");
        }
        
        return result;
    }

    /**
     * 회원가입 인증 이메일 발송
     * @param enrolledUser
     * @return
     */
    @Override
    public String signUpEmailSend(UserDTO enrolledUser) {
        // 사용자 인증 키 발급
        enrolledUser.generateAuthKey();

        // 이메일 메시지 설정
        MimeMessage mail = javaMailSender.createMimeMessage();
        try{
            mail.setSubject("instagram 회원 가입 이메일 인증","UTF-8");
            mail.addRecipient(Message.RecipientType.TO, new InternetAddress(enrolledUser.getEmail()));
            mail.setText(new StringBuffer().append("<h1>[이메일 인증]</h1>")
                    .append("<p>아래 링크를 클릭하시면 이메일 인증이 완료됩니다.</p>")
                    .append("<form method='post' action='http://localhost:8088/user/")
                    .append(enrolledUser.getUserId())
                    .append("/emailconfirm'>")
                    .append("<input type='hidden' name='_method' value='put'/>")
                    .append("<input type='hidden' name='userId' value='")
                    .append(enrolledUser.getUserId())
                    .append("'/>")
                    .append("<input type='hidden' name='authKey' value='")
                    .append(enrolledUser.getAuthKey())
                    .append("'/>")
                    .append("<button type='submit'>인증 완료</button>")
                    .append("</form>")
                    .toString(), "UTF-8", "html");
            //사용자 인증 이메일 전송
            javaMailSender.send(mail);
        }catch (Exception e){
            e.printStackTrace();
            throw new UserException("user-auth email sending failure");
        }

        return enrolledUser.getAuthKey();
    }

    @Override
    public void userInformUpdate(UserDTO enrolledUser) {
        User user = modelMapper.map(enrolledUser, User.class);
        userRepository.save(user);
    }

    @Override
    public boolean authValidation(Map<String, Object> param) {
        // db에 등록되어 있는 사용자
        long userId = (long)param.get("userId");
        User enrolledUser = userRepository.getOne(userId);

        // 이미 인증 되어 있는 경우
        if(enrolledUser.getAuthStatus().equals(UserAuthStatus.Y)){
            throw new UserException("already completed auth");
        }else{
            // 인증 키 일치 여부 검사
            String param_authKey = (String) param.get("authKey");
            if(param_authKey.equals(enrolledUser.getAuthKey())){
                return true;
            }else{
                throw new UserException("invalid auth key");
            }
        }
    }

    @Override
    public void completeUserAuth(Long userId) {
        User user = userRepository.getOne(userId);
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        // 컬럼 변경
        userDTO.setAuthStatus(UserAuthStatus.Y);
        userRepository.save(modelMapper.map(userDTO, User.class));
    }

    @Override
    public void updatePassword(Long userId, String password) {
        User user = userRepository.getOne(userId);
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        // 변경된 비밀번호 셋팅
        String encodedPassword = bCryptPasswordEncoder.encode(password);
        userDTO.setPassword(encodedPassword);
        user = userRepository.save(modelMapper.map(userDTO, User.class));
        if(user == null){
            throw  new UserException("failed change password");
        }
    }

}
