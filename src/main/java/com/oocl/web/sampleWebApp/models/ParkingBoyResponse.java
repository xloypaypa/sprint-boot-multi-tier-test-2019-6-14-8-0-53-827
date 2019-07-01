package com.oocl.web.sampleWebApp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.oocl.web.sampleWebApp.domain.ParkingBoy;

import java.util.Objects;

public class ParkingBoyResponse {
    private String employeeId;

    private String[] parkingLotIDs;

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String[] getParkingLotIDs() {
        return parkingLotIDs;
    }

    public void setParkingLotIDs(String[] parkingLotIDs) {
        this.parkingLotIDs = parkingLotIDs;
    }

    public static ParkingBoyResponse create(String employeeId, String[] parkingLotIDs) {
        Objects.requireNonNull(employeeId);

        final ParkingBoyResponse response = new ParkingBoyResponse();
        response.setEmployeeId(employeeId);
        response.setParkingLotIDs(parkingLotIDs);
        return response;
    }

    public static ParkingBoyResponse create(ParkingBoy entity, String[] parkingLots) {
        return create(entity.getEmployeeId(), parkingLots);
    }

    @JsonIgnore
    public boolean isValid() {
        return employeeId != null;
    }
}
