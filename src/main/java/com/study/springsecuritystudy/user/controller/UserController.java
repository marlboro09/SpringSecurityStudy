package com.study.springsecuritystudy.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.study.springsecuritystudy.security.JwtUtil;
import com.study.springsecuritystudy.security.UserDetailsImpl;
import com.study.springsecuritystudy.user.dto.LoginRequsetDto;
import com.study.springsecuritystudy.user.dto.LoginResponseDto;
import com.study.springsecuritystudy.user.dto.SignupRequestDto;
import com.study.springsecuritystudy.user.dto.UserInfoDto;
import com.study.springsecuritystudy.user.entity.User;
import com.study.springsecuritystudy.user.entity.UserRoleEnum;
import com.study.springsecuritystudy.user.service.UserService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@Slf4j(topic = "로그인 및 JWT 생성")
public class UserController {

	final UserService userService;
	final JwtUtil jwtUtil;

	public UserController(UserService userService, JwtUtil jwtUtil) {
		this.userService = userService;
		this.jwtUtil = jwtUtil;
	}

	//회원가입(post /user)
	//로그인(post /login)
	//JWT를 이용한 UserName 반환 GET 만들기 (GET /user/info)

	@PostMapping("/jwt")
	public ResponseEntity<String> createJWT() {
		String username = "박성원";
		UserRoleEnum role = UserRoleEnum.USER;

		return new ResponseEntity<>(jwtUtil.createToken(username, role), HttpStatus.OK);
	}

	@GetMapping("/")
	public ResponseEntity<String> test() {
		return new ResponseEntity<>("잘 들어 옵니다?", HttpStatus.OK);
	}

	@PostMapping("/users")
	public ResponseEntity<User> signup(@RequestBody SignupRequestDto signupRequestDto) {
		return ResponseEntity.ok(userService.createUser(signupRequestDto));
	}

	@PostMapping("/login")
	public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequsetDto loginRequsetDto) {
		return ResponseEntity.ok(userService.loginUser(loginRequsetDto));
	}

	@GetMapping("/users/info")
	public ResponseEntity<UserInfoDto> info(@AuthenticationPrincipal UserDetailsImpl userDetails) {
		System.out.println("유저확인 : " + userDetails.getUsername());
		UserInfoDto userInfoDto = userService.getInfo(userDetails.getUser());
		return ResponseEntity.ok(userInfoDto);
	}
}
