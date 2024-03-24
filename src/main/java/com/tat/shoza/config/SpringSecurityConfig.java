package com.tat.shoza.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.tat.shoza.handleexception.ForbiddenHandle;
import com.tat.shoza.service.UserService;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private UserService userService;
	
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
		auth.setUserDetailsService(userService);
		auth.setPasswordEncoder(passwordEncoder());
		return auth;
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	    auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
	}
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	    http
	        .authorizeRequests()
	        .antMatchers("/","/pay/**", "/home","/shoza/introduce","/error","/shoza/search**","/shoza/feedback","/shoza/category/**","/register/**", "/js/**", "/css/**", "/img/**").permitAll()
	            .antMatchers("/admin/**","/api-admin/**").hasRole("ADMIN")
	        	.antMatchers("/user/**", "/shoza/addToCart**", "/shoza/my-cart/**","/shoza/check/**","/shoza/comment/**","/shoza/product/**")
	        	.hasAnyRole("USER","ADMIN")
	            .antMatchers("/login").permitAll()
	            .anyRequest().authenticated()
	        .and()
	        .formLogin()
	            .loginPage("/login")
	            .permitAll()
	        .and()
	        .logout()
	            .invalidateHttpSession(true)
	            .clearAuthentication(true)
	            .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
	            .logoutSuccessUrl("/")
	            .permitAll()
	        .and()
	        .exceptionHandling()
	        	.accessDeniedHandler(new ForbiddenHandle())

	        	
	        	;
	}
	
//	.exceptionHandling()
//	.accessDeniedHandler(new CustomAccessDeniedHandler())
//	.defaultAuthenticationEntryPointFor(new HttpStatusEntryPoint(HttpStatus.NOT_FOUND), new AntPathRequestMatcher("/**"))
//	;
}
