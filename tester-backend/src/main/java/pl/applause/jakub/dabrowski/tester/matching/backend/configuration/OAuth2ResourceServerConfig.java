package pl.applause.jakub.dabrowski.tester.matching.backend.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import pl.applause.jakub.dabrowski.tester.matching.backend.constant.RolesConstants;

@EnableResourceServer
@Configuration
public class OAuth2ResourceServerConfig extends ResourceServerConfigurerAdapter {
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests().antMatchers("/protected/**").hasRole(RolesConstants.ADMIN_ROLE);
		http.authorizeRequests().antMatchers("/api/**").hasAnyRole(RolesConstants.ADMIN_ROLE, RolesConstants.USER_ROLE);
	}
}