package net.icdpublishing.exercise2.myapp.customers.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import net.icdpublishing.exercise2.myapp.customers.security.CustomAccessDeniedHandler;
import net.icdpublishing.exercise2.myapp.customers.service.CustomerService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private CustomAccessDeniedHandler accessDeniedHandler;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
//		http
//			.csrf().disable()
//			.authorizeRequests()
//			.antMatchers("/", "/api").permitAll()
//			.anyRequest().authenticated()
//			.and()
//			
//			.formLogin()
//			.loginPage("/search")
//            .permitAll()
//            .and()
//            .logout()
//			.permitAll()
//			.and()
//            .exceptionHandling().accessDeniedHandler(accessDeniedHandler);;
            
//            http.csrf().disable().authorizeRequests().antMatchers("/", "/api").permitAll()
//            .and().antMatcher("/search").authorizeRequests().anyRequest().hasRole("USER")
//			.and().formLogin().loginPage("/search")
//			.failureUrl("/search?error=1").loginProcessingUrl("/")
//			.permitAll().and().logout()
//			.logoutSuccessUrl("/");
		
		 http
	      .authorizeRequests()
	        .antMatchers("/","/api/**").permitAll() // #4
	        .antMatchers("/search").hasRole("USER") // #6
	        .anyRequest().authenticated() // 7
	        .and()
	    .formLogin()  // #8
	        .loginPage("/search") // #9
	        .permitAll(); // #5
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		for (String email : customerService.getCustomersMap().keySet()) {
			auth.inMemoryAuthentication().withUser(email).password(email).roles("USER");
		}
		//auth.userDetailsService(userDetailsService).;
	}
}
