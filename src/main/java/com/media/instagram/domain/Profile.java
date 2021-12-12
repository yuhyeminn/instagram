package com.media.instagram.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Profile {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="profile_id")
    Long profileId;

    @Column(name="profile_nickname", unique=true, nullable = false)
    String nickname;

    @Column(name="profile_name")
    String name;

    @Column(name="profile_introduction")
    String introduction;

    @Column(name="profile_image")
    String profileImage;

    /**
     * 공개(public), 비공개(private)
     */
    @Column(name = "profile_privacy")
    @Enumerated(EnumType.STRING)
    ProfilePrivacy profilePrivacy;

    /**
     * 활성화(enable), 비활성화(disable)
     */
    @Column(name = "profile_status")
    @Enumerated(EnumType.STRING)
    ProfileStatus profileStatus;

}
