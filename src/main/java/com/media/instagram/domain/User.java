package com.media.instagram.domain;

import javax.persistence.*;

@Entity
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long userId;

    @Column(name = "user_email")
    String email;

    // FIXME : Column 어노테이션 추가
    String password;

    String name;

    String nickname;

    @Enumerated(EnumType.STRING)
    UserType userType;


}
