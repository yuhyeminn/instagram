package com.media.instagram.repository;

import com.media.instagram.domain.Profile;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProfileRepository extends JpaRepository<Profile, Long> {
    long countByNickname(String nickname);

    long countByNicknameAndProfileIdNot(String nickname, long profileId);
}
