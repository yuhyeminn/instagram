package com.media.instagram.domain;

public enum ProfilePrivacy {
    PUBLIC(0), PRIVATE(1);

    private final int privacyNum;

    ProfilePrivacy(int privacyNum) {
        this.privacyNum = privacyNum;
    }
}
