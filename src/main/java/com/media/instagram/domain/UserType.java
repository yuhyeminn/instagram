package com.media.instagram.domain;

public enum UserType {
    INSTAGRAM(0), FACEBOOK(1);

    private final int typeNum;

    UserType(int typeNum) {
        this.typeNum = typeNum;
    }
}
