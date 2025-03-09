package com.example.ecommerce.pms.service;


import com.example.ecommerce.pms.entity.AuthDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private JWTService jwtService;

    @Autowired
    AuthenticationManager authManager;

    public String verify(AuthDto auth) {
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(auth.getUsername(), auth.getPassword()));
        if (authentication.isAuthenticated()) {
            String token = jwtService.generateToken(auth.getUsername());
            System.out.println("token = " + token);
            return token;
        } else {
            return "fail";
        }
    }

    public String getCurrentUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }

    public String getUserRole() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getAuthorities().iterator().next().toString();
        } else {
            return principal.toString();
        }
    }
}
