package com.media.instagram.service;

import com.media.instagram.dto.ProfileDTO;

import java.util.Map;

public interface ProfileService {

    ProfileDTO updateProfile(ProfileDTO param);

    ProfileDTO updateNickname(Map<String, Object> param);
}
