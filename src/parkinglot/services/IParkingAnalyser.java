package parkinglot.services;

import parkinglot.exceptions.ParkingException;
import parkinglot.models.Car;
import java.util.Map;

public interface IParkingAnalyser {
    void analyse(String command, String[] args, Map<Long, Car> parkingLotData) throws ParkingException;
}
