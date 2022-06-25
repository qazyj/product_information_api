package api.productinformation.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@EnableWebMvc
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30)
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.basePackage("api.productinformation.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("KYJ Swagger Test")
                .description("POST example \n\nItem      " +
                        "{" +
                        "\"itemName\" : \"test\"," +
                        "\"itemPrice\" : 10000," +
                        "\"itemType\" : \"일반\"," +
                        "\"stockQuantity\" : 10," +
                        "\"startDate\" : \"2022.2.2\"," +
                        "\"endDate\" : \"2022.4.5\"" +
                        "}\n\n" +
                        "Promotion      " +
                        "{" +
                        "\"promotionName\" : \"입사할인\"," +
                        "\"discountAmount\" : \"10000\"," +
                        "\"startDate\" : \"2022.2.2\"," +
                        "\"endDate\" : \"2022.4.5\"" +
                        "}\n\n" +
                        "User      " +
                        "{" +
                        "\"userName\" : \"김영진\"," +
                        "\"userType\" : \"일반\"," +
                        "\"userState\" : \"정상\"," +
                        "\"address\" : {" +
                        "\"city\" : \"인천\"," +
                        "\"street\" : \"남동구 논고개로\"," +
                        "\"zipcode\" : \"21667\"}" +
                        "}\n\n" +
                        "DELETE example\n\nItem      " +
                        "{" +
                        "\"id\" : 1" +
                        "}\n\n" +
                        "Promotion      " +
                        "{" +
                        "\"id\" : 1" +
                        "}\n\n" +
                        "User      " +
                        "{" +
                        "\"id\" : 1" +
                        "}\n\n" +
                        "GET example\n\nItem      " +
                        "{" +
                        "\"id\" : 1" +
                        "}\n\n" +
                        "User      " +
                        "{" +
                        "\"id\" : 1" +
                        "}")
                .version("3.0")
                .build();
    }

}