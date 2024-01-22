package com.panneer.springlearn.security;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user1")
@PreAuthorize("hasRole('USER1')")
public class UserOneController {
    @PostMapping("/users/{user}")
    @PreAuthorize("hasAuthority('user1:create')")
    public ResponseEntity<String> postUsers(@RequestBody String body, @PathVariable("user") String user) {
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('user1:read')")
    public ResponseEntity<String> getAllBuyOrders() {
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @DeleteMapping("/users")
    @PreAuthorize("hasAuthority('user1:delete')")
    public ResponseEntity<String> deleteAllBuyOrders() {
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @DeleteMapping("/users/{user}")
    @PreAuthorize("hasAuthority('user1:delete')")
    public ResponseEntity<String> deleteUserBuyOrders(@PathVariable("user") String user) {
        return new ResponseEntity<String>(HttpStatus.OK);
    }
}
