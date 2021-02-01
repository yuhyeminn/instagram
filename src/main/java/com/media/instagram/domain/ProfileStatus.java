package com.media.instagram.domain;

public enum ProfileStatus {
    ENABLE(0), DISABLE(1);

    private final int statusNum;

    ProfileStatus(int statusNum) {
        this.statusNum = statusNum;
    }
}
