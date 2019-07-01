package com.oocl.web.sampleWebApp.controllers;

import com.oocl.web.sampleWebApp.domain.ParkingBoy;
import com.oocl.web.sampleWebApp.domain.ParkingBoyRepository;
import com.oocl.web.sampleWebApp.domain.ParkingLot;
import com.oocl.web.sampleWebApp.domain.ParkingLotRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest()
@AutoConfigureMockMvc
public class ParkingBoyResourceTest {

    @Autowired
    private ParkingLotRepository parkingLotRepository;

    @Autowired
    private ParkingBoyRepository parkingBoyRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DirtiesContext
    public void should_add_new_parking_boy_successfully_when_employee_id_not_exist() throws Exception {
        this.mockMvc.perform(put("/parkingboys")
                .contentType(MediaType.APPLICATION_JSON).content("{\"employeeId\":\"1\"}"))
                .andDo(print()).andExpect(status().isCreated());
    }

    @Test
    @DirtiesContext
    public void should_get_bad_request_when_add_parking_boy_with_existing_employee_id() throws Exception {
        this.parkingBoyRepository.save(new ParkingBoy("1"));

        MvcResult mvcResult = this.mockMvc.perform(put("/parkingboys")
                .contentType(MediaType.APPLICATION_JSON).content("{\"employeeId\":\"1\"}")).andReturn();

        assertEquals(400, mvcResult.getResponse().getStatus());
    }

    @Test
    @DirtiesContext
    public void should_can_get_all_parking_boy() throws Exception {
        this.parkingBoyRepository.save(new ParkingBoy("boy"));

        this.mockMvc.perform(get("/parkingboys"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(is("[{\"employeeId\":\"boy\",\"parkingLotIDs\":[]}]")));
    }

    @Test
    @DirtiesContext
    public void should_also_get_associated_parking_lot_when_get_parking_boy() throws Exception {
        this.parkingBoyRepository.save(new ParkingBoy("boy"));
        this.parkingLotRepository.save(new ParkingLot("1", 1, null));
        this.mockMvc.perform(put("/parkinglots/1/parkingboy")
                .contentType(MediaType.APPLICATION_JSON).content("{\"employeeId\":\"boy\"}")).andReturn();

        MvcResult mvcResult = this.mockMvc.perform(get("/parkingboys")).andReturn();

        assertEquals(200, mvcResult.getResponse().getStatus());
        assertEquals("[{\"employeeId\":\"boy\",\"parkingLotIDs\":[\"1\"]}]", mvcResult.getResponse().getContentAsString());
    }
}