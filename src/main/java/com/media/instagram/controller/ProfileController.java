package com.media.instagram.controller;

import com.media.instagram.dto.ProfileDTO;
import com.media.instagram.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(value="/profile")
public class ProfileController {

    private final ProfileService profileService;

    // TODO : 프로필 기본 정보 수정 (소개, 이름) - 중복 이슈 없는 컬럼
    // ProfileDTO param - 변경할 소개, 이름
    @PutMapping("/{profileId}")
    public ResponseEntity updateProfile(@PathVariable Long profileId, @RequestBody ProfileDTO param){
            ProfileDTO result;
        try{
            param.setProfileId(profileId);
            result = profileService.updateProfile(param);
            return new ResponseEntity(result,HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{profileId}/nickname")
    public ResponseEntity updateNickname(@PathVariable Long profileId, @RequestParam String nickname){
        // FIXME : nickname 유효성 검사는..? 이 하나의 데이터를 위해 DTO를 써야하는가..?
        // nickname에 빈 값 들어올 경우
        if("".equals(nickname) || nickname == null){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        try {
            ProfileDTO result = null;
            Map<String, Object> param = new HashMap<>();
            param.put("nickname", nickname);
            param.put("profileId", profileId);
            if(!"".equals(nickname)){
                result = profileService.updateNickname(param);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // TODO : 프로필 사진 편집 (1장만 가능하기 때문에 등록과 수정 동시에)
    // TODO : 프로필 공개 여부 수정
    // TODO : 프로필 비활성화 여부 수정 (계정 비활성화 시)
}
