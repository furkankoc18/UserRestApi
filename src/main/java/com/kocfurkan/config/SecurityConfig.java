package com.kocfurkan.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	
	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("furkan").password("{noop}password").roles("ADMIN");
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		/*
		 * http.httpBasic().and().csrf().disable().authorizeRequests().antMatchers("/").
		 * permitAll() .antMatchers(HttpMethod.POST,
		 * "/userService/saveUser").permitAll() .antMatchers(HttpMethod.POST,
		 * "/userService/removeUser").permitAll() .antMatchers(HttpMethod.POST,
		 * "/loginService/userLogin").permitAll() .antMatchers(HttpMethod.POST,
		 * "/login").permitAll().antMatchers(HttpMethod.POST, "/newuser/*")
		 * .permitAll().antMatchers(HttpMethod.GET,
		 * "/userService/newUserActivition").permitAll() .antMatchers(HttpMethod.GET,
		 * "/exploreCourse").permitAll().anyRequest().authenticated();
		 */

		http.httpBasic().and().authorizeRequests().antMatchers("/userService/getUser").hasRole("ADMIN")
				.antMatchers("/loginService/**").hasRole("ADMIN").and().csrf().disable().formLogin().disable();

	}

}