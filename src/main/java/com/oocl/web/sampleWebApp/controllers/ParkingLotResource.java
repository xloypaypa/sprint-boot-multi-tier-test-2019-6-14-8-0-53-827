package com.oocl.web.sampleWebApp.controllers;

import com.oocl.web.sampleWebApp.domain.ParkingBoy;
import com.oocl.web.sampleWebApp.domain.ParkingBoyRepository;
import com.oocl.web.sampleWebApp.domain.ParkingLot;
import com.oocl.web.sampleWebApp.domain.ParkingLotRepository;
import com.oocl.web.sampleWebApp.models.ParkingLotResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.function.Predicate;

@RestController
@RequestMapping("/parkinglots")
public class ParkingLotResource {

    @Autowired
    private ParkingLotRepository parkingLotRepository;

    @Autowired
    private ParkingBoyRepository parkingBoyRepository;

    @GetMapping
    public ResponseEntity<ParkingLotResponse[]> getAll() {
        final ParkingLotResponse[] parkingBoys = parkingLotRepository.findAll().stream()
                .map(ParkingLotResponse::create)
                .toArray(ParkingLotResponse[]::new);
        return ResponseEntity.ok(parkingBoys);
    }

    @PutMapping
    public ResponseEntity addNewParkingBoy(@RequestBody ParkingLot parkingLot) {
        if (parkingLot.getCapacity() > 100 || parkingLot.getCapacity() <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        try {
            this.parkingLotRepository.save(parkingLot);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping(value = "/{parkingLotID}/parkingboy")
    public ResponseEntity associate(@PathVariable String parkingLotID, @RequestBody ParkingBoy requestParkingBoy) {
        boolean parkingBoyExist = this.parkingBoyRepository.findAll().stream().anyMatch(parkingBoy -> parkingBoy.getEmployeeId().equals(requestParkingBoy.getEmployeeId()));
        Optional<ParkingLot> optionalParkingLot = this.parkingLotRepository.findAll().stream().filter(parkingLot -> parkingLot.getParkingLotID().equals(parkingLotID)).findFirst();
        if (!parkingBoyExist || !optionalParkingLot.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        ParkingLot parkingLot = optionalParkingLot.get();
        if (parkingLot.getAssociatedParkingBoy() != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        parkingLot.setAssociatedParkingBoy(requestParkingBoy.getEmployeeId());
        this.parkingLotRepository.save(parkingLot);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
