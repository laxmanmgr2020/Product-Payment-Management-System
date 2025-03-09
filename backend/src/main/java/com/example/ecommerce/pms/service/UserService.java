package com.example.ecommerce.pms.service;

import com.example.ecommerce.pms.entity.UserInfo;
import com.example.ecommerce.pms.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserInfoRepository repo;


    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @Autowired
    UserInfoRepository userInfoRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo user = userInfoRepository.findByEmail(username);
        if (user == null) {
            System.out.println("User Not Found");
            throw new UsernameNotFoundException("user not found");
        }

        return user;
    }

    public UserInfo register(UserInfo user) {
        user.setPassword(encoder.encode(user.getPassword()));
        repo.save(user);
        return user;
    }

    public UserInfo userDetail(Long userId) {
        return userInfoRepository.findById(userId).orElse(null);
    }

    public UserInfo update(Long userId, UserInfo user) {
        UserInfo oldData = userDetail(userId);
        if (oldData != null) {
            oldData.setName(user.getName());
            oldData.setAddress(user.getAddress());
            repo.save(oldData);
        }
        return oldData;
    }

    public UserInfo passwordChange(Long userId, UserInfo user) {
        UserInfo oldData = userDetail(userId);
        if (oldData != null) {
            oldData.setPassword(encoder.encode(user.getPassword()));
            repo.save(oldData);
        }
        return oldData;
    }

    public void delete(Long userId) {
        UserInfo userInfo = userDetail(userId);
        if (userInfo != null)
            userInfoRepository.delete(userInfo);
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getAuthorities().iterator().next().toString();
        } else {
            return principal.toString();
        }
    }
}
