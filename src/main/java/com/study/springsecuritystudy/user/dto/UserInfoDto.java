package com.study.springsecuritystudy.user.dto;

import com.study.springsecuritystudy.user.entity.User;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserInfoDto {
	String username;

	boolean isAdmin;

	public UserInfoDto(User getUser) {
		this.username = username;
	}
}
