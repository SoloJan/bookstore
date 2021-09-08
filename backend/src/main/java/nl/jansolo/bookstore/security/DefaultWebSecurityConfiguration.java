package nl.jansolo.bookstore.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class DefaultWebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final String ROLE_CUSTOMER = "customer";
    private static final String ROLE_SHOPKEEPER = "shopkeeper";

    @Value("${customer.user.name}")
    private String customerUserName;
    @Value("${customer.user.password}")
    private String customerPassword;
    @Value("${shopkeeper.user.name}")
    private String shopkeeperUserName;
    @Value("${shopkeeper.user.password}")
    private String shopKeeperPassword;

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
                .antMatchers("/v3/api-docs/**", "/swagger*/**", "/webjars/**").permitAll()
                .antMatchers(HttpMethod.PUT, "/bookstore/*/order").hasAnyRole(ROLE_SHOPKEEPER)
                .antMatchers(HttpMethod.PUT, "/bookstore/*/buy").hasAnyRole(ROLE_CUSTOMER)
                .antMatchers(HttpMethod.GET, "/bookstore").hasAnyRole(ROLE_CUSTOMER, ROLE_SHOPKEEPER)
                .antMatchers(HttpMethod.GET, "/book").hasAnyRole(ROLE_CUSTOMER, ROLE_SHOPKEEPER)
                .anyRequest().denyAll()
                .and()
                .httpBasic();
    }

    /**
     * loads a customer, and a shopkeeper user into an in memory database, should not be used in production
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser(customerUserName).password("{noop}"+ customerPassword).roles(ROLE_CUSTOMER);
        auth.inMemoryAuthentication().withUser(shopKeeperPassword).password("{noop}"+ shopKeeperPassword).roles(ROLE_SHOPKEEPER);
    }

}
