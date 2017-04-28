package com.tvajjala;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner.Mode;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.PropertySource;

import com.tvajjala.persistence.model.Product;
import com.tvajjala.service.ProductService;

/**
 * Application describes most of the features of spring boot
 *
 * @author ThirupathiReddy V
 */
@SpringBootApplication
@PropertySource( value = "classpath:application.yml" )
public class MainApplication extends SpringBootServletInitializer implements CommandLineRunner {

    /** Reference to logger */
    private static final Logger LOG = LoggerFactory.getLogger( MainApplication.class );

    @Autowired
    ProductService productService;

    public static void main( String[] args ) throws Exception {

        // NOTE:: If you enable redis profile you must start redis DB server externally
        // new SpringApplicationBuilder().sources( MainApplication.class).profiles("redis").run(args);

        System.setProperty( "spring.profiles.active", "hazelcast" );

        LOG.info( "Running Spring boot application with profile :  {}", System.getProperty( "spring.profiles.active" ) );
        final SpringApplication application = new SpringApplication( MainApplication.class );
        final Properties properties = new Properties();
        application.setBannerMode( Mode.CONSOLE );
        application.setDefaultProperties( properties );
        application.run();
    }

    @Override
    public void run( String... args ) throws Exception {

        LOG.info( "Seed data can be handled from here" );

        final List<Product> products = Arrays.asList( new Product( "US12345678", "IPad", "New Ipad", 800.34 ),
                new Product( "US12345679", "IPadMini", "Refurbished Ipad mini", 700.34 ), new Product( "US12345677", "Iphone5", "Iphone", 200.34 ),
                new Product( "US12345676", "Iphone6", "Refurbished Iphone", 444.34 ) );

        for ( final Product product : products ) {
            productService.createProduct( product );
        }

    }

}
