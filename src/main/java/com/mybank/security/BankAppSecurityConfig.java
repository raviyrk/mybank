package com.mybank.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
public class BankAppSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private BasicAuthentication basicAuthentication;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeRequests().antMatchers("/","/Account","/transfer").permitAll().anyRequest().authenticated();
		http.httpBasic().authenticationEntryPoint(basicAuthentication);
	}
	
	@Autowired 
	   public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {  
	     auth.inMemoryAuthentication().withUser("ravi").password("$2a$04$Sy8stMCVhG7.zRp2uneqauuhscMAaKToTPs9djnpEUqkPKyrsJgKe").roles("USER");  
	   }  

	@Bean
	public BCryptPasswordEncoder encoder() {
	    return new BCryptPasswordEncoder();
	}
	
}
