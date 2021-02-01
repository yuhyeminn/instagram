package com.media.instagram.controller;

import com.media.instagram.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value="/profile")
public class ProfileController {

    private final ProfileService profileService;
}
