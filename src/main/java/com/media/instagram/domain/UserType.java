package com.media.instagram.domain;

public enum UserType {
    INSTAGRAM(0), GOOGLE(1), KAKAO(2);

    private final int typeNum;

    UserType(int typeNum) {
        this.typeNum = typeNum;
    }
}
