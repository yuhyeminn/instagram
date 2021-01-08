package com.media.instagram.controller;

import com.media.instagram.service.UserService;
import lombok.RequiredArgsConstructor;
import org.junit.Test;
import org.springframework.web.bind.annotation.RestController;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

//@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

}
