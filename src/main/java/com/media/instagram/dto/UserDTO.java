package com.media.instagram.dto;

import com.media.instagram.domain.Profile;
import com.media.instagram.domain.UserAuthStatus;
import com.media.instagram.domain.UserType;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private long userId;

    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "이메일 양식이 올바르지 않습니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
            message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.")
    private String password;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
            message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.")
    private String passwordChk;

    private String name;

    @NotBlank(message = "사용자 닉네임은 필수 입력 값입니다.")
    private String nickname;

    private UserType userType;

    private String authKey;

    private UserAuthStatus authStatus;

    private Profile profile;

    /**
     * 이메일 인증을 위한 임의의 토큰 생성
     */
    public void generateAuthKey() {
        this.authKey = UUID.randomUUID().toString();
    }
}
