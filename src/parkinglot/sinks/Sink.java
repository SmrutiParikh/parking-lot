package parkinglot.sinks;

import parkinglot.exceptions.ParkingException;

public interface Sink {
    public void info(String message);
    public void error(String message, ParkingException exception);
    void error(Exception exception);
}
