package com.media.instagram.dto;

import com.media.instagram.domain.ProfilePrivacy;
import com.media.instagram.domain.ProfileStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDTO {
    private long profileId;
    private String nickname;
    private String name;
    private String introduction;
    private String profileImage;
    private ProfilePrivacy profilePrivacy;
    private ProfileStatus profileStatus;
}
