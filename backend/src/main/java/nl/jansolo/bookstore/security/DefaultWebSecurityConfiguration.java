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
    private static final String ROLE_DEVELOPER = "developer";

    @Value("${customer.user.name}")
    private String customerUserName;
    @Value("${customer.user.password}")
    private String customerPassword;
    @Value("${shopkeeper.user.name}")
    private String shopkeeperUserName;
    @Value("${shopkeeper.user.password}")
    private String shopKeeperPassword;
    @Value("${developer.user.name}")
    private String developerUserName;
    @Value("${developer.user.password}")
    private String developerPassword;
    /**
     * Configures security for HTTP
     * Cors and csrf are disabled which should not be done in production
     * The customer and shopkeeper roles can both look at the available books and the stores
     * Only the customer can buy books, and only shopkeepers can order new books to his store
     * The swagger documentation and H2 database are only accessible by developers
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
                .antMatchers( "/h2-console/**").hasRole(ROLE_DEVELOPER)
                .antMatchers("/v3/api-docs/**", "/swagger*/**", "/webjars/**").hasRole(ROLE_DEVELOPER)
                .antMatchers(HttpMethod.PUT, "/bookstore/*/order").hasAnyRole(ROLE_SHOPKEEPER)
                .antMatchers(HttpMethod.PUT, "/bookstore/*/buy").hasAnyRole(ROLE_CUSTOMER)
                .antMatchers(HttpMethod.GET, "/bookstore").hasAnyRole(ROLE_CUSTOMER, ROLE_SHOPKEEPER)
                .antMatchers(HttpMethod.GET, "/book").hasAnyRole(ROLE_CUSTOMER, ROLE_SHOPKEEPER)
                .anyRequest().denyAll()
                .and()
                .httpBasic();
    }

    /**
     * loads a customer,  a shopkeeper and a developer all with credentials into an in memory database, should not be used in production
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser(customerUserName).password("{noop}"+ customerPassword).roles(ROLE_CUSTOMER);
        auth.inMemoryAuthentication().withUser(shopkeeperUserName).password("{noop}"+ shopKeeperPassword).roles(ROLE_SHOPKEEPER);
        auth.inMemoryAuthentication().withUser(developerUserName).password("{noop}"+ developerPassword).roles(ROLE_DEVELOPER);
    }

}
