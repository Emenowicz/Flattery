package zpi.flattery.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import zpi.flattery.security.LoginAuthenticationProvider;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private LoginAuthenticationProvider loginAuthenticationProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/", "/search", "/webjars/**", "/offers", "/css/**", "/js/**").permitAll()
                .antMatchers("/public/**", "/resources/**", "/resources/public/**").permitAll().antMatchers("/register", "/login").permitAll()
                .anyRequest().authenticated()
                .and().formLogin().and().formLogin().loginPage("/")
                .and().csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(loginAuthenticationProvider);
    }
}
