package com.sp.dto;

import lombok.Data;

@Data
public class UserAuthDTO {
	private String name;
	private String email;
	private String password;
	private String role;
	private boolean enable;
}
