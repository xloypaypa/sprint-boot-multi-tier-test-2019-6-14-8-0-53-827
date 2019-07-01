package com.oocl.web.sampleWebApp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.oocl.web.sampleWebApp.domain.ParkingLot;

import java.util.Objects;

public class ParkingLotResponse {
    private String parkingLotID;
    private int availablePositionCount, capacity;

    public static ParkingLotResponse create(String parkingLotID, int availablePositionCount, int capacity) {
        Objects.requireNonNull(parkingLotID);

        final ParkingLotResponse response = new ParkingLotResponse();
        response.setParkingLotID(parkingLotID);
        response.setAvailablePositionCount(availablePositionCount);
        response.setCapacity(capacity);
        return response;
    }

    public static ParkingLotResponse create(ParkingLot entity) {
        return create(entity.getParkingLotID(), entity.getCapacity(), entity.getCapacity());
    }

    @JsonIgnore
    public boolean isValid() {
        return parkingLotID != null;
    }

    public String getParkingLotID() {
        return parkingLotID;
    }

    public void setParkingLotID(String parkingLotID) {
        this.parkingLotID = parkingLotID;
    }

    public int getAvailablePositionCount() {
        return availablePositionCount;
    }

    public void setAvailablePositionCount(int availablePositionCount) {
        this.availablePositionCount = availablePositionCount;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
