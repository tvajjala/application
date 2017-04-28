package com.tvajjala.resource.test;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

// @RunWith(SpringRunner.class)
// @SpringBootTest
public class EventResourceTest {

    // @InjectMocks
    // EventResource eventResource;

    private MockMvc mockMvc;

    // @Mock
    // EventService eventService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks( this );
        // mockMvc = MockMvcBuilders.standaloneSetup(eventResource).build();
    }

    @Test
    public void eventResourceTest() throws Exception {

        // Mocking method call and saying that do nothing when save method is
        // called on registrationService

        // @formatter:off
        mockMvc.perform( MockMvcRequestBuilders.get( "/events" ) ).andDo( MockMvcResultHandlers.print() )
                .andExpect( MockMvcResultMatchers.status().isOk() ).andExpect( content().contentTypeCompatibleWith( MediaType.APPLICATION_JSON ) );

        // @formatter:on

    }

}
