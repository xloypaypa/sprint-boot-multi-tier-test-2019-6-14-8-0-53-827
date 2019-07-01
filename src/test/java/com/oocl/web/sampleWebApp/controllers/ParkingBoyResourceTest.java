package com.oocl.web.sampleWebApp.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest()
@AutoConfigureMockMvc
public class ParkingBoyResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DirtiesContext
    public void should_add_new_parking_boy() throws Exception {
        this.mockMvc.perform(put("/parkingboys")
                .contentType(MediaType.APPLICATION_JSON).content("{\"employeeId\":\"1\"}"))
                .andDo(print()).andExpect(status().isCreated());
    }
}