package com.media.instagram.domain;

public enum UserAuthStatus {
    Y(0), N(1);

    private final int statusNum;

    UserAuthStatus(int statusNum) {
        this.statusNum = statusNum;
    }
}
