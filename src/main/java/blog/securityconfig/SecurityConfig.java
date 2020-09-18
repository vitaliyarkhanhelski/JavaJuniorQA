package blog.securityconfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private PasswordEncoder passwordEncoder;

    public SecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .passwordEncoder(passwordEncoder)
                .withUser("admin")
                //.password("$2a$10$xnQcn0Hmg.G/C63BHwFw6O7EJP3TiO2hjmI/AV4AGZxlUnwE6koia")
                .password("$2a$10$zWXgio4NH6qQ5/Mpbbh2XuUpxDVFHApOlGvbJ3WpropoxDHW/Tsr6")
                .roles("ADMIN");
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/delete").hasRole("ADMIN")
                .antMatchers("/showFormForAdd").hasRole("ADMIN")
                .antMatchers("/showFormForUpdate").hasRole("ADMIN")
                .antMatchers("/favorite").hasRole("ADMIN")
                .antMatchers("/complete").hasRole("ADMIN")
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .defaultSuccessUrl("/");

        http
                .csrf().disable()
                .cors().disable();
    }
}
