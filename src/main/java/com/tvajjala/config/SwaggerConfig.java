package com.tvajjala.config;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContextHolder;

import com.google.common.base.Predicate;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author ThirupathiReddy V
 *         <ul>
 *         <li>https://springfox.github.io/springfox/docs/current/</li>
 *         <li>/api/v2/api-docs</li>
 *         <li>http://stevemichelotti.com/customize-authentication-header-in-swaggerui-using-swashbuckle/</li>
 *         </ul>
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /** Reference to logger */
    private static final Logger LOGGER = LoggerFactory.getLogger( SwaggerConfig.class );

    @Autowired
    private MessageSource msgSource;

    @Bean
    public Docket petApi() {
	return new Docket( DocumentationType.SWAGGER_2 ).groupName( "swagger-api" ).apiInfo( apiInfo() ).select()
		//.apis( RequestHandlerSelectors.basePackage( "com.tvajjala.resource" ) )
		.paths(PathSelectors.any())
		.build().securitySchemes( newArrayList( apiKey() ) ).securityContexts( newArrayList( securityContext() ) );
    }

    public ApiInfo apiInfo() {
	return new ApiInfoBuilder()
		// .title("Wyzbee MicroServices API")
		.description( msgSource.getMessage( "api.description", null, LocaleContextHolder.getLocale() ) )
		.termsOfServiceUrl( "http://trvajjala.in" ).license( "Documentation" ).licenseUrl( "/docs" ).version( "1.0" ).build();
    }

    @Bean
    SecurityContext securityContext() {
	final AuthorizationScope readScope = new AuthorizationScope( "read:*", "read all the endpoints" );
	final AuthorizationScope[] scopes = new AuthorizationScope[1];
	scopes[0] = readScope;
	final SecurityReference securityReference = SecurityReference.builder().reference( "app-auth" ).scopes( scopes ).build();

	return SecurityContext.builder().securityReferences( newArrayList( securityReference ) ).forPaths( userOnlyEndpoints() ).build();
    }

    private Predicate<String> userOnlyEndpoints() {
	return input -> {

	    if ( LOGGER.isDebugEnabled() ) {
		LOGGER.debug( "input {} ", input );
	    }
	    return false;
	};
    }

    @Bean
    SecurityScheme apiKey() {
	return new ApiKey( "Authentication Token", "Authorization", "header" );// header ,query two values allowed
    }

    List<AuthorizationScope> scopes() {
	return newArrayList( new AuthorizationScope( "write:*", "modify endpoints" ), new AuthorizationScope( "read:*", "read endpoints" ) );
    }

}