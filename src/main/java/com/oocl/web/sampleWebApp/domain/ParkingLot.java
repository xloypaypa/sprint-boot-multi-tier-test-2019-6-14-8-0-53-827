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

    @Column(name = "parking_boy_id")
    private String associatedParkingBoy;

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

    public String getAssociatedParkingBoy() {
        return associatedParkingBoy;
    }

    public void setAssociatedParkingBoy(String associatedParkingBoy) {
        this.associatedParkingBoy = associatedParkingBoy;
    }

    protected ParkingLot() {}

    public ParkingLot(String parkingLotID, int capacity, String associatedParkingBoy) {
        this.parkingLotID = parkingLotID;
        this.capacity = capacity;
        this.associatedParkingBoy = associatedParkingBoy;
    }

}
