package com.panneer.springlearn.security.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

    USER1_READ("user1:read"),
    USER1_UPDATE("user1:update"),
    USER1_CREATE("user1:create"),
    USER1_DELETE("user1:delete"),
    USER2_READ("user2:read"),
    USER2_UPDATE("user2:update"),
    USER2_CREATE("user2:create"),
    USER2_DELETE("user2:delete");

    @Getter
    private final String permission;
}