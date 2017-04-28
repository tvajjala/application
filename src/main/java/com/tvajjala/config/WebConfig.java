package com.tvajjala.config;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.h2.server.web.WebServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.MediaType;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * If you want to completely suppress the spring boot auto configuration(for webMVC) you need to use @EnableWebMvc <br>
 * and write class that extends WebMvcConfigurer define your own beans so that it will pickup these beans.
 *
 * @author ThirupathiReddy V
 */
@EnableWebMvc
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    /** Reference to logger */
    private static final Logger LOGGER = LoggerFactory.getLogger( WebConfig.class );

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyPlaceHolderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Override
    public void addCorsMappings( CorsRegistry registry ) {
        super.addCorsMappings( registry );
        // https://en.wikipedia.org/wiki/Cross-origin_resource_sharing
        registry.addMapping( "/*" );// all the end-points now can be accessible from other domains
    }

    @Override
    public void addResourceHandlers( ResourceHandlerRegistry registry ) {
        super.addResourceHandlers( registry );
        registry.addResourceHandler( "/favicon.ico" ).addResourceLocations( "classpath:/static/" );
        registry.addResourceHandler( "/valour/**" ).addResourceLocations( "classpath:/static/" );
        registry.addResourceHandler( "/webjars/**" ).addResourceLocations( "classpath:/META-INF/resources/webjars/" );
    }

    @Override
    public void addViewControllers( ViewControllerRegistry registry ) {
        super.addViewControllers( registry );
        registry.addRedirectViewController( "/", "/api-console" );
        registry.addViewController( "/api-console" ).setViewName( "index" );
        registry.addViewController( "/docs" ).setViewName( "documentation" );
        registry.addViewController( "/charts" ).setViewName( "charts" );
    }

    @Override
    public void configureMessageConverters( List<HttpMessageConverter<?>> converters ) {
        converters.add( jaksonMessageConverter() );
        converters.add( stringConverter() );
        super.configureMessageConverters( converters );
    }

    @Bean
    public EmbeddedServletContainerCustomizer embeddedServletContainerCustomizer() {

        return new EmbeddedServletContainerCustomizer() {
            private static final String CONTEXT_PATH = "/callidus-api";

            @Override
            public void customize( ConfigurableEmbeddedServletContainer container ) {
                container.setSessionTimeout( 30, TimeUnit.MINUTES );
                int port = 8084;
                try {
                    final String portString = System.getProperty( "server.port" );
                    if ( portString != null ) {
                        port = Integer.parseInt( portString );
                    }

                    LOGGER.info( "Server running on the port {} ", port );
                } catch ( final NumberFormatException e ) {
                    LOGGER.debug( "Error while reading server.port ", e );
                }
                LOGGER.trace( "Customizing embeddedServlet container  using port :{} and contextPath :{}", port, CONTEXT_PATH );
                container.setPort( port );
            }
        };
    }

    @Override
    public void extendMessageConverters( List<HttpMessageConverter<?>> converters ) {
        super.extendMessageConverters( converters );
    }

    @Bean
    public MappingJackson2HttpMessageConverter jaksonMessageConverter() {
        final MappingJackson2HttpMessageConverter jaksonMessageConverter = new MappingJackson2HttpMessageConverter();
        jaksonMessageConverter.setObjectMapper( objectMapper() );
        return jaksonMessageConverter;
    }

    @Bean( name = "messageSource" )
    public ReloadableResourceBundleMessageSource messageSource() {
        final ReloadableResourceBundleMessageSource messageBundle = new ReloadableResourceBundleMessageSource();
        messageBundle.setBasename( "classpath:messages/messages" );
        messageBundle.setUseCodeAsDefaultMessage( true );
        messageBundle.setDefaultEncoding( "UTF-8" );
        return messageBundle;
    }

    @Bean
    public ObjectMapper objectMapper() {
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure( DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false );
        objectMapper.setSerializationInclusion( JsonInclude.Include.NON_NULL );
        objectMapper.configure( SerializationFeature.FAIL_ON_EMPTY_BEANS, false );
        objectMapper.configure( SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true );
        return objectMapper;
    }

    @Bean
    public BufferingClientHttpRequestFactory requestFactory() {
        return new BufferingClientHttpRequestFactory( simpleClientHttpRequestFactory() );
    }

    @Bean
    public SimpleClientHttpRequestFactory simpleClientHttpRequestFactory() {
        final SimpleClientHttpRequestFactory simpleClientHttpRequestFactory = new SimpleClientHttpRequestFactory();
        simpleClientHttpRequestFactory.setReadTimeout( 3000 );
        simpleClientHttpRequestFactory.setConnectTimeout( 3000 );
        simpleClientHttpRequestFactory.setBufferRequestBody( true );
        simpleClientHttpRequestFactory.setOutputStreaming( true );
        return simpleClientHttpRequestFactory;
    }

    @Bean
    public StringHttpMessageConverter stringConverter() {
        final StringHttpMessageConverter stringConverter = new StringHttpMessageConverter( Charset.forName( "UTF-8" ) );
        stringConverter.setSupportedMediaTypes( Arrays.asList( MediaType.TEXT_PLAIN, MediaType.TEXT_HTML, MediaType.APPLICATION_JSON ) );
        return stringConverter;
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    ServletRegistrationBean h2servletRegistration() {
        final ServletRegistrationBean registrationBean = new ServletRegistrationBean( new WebServlet() );
        registrationBean.addUrlMappings( "/console/*" );
        return registrationBean;
    }

}
