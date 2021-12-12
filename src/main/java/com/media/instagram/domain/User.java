package com.media.instagram.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(
        name="user",
        uniqueConstraints={
                @UniqueConstraint(
                    name="user_email_type_unique",
                    columnNames={"user_email","user_type"}
                )
        }
)
@Data
public class User {
    @Column(name = "user_id")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long userId;

    @Column(name = "user_email")
    String email;

    @Column(name = "user_password")
    String password;

    @Column(name = "user_name")
    String name;

    @Column(name = "user_nickname", unique=true)
    String nickname;

    @Column(name = "user_type")
    @Enumerated(EnumType.STRING)
    UserType userType;

    @Column(name = "auth_key")
    String authKey;

    @Column(name = "auth_status")
    @Enumerated(EnumType.STRING)
    UserAuthStatus authStatus;


}
