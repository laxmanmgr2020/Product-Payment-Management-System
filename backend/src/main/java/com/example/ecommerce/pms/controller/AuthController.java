package com.example.ecommerce.pms.controller;


import com.example.ecommerce.pms.entity.AuthDto;
import com.example.ecommerce.pms.service.AuthService;
import com.example.ecommerce.pms.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody AuthDto auth) {
        Map<String, String> map = new HashMap<>();
        String token = authService.verify(auth);
        UserDetails userDetails = userService.loadUserByUsername(auth.getUsername());
        map.put("token", token);
        map.put("role", userDetails.getAuthorities().iterator().next().getAuthority());
        map.put("username", userDetails.getUsername());
        System.out.println("map = " + map);
        return ResponseEntity.ok(map);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextHolder.clearContext(); // Clears authentication context

        response.setStatus(HttpServletResponse.SC_OK);
        System.out.println("here we are===");
        return ResponseEntity.ok("{\"message\": \"Logout successful\"}");
    }
}
