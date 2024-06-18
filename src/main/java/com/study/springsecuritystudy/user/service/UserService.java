package com.study.springsecuritystudy.user.service;

import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.study.springsecuritystudy.security.JwtUtil;
import com.study.springsecuritystudy.user.dto.LoginRequsetDto;
import com.study.springsecuritystudy.user.dto.LoginResponseDto;
import com.study.springsecuritystudy.user.dto.SignupRequestDto;
import com.study.springsecuritystudy.user.dto.UserInfoDto;
import com.study.springsecuritystudy.user.entity.User;
import com.study.springsecuritystudy.user.entity.UserRoleEnum;
import com.study.springsecuritystudy.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    public User createUser(SignupRequestDto signupRequestDto) {
        String username = signupRequestDto.getUsername();
        String password = passwordEncoder.encode(signupRequestDto.getPassword());
        String info = signupRequestDto.getInfo();

        Optional<User> checkUser = userRepository.findByUsername(username);
        if(checkUser.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다");
        }

        UserRoleEnum role = UserRoleEnum.USER;
        if (signupRequestDto.isAdmin()) {
            if(!ADMIN_TOKEN.equals(signupRequestDto.getAdminToken())) {
                throw new IllegalArgumentException("관리자 암호가 틀렸습니다");
            }
            role = UserRoleEnum.ADMIN;
        }

        User user = new User(username, password, role, info);
        return userRepository.save(user);
    }

    public LoginResponseDto loginUser(@RequestBody LoginRequsetDto loginRequsetDto) {
        User user = this.userRepository.findByUsername(loginRequsetDto.getUsername()).orElseThrow(
            ()-> new IllegalArgumentException("아이디를 다시 확인해주세요")
        );

        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequsetDto.getUsername(),
                loginRequsetDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtUtil.createToken(loginRequsetDto.getUsername(),UserRoleEnum.USER);
        userRepository.save(user);

        return new LoginResponseDto(token, "로그인에 성공했습니다.");
    }

    public UserInfoDto getInfo(String username) {
        User user = findByUsername(username);
        return new UserInfoDto(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(
            ()-> new IllegalArgumentException("등록된 회원이 아닙니다.")
        );
    }

    public UserInfoDto getInfo(User user) {
        return new UserInfoDto(user);
    }
}
