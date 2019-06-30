package parkinglot.sinks;

import parkinglot.exceptions.ParkingException;

public class StdOutSink implements Sink{

    @Override
    public void info(String message){
        System.out.println(message);
    }

    @Override
    public void error(String message, ParkingException exception){
        System.out.println(message);
        System.out.println(exception.getMessage());
    }

    @Override
    public void error(Exception exception){
        System.out.println(exception.getMessage());
    }
}
