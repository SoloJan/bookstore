package nl.jansolo.bookstore.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableWebSecurity
public class DefaultWebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final String ROLE_CUSTOMER = "customer";


    @Value("${spring.security.user.name}")
    private String username;
    @Value("${spring.security.user.password}")
    private String password;

    /**
     * Configures security for HTTP
     * The bookstore endpoint can now be accesed by a user who is logged in and has the role customer
     * @param http {@link HttpSecurity} instance to configure
     * @throws Exception exception
     */
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .cors().and()
                .csrf().disable().headers().frameOptions().disable()
                .and()
                .authorizeRequests()
                .antMatchers( "/h2-console/**").permitAll()
                .antMatchers("/bookstore/**").hasRole(ROLE_CUSTOMER)
                .anyRequest().denyAll()
                .and()
                .httpBasic();
    }

    /**
     * loads a user into an in memory database with a password and username and the customer role
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser(username).password("{noop}"+password).roles(ROLE_CUSTOMER);
    }

}
