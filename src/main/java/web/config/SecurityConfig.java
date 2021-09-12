package web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import web.config.handler.LoginSuccessHandler;
import web.service.UserService;
import web.service.UserServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private UserService userService;
    private LoginSuccessHandler loginSuccessHandler; // класс, в котором описана логика перенаправления пользователей по ролям

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setLoginSuccessHandler(LoginSuccessHandler loginSuccessHandler) {
        this.loginSuccessHandler = loginSuccessHandler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/hello").permitAll()
                .antMatchers("/users/**").hasRole("USER") // только для аутентифицированных пользователей
                .antMatchers("/admin/**").hasRole("ADMIN")
                .and().formLogin()
                .successHandler(loginSuccessHandler)//добавление стандартной формы login
                .and().logout().logoutSuccessUrl("/hello");//выход из учетки, перекинет на hello страницу
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
            DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
            authenticationProvider.setPasswordEncoder(passwordEncoder());
            authenticationProvider.setUserDetailsService(userService);

            return authenticationProvider;
    }
}
