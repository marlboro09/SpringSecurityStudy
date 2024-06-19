package com.study.springsecuritystudy.user.dto;

import com.study.springsecuritystudy.user.entity.User;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserInfoDto {
	String username;

	public UserInfoDto(User user) {
		this.username = user.getUsername();
	}
}
