package com.study.springsecuritystudy.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
public class LoginResponseDto {
	private String token;

	private String message;

	public LoginResponseDto(String token, String message) {
		this.token = token;
		this.message = message;
	}
}
