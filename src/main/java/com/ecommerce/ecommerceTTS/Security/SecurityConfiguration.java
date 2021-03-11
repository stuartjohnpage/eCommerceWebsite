package com.ecommerce.ecommerceTTS.Security;


import com.ecommerce.ecommerceTTS.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
    }
//          Can't get to cart or console.
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.
//                authorizeRequests()
//                .antMatchers("/console/**").permitAll()
//                .antMatchers("/main").permitAll()
//                .antMatchers("/signin").permitAll()
//                .antMatchers("/cart").authenticated()
//                .antMatchers("/custom.js").permitAll()
//                .antMatchers("/custom.css").permitAll();
//
//        http.headers().frameOptions().disable();
//
//    }
//}
    //can get to cart and console, but not see main unless logged in
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable();
        http
                .authorizeRequests()
                .antMatchers("/console/**").permitAll()
                .antMatchers("/signin").permitAll()
                .antMatchers("/cart").authenticated()
                .antMatchers("/main").permitAll()
                .antMatchers("/").permitAll()
                .antMatchers().hasAuthority("USER").anyRequest()
                .authenticated().and().csrf().disable().formLogin()
                .loginPage("/signin")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/")
                .and().logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/signout"))
                .logoutSuccessUrl("/");
    }
}

