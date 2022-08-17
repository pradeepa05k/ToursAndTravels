package com.toursandtravels.model;

import java.sql.Timestamp;
import java.util.List;
import java.math.BigDecimal;

public class Bus {
    private String busId;
    private String busName;
    private String busType;
    private String arrivalTime;
    private String departureTime;
//    private List<BusTiming> boardingPoint;
//    private List<BusTiming> droppingPoint;
    private int noOfSeats;
    private BigDecimal busFare;
    private String duration;
    private String userCreated;
    private String userUpdated;
    private Timestamp dateCreated;
    private Timestamp dateUpdated;

    public String getBusId() {
        return busId;
    }

    public void setBusId(String busId) {
        this.busId = busId;
    }

    public String getBusName() {
        return busName;
    }

    public void setBusName(String busName) {
        this.busName = busName;
    }

    public String getBusType() {
        return busType;
    }

    public void setBusType(String busType) {
        this.busType = busType;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

//    public List<BusTiming> getBoardingPoint() {
//        return boardingPoint;
//    }
//
//    public void setBoardingPoint(List<BusTiming> boardingPoint) {
//        this.boardingPoint = boardingPoint;
//    }
//
//    public List<BusTiming> getDroppingPoint() {
//        return droppingPoint;
//    }
//
//    public void setDroppingPoint(List<BusTiming> droppingPoint) {
//        this.droppingPoint = droppingPoint;
//    }

    public int getNoOfSeats() {
        return noOfSeats;
    }

    public void setNoOfSeats(int noOfSeats) {
        this.noOfSeats = noOfSeats;
    }

    public BigDecimal getBusFare() {
        return busFare;
    }

    public void setBusFare(BigDecimal busFare) {
        this.busFare = busFare;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getUserCreated() {
        return userCreated;
    }

    public void setUserCreated(String userCreated) {
        this.userCreated = userCreated;
    }

    public String getUserUpdated() {
        return userUpdated;
    }

    public void setUserUpdated(String userUpdated) {
        this.userUpdated = userUpdated;
    }

    public Timestamp getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Timestamp getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(Timestamp dateUpdated) {
        this.dateUpdated = dateUpdated;
    }
}
