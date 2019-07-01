package com.oocl.web.sampleWebApp.domain;

import javax.persistence.*;

@Entity
@Table(name = "parking_lot")
public class ParkingLot {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "parking_lot_id", length = 64, unique = true, nullable = false)
    private String parkingLotID;

    @Column(name = "capacity")
    private int capacity;

    public Long getId() {
        return id;
    }

    public String getParkingLotID() {
        return parkingLotID;
    }

    public void setParkingLotID(String parkingLotID) {
        this.parkingLotID = parkingLotID;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    protected ParkingLot() {}

    public ParkingLot(String parkingLotID, int capacity) {
        this.parkingLotID = parkingLotID;
        this.capacity = capacity;
    }

}
