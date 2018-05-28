package zpi.flattery.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests().antMatchers("/","/search", "/webjars/**", "/offers", "/css/**","/js/**").permitAll()
                .antMatchers("/public/**", "/resources/**","/resources/public/**").permitAll()
                .antMatchers("/**").anonymous()
                .anyRequest().authenticated()
                .and()
                .csrf().disable()
                .cors()
        ;
    }


}
