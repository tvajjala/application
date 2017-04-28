package com.tvajjala.resource.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.tvajjala.MainApplication;

/**
 * @author ThirupathiReddy V
 */
@RunWith( SpringRunner.class )
@SpringBootTest( classes = MainApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT )
public class WebEndpointTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void getEventsTest() {
        final ResponseEntity<String> response = testRestTemplate.getForEntity( "/metrics", String.class );
        System.err.println( "Json Response " + response.getBody() );
    }

}
