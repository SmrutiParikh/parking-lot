package parkinglot.services;

import parkinglot.exceptions.ParkingException;
import parkinglot.models.Car;
import java.util.Map;

public interface IParkingAdministrator {
    public Map<Long, Car> getParkingLotData() throws ParkingException;
    public void status() throws ParkingException;
    public void createParkingLot(String capacity) throws ParkingException;
    public void park(String registrationNo, String color) throws ParkingException;
    public void leave(String slot) throws ParkingException;
}
