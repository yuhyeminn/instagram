package com.media.instagram.serviceimpl;

import com.media.instagram.domain.Profile;
import com.media.instagram.dto.ProfileDTO;
import com.media.instagram.repository.ProfileRepository;
import com.media.instagram.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;
    private final ModelMapper modelMapper;
    @Override
    public ProfileDTO updateProfile(ProfileDTO param) {

        ProfileDTO result;
        // 변경 전 프로필
        Profile profile = profileRepository.getOne(param.getProfileId());
        // 이름 ,소개 -> 중복 가능
        profile.setIntroduction(param.getIntroduction());
        profile.setName(param.getName());

        profile = profileRepository.save(profile);
        result = modelMapper.map(profile, ProfileDTO.class);

        return result;
    }

    @Override
    public ProfileDTO updateNickname(Map<String, Object> param) {
        ProfileDTO result;

        String nickname = (String) param.get("nickname");
        long profileId = (long) param.get("profileId");
        //변경하려는 닉네임의 중복 체크
        //해당 profile 데이터 제외한 목록에서 중복 검색
        long cnt = profileRepository.countByNicknameAndProfileIdNot(nickname, profileId);
        // 중복된 닉네임 있을 경우
        if(cnt > 0){
            throw new RuntimeException("duplicate nickname");
        }

        // 닉네임 변경 처리
        Profile profile = profileRepository.getOne(profileId);
        profile.setNickname(nickname);
        result = modelMapper.map(profileRepository.save(profile), ProfileDTO.class);

        return result;
    }
}
