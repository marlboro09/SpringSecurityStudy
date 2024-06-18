package com.study.springsecuritystudy.user.dto;

import lombok.Getter;

@Getter
public class SignupRequestDto {
	private String username;

	private String password;

	private boolean admin = false;

	private String adminToken = "";

	private String info;
}
