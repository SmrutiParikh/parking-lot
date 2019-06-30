package com.gojek.services;

import com.gojek.exceptions.ParkingException;
import com.gojek.models.Car;
import java.util.Map;

public interface IParkingAdministrator {
    public Map<Long, Car> getParkingLotData() throws ParkingException;
    public void status() throws ParkingException;
    public void createParkingLot(String capacity) throws ParkingException;
    public void park(String registrationNo, String color) throws ParkingException;
    public void leave(String slot) throws ParkingException;
}
