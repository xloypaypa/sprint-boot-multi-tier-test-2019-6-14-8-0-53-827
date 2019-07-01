package com.oocl.web.sampleWebApp.domain;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class ParkingLot {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "parking_lot_id", length = 64, unique = true, nullable = false)
    private String parkingLotId;

    @Column(name = "capacity")
    private int capacity;

    public Long getId() {
        return id;
    }

    public String getParkingLotId() {
        return parkingLotId;
    }

    public void setParkingLotId(String parkingLotId) {
        this.parkingLotId = parkingLotId;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    protected ParkingLot() {}

    public ParkingLot(String parkingLotId, int capacity) {
        this.parkingLotId = parkingLotId;
        this.capacity = capacity;
    }

}
