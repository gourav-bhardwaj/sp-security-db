package com.sp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder.BCryptVersion;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.sp.constants.Roles;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
public class SpSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/signUp").permitAll()
		.antMatchers("/logout").permitAll()
		.antMatchers("/v2/api-docs").hasAnyRole(Roles.ROLE_USER, Roles.ROLE_ADMIN)
		.antMatchers("/api-swagger-ui").hasRole(Roles.ROLE_ADMIN)
		.antMatchers("/roles", "/roles/**").hasRole(Roles.ROLE_ADMIN)
		.anyRequest().authenticated()
		.and().formLogin().successHandler((request, response, authentication) -> {
					List<String> authorities = authentication.getAuthorities()
							.stream().map(GrantedAuthority::getAuthority)
							.collect(Collectors.toList());
					StringBuilder redirectPath = new StringBuilder(request.getContextPath());
					if (log.isDebugEnabled()) {
						log.debug("Authorities :: {}", authentication);
					}
					log.info("Current Context :: {}", request.getContextPath());
					redirectPath.append(!authorities.contains("ROLE_"+Roles.ROLE_ADMIN) ? "/v2/api-docs" : "/api-swagger-ui");
					response.sendRedirect(redirectPath.toString());
				}).and().csrf().disable();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(BCryptVersion.$2Y, 11);
	}

}
