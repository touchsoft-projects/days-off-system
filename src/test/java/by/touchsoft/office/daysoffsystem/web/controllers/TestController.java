package by.touchsoft.office.daysoffsystem.web.controllers;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TestController {

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new Controller()).build();
    }

    @Test
    public void testDaysoffTestResultOK() throws Exception {
        this.mockMvc.perform(get("/daysoff/healthCheck"))
                .andExpect(status().isOk());
    }
}
