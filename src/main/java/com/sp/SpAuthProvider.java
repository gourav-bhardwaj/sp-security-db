package com.sp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.sp.service.EmployeeAuthService;

@Component
public class SpAuthProvider implements AuthenticationProvider {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private EmployeeAuthService authService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String userName = (String)authentication.getPrincipal();
		if (null != userName) {
			UserDetails userDetails = this.authService.loadUserByUsername(userName);
			if (null != userDetails) {
				String password = (String)authentication.getCredentials();
				if (null == password || !passwordEncoder.matches(userDetails.getPassword(), password)) {
					throw new BadCredentialsException("employee credentials not match");
				}
				if (!userDetails.isAccountNonExpired()) {
					throw new AccountExpiredException("employee account has been expired");
				}
				if (!userDetails.isAccountNonLocked()) {
					throw new LockedException("employee account has been locked");
				}
				if (!userDetails.isCredentialsNonExpired()) {
					throw new CredentialsExpiredException("employee credentials has been expired");
				}
				return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities());
			}
		}
		return null;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}

}
