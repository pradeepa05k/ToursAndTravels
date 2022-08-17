package com.toursandtravels.model;

import java.util.Date;
import java.util.List;

public class Passenger {
    private String passengerId;
    private String busId;
    private String seatId;
    private String passengerName;
    private String gender;
    private int age;
    private boolean isBooked;
    private String email_Id;
    private String mobileNo;
//    private List<BusTiming> boardingPoint;
//    private List<BusTiming> droppingPoint;
    private Date dateOfJourney;
    private String userCreated;
    private String userUpdated;


    public String getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(String passengerId) {
        this.passengerId = passengerId;
    }

    public String getBusId() {
        return busId;
    }

    public void setBusId(String busId) {
        this.busId = busId;
    }

    public String getSeatId() {
        return seatId;
    }

    public void setSeatId(String seatId) {
        this.seatId = seatId;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public void setBooked(boolean booked) {
        isBooked = booked;
    }

    public String getEmail_Id() {
        return email_Id;
    }

    public void setEmail_Id(String email_Id) {
        this.email_Id = email_Id;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
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

    public Date getDateOfJourney() {
        return dateOfJourney;
    }

    public void setDateOfJourney(Date dateOfJourney) {
        this.dateOfJourney = dateOfJourney;
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
}
