package com.oocl.web.sampleWebApp.controllers;

import com.oocl.web.sampleWebApp.domain.ParkingBoy;
import com.oocl.web.sampleWebApp.domain.ParkingBoyRepository;
import com.oocl.web.sampleWebApp.domain.ParkingLot;
import com.oocl.web.sampleWebApp.domain.ParkingLotRepository;
import com.oocl.web.sampleWebApp.models.ParkingLotResponse;
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

import static com.oocl.web.sampleWebApp.WebTestUtil.getContentAsObject;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest()
@AutoConfigureMockMvc
public class ParkingLotResourceTest {

    @Autowired
    private ParkingLotRepository parkingLotRepository;

    @Autowired
    private ParkingBoyRepository parkingBoyRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DirtiesContext
    public void should_add_new_parking_lot() throws Exception {
        this.mockMvc.perform(put("/parkinglots")
                .contentType(MediaType.APPLICATION_JSON).content("{\"parkingLotID\":\"1\", \"capacity\":1}"))
                .andDo(print()).andExpect(status().isCreated());
    }

    @Test
    @DirtiesContext
    public void should_get_400_when_capacity_is_not_in_1_to_100() throws Exception {
        this.mockMvc.perform(put("/parkinglots")
                .contentType(MediaType.APPLICATION_JSON).content("{\"parkingLotID\":\"1\", \"capacity\":101}"))
                .andDo(print()).andExpect(status().isBadRequest());
    }

    @Test
    @DirtiesContext
    public void should_get_400_when_the_parking_lot_id_already_existing() throws Exception {
        this.parkingLotRepository.save(new ParkingLot("1", 1, null));

        MvcResult mvcResult = this.mockMvc.perform(put("/parkinglots")
                .contentType(MediaType.APPLICATION_JSON).content("{\"parkingLotID\":\"1\", \"capacity\":2}")).andReturn();

        assertEquals(400, mvcResult.getResponse().getStatus());
    }

    @Test
    @DirtiesContext
    public void should_can_get_all_parking_lot() throws Exception {
        this.parkingLotRepository.save(new ParkingLot("1", 1, null));

        MvcResult mvcResult = this.mockMvc.perform(get("/parkinglots")).andReturn();

        assertEquals(200, mvcResult.getResponse().getStatus());
        final ParkingLotResponse[] parkingLots = getContentAsObject(mvcResult, ParkingLotResponse[].class);
        assertEquals(1, parkingLots.length);
        assertEquals("1", parkingLots[0].getParkingLotID());
        assertEquals(1, parkingLots[0].getCapacity());
    }

    @Test
    @DirtiesContext
    public void should_can_associate_parking_lot_to_parking_boy() throws Exception {
        this.parkingBoyRepository.save(new ParkingBoy("boy"));
        this.parkingLotRepository.save(new ParkingLot("1", 1, null));

        MvcResult mvcResult = this.mockMvc.perform(put("/parkinglots/1/parkingboy")
                .contentType(MediaType.APPLICATION_JSON).content("{\"employeeId\":\"boy\"}")).andReturn();

        assertEquals(201, mvcResult.getResponse().getStatus());
    }

    @Test
    @DirtiesContext
    public void should_get_bad_request_when_parking_lot_already_associated() throws Exception {
        this.parkingBoyRepository.save(new ParkingBoy("boy"));
        this.parkingLotRepository.save(new ParkingLot("1", 1, "1"));

        MvcResult mvcResult = this.mockMvc.perform(put("/parkinglots/1/parkingboy")
                .contentType(MediaType.APPLICATION_JSON).content("{\"employeeId\":\"boy\"}")).andReturn();

        assertEquals(400, mvcResult.getResponse().getStatus());
    }

    @Test
    @DirtiesContext
    public void should_get_bad_request_when_parking_lot_not_exist() throws Exception {
        this.parkingBoyRepository.save(new ParkingBoy("boy"));

        MvcResult mvcResult = this.mockMvc.perform(put("/parkinglots/1/parkingboy")
                .contentType(MediaType.APPLICATION_JSON).content("{\"employeeId\":\"boy\"}")).andReturn();

        assertEquals(400, mvcResult.getResponse().getStatus());
    }

    @Test
    @DirtiesContext
    public void should_get_bad_request_when_parking_boy_not_exist() throws Exception {
        this.parkingLotRepository.save(new ParkingLot("1", 1, null));

        MvcResult mvcResult = this.mockMvc.perform(put("/parkinglots/1/parkingboy")
                .contentType(MediaType.APPLICATION_JSON).content("{\"employeeId\":\"boy\"}")).andReturn();

        assertEquals(400, mvcResult.getResponse().getStatus());
    }

}