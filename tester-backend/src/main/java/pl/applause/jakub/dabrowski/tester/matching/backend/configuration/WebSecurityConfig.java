package pl.applause.jakub.dabrowski.tester.matching.backend.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pl.applause.jakub.dabrowski.tester.matching.backend.constant.RolesConstants;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	public void globalUserDetails(final AuthenticationManagerBuilder auth) throws Exception {
		//@formatter:off
		auth.inMemoryAuthentication()
			.withUser("user").password(passwordEncoder.encode("user123")).roles(RolesConstants.USER_ROLE)
			.and().withUser("admin").password(passwordEncoder.encode("admin123")).roles(RolesConstants.ADMIN_ROLE);
		//@formatter:on
	}
	
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		// @formatter:off
		http
			.authorizeRequests()
			.antMatchers("/login").permitAll()
			.anyRequest().authenticated()
			.and().formLogin().permitAll()
			.and().csrf().disable();
		// @formatter:on
	}
}
