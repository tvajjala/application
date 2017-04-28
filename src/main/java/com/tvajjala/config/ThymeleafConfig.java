package com.tvajjala.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.resourceresolver.SpringResourceResourceResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

import nz.net.ultraq.thymeleaf.LayoutDialect;

/**
 * @author ThirupathiReddy V
 */
@Configuration
public class ThymeleafConfig {

    @Bean
    public TemplateResolver templateResolver() {
        // SpringResourceTemplateResolver resolver=new SpringResourceTemplateResolver();
        final TemplateResolver resolver = new TemplateResolver();
        resolver.setResourceResolver( thymeleafResourceResolver() );
        resolver.setPrefix( "classpath:/views/" );
        resolver.setSuffix( ".html" );
        resolver.setTemplateMode( "LEGACYHTML5" );
        // LEGACYHTML5 requires nekohtml parser
        resolver.setCharacterEncoding( "UTF-8" );
        resolver.setCacheable( false );
        resolver.setOrder( 1 );
        return resolver;
    }

    @Bean
    public SpringResourceResourceResolver thymeleafResourceResolver() {
        return new SpringResourceResourceResolver();
    }

    @Bean
    public SpringTemplateEngine templateEngine() {
        final SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver( templateResolver() );
        templateEngine.addDialect( new LayoutDialect() );
        return templateEngine;
    }

    @Bean
    public ThymeleafViewResolver thymeleafViewResolver() {
        final ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine( templateEngine() );
        return resolver;
    }

}
