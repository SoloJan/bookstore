package nl.jansolo.bookstore.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class OpenApiSwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        SecurityRequirement requirement = new SecurityRequirement().addList("basicScheme");
        List<SecurityRequirement> req = new ArrayList<>();
        req.add(requirement);
        return new OpenAPI()
                .components(new Components().addSecuritySchemes("basicScheme",
                            new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("basic"))).security(req)
                .info(new Info().title("Bookstore API").contact(new Contact().name("Jan Ruijtenberg")
                                .email("jan.ruijtenberg@hotmail.com"))
                        .description("This is a very simple bookstore application." +
                                " A shopkeeper can order books for his store" +
                                " And a customer can buy the books, but only if they are in stock." +
                                " There is a very limited set of books available and there is only one store." +
                                " Both the shopkeeper and the customer can see the list of shops, and books." +
                        "There is a user with username and password customer, and a user with username and password " +
                        "shopkeeper, you can authorize as one or the other to play around with the api's"));
    }
}
