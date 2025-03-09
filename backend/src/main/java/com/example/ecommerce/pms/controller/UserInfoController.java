package com.example.ecommerce.pms.controller;

import com.example.ecommerce.pms.entity.UserInfo;
import com.example.ecommerce.pms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/userInfo")
public class UserInfoController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    public UserInfo register(@RequestBody UserInfo user) {
        return userService.register(user);
    }

    @PostMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    public ResponseEntity<UserInfo> update(@PathVariable Long id, @RequestBody UserInfo userInfo) {
        return ResponseEntity.ok(userService.update(id, userInfo));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    public ResponseEntity<UserInfo> userDetail(@PathVariable Long id) {
        if (userService.userDetail(id) != null)
            return ResponseEntity.ok(userService.userDetail(id));
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> update(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.ok("User has been deleted successfully");
    }

    @PostMapping("/passwordChange/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    public ResponseEntity<UserInfo> passwordChange(@PathVariable Long id, @RequestBody UserInfo userInfo) {
        return ResponseEntity.ok(userService.passwordChange(id, userInfo));
    }


}
