package com.sp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder.BCryptVersion;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.sp.constants.Roles;

@EnableWebSecurity
@Configuration
public class SpSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/signUp").permitAll()
		.antMatchers("/logout").permitAll()
		.antMatchers("/v2/api-docs").hasRole(Roles.ROLE_ADMIN)
		.antMatchers("/api-swagger-ui").hasRole(Roles.ROLE_ADMIN)
		.antMatchers("/roles", "/roles/**").hasRole(Roles.ROLE_ADMIN)
		.anyRequest().authenticated()
		.and().formLogin()
		.and().exceptionHandling().authenticationEntryPoint(new SpAuthEntrypoint())
		.and().csrf().disable();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(BCryptVersion.$2Y, 11);
	}

}
