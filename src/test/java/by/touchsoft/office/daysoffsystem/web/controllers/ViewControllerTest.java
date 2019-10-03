package by.touchsoft.office.daysoffsystem.web.controllers;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ViewControllerTest {

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new ViewController()).build();
    }

    @Test
    public void testHealthCheck() throws Exception {
        this.mockMvc.perform(get("/healthCheck"))
                .andExpect(status().isOk());
    }
}
