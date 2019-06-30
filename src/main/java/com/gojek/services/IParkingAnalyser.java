package com.gojek.services;

import com.gojek.exceptions.ParkingException;
import com.gojek.models.Car;
import java.util.Map;

public interface IParkingAnalyser {
    void analyse(String command, String[] args, Map<Long, Car> parkingLotData) throws ParkingException;
}
