package com.panneer.springlearn.security.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.panneer.springlearn.security.enums.Permission.*;


@RequiredArgsConstructor
public enum Role {

    USER(Collections.emptySet()),
    USER1(
            Set.of(
                    USER1_READ,
                    USER1_UPDATE,
                    USER1_DELETE,
                    USER1_CREATE
            )
    ),
    USER2(
            Set.of(
                    USER2_READ,
                    USER2_UPDATE,
                    USER2_DELETE,
                    USER2_CREATE
            )
    ),
    ALL(
            Set.of(
                    USER1_READ,
                    USER1_UPDATE,
                    USER1_DELETE,
                    USER1_CREATE,
                    USER2_READ,
                    USER2_UPDATE,
                    USER2_DELETE,
                    USER2_CREATE
            )
    ),

    ;

    @Getter
    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}