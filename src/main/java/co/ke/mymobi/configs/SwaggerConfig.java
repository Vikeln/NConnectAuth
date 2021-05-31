package co.ke.mymobi.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.AuthorizationScopeBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
  /**
   * Bean that configures Swagger API documentation.
   * 
   * @return base package of the API to be documented
   */
  @Bean
  public Docket api() {
    AuthorizationScope[] authScopes = new AuthorizationScope[1];
    authScopes[0] = new AuthorizationScopeBuilder().scope("global").description("full access").build();
    SecurityReference securityReference = SecurityReference.builder().reference("Authorization-key")
            .scopes(authScopes).build();

    SecurityReference securityReference1 = SecurityReference.builder().reference("App-Key")
            .scopes(authScopes).build();

    ArrayList<SecurityContext> securityContexts = new ArrayList<>();
    securityContexts.add(SecurityContext.builder().securityReferences(newArrayList(securityReference)).build());
    securityContexts.add(SecurityContext.builder().securityReferences(newArrayList(securityReference1)).build());


    return new Docket(DocumentationType.SWAGGER_2).select()
            .apis(RequestHandlerSelectors.basePackage("co.ke.mymobi"))
            .paths(PathSelectors.any())
            .build().apiInfo(apiEndPointsInfo())
            .securitySchemes(apiKey())
            .securityContexts(securityContexts);
  }

  private ApiInfo apiEndPointsInfo() {

    return new ApiInfoBuilder().title("AUTH SERVICE REST API").description("AUTH SERVICE REST API.")
            .license("TRENDY 2021")
            .version("2.0.0")
            .build();

  }

  private List<ApiKey> apiKey() {
    List<ApiKey> keys = new ArrayList<>();
    keys.add(new ApiKey("Authorization-key", "Authorization", "header"));
    keys.add(new ApiKey("App-Key", "App-Key", "header"));
    return keys;
  }
}