package com.intelliment.security.configuration;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	private static final Logger logger = Logger.getLogger(SecurityConfiguration.class);

	@Autowired
	public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
		logger.info("Setting up Authentication manager");
		auth.inMemoryAuthentication().withUser("vipin").password("vipin").roles("ADMIN");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		logger.info("Configuring Global Security");
		http.csrf().disable().authorizeRequests().antMatchers("/").permitAll().antMatchers("/top/**").access("hasRole('ADMIN')")
				.antMatchers("/search/**").access("hasRole('ADMIN')").and().httpBasic().and().exceptionHandling()
				.accessDeniedPage("/Access_Denied");

	}
}