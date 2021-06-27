package com.sp.mapper;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sp.dto.Employee;

public class EmployeeMapper implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1398704765612296501L;
	private long userId;
	private String username;
	@JsonIgnore
	private String password;
	private Collection<? extends GrantedAuthority> authorities;
	private boolean accountNonExpired;
	private boolean accountNonLocked;
	private boolean credentialsNonExpired;
	private boolean enable;
	
	public EmployeeMapper(Employee employee) {
		this.userId = employee.getEmpId();
		this.username = employee.getEmpEmail();
		this.password = employee.getEmpPassword();
		this.authorities = employee.getEmpRoles().stream().map(roleObj -> {
			return new SimpleGrantedAuthority(roleObj.getEmpRoleCode());
		}).collect(Collectors.toList());
		this.accountNonExpired = LocalDateTime.now().isBefore(employee.getEmpAccExpire());
		this.accountNonLocked = LocalDateTime.now().plusDays(7).isBefore(employee.getEmpAccExpire());
		this.credentialsNonExpired = LocalDateTime.now().plusDays(10).isBefore(employee.getEmpAccExpire());;
		this.enable = employee.isEmpEnable();
	}

	public long getUserId() {
		return userId;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return this.accountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return this.credentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		return this.enable;
	}

}
