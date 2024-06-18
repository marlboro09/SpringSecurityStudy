package com.study.springsecuritystudy.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginRequsetDto {
	private String username;

	private String password;
}
