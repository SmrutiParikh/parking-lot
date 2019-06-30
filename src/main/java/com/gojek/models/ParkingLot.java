package com.gojek.models;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ParkingLot {
    private long capacity;
    private Map<Long, Car> parkedCars;

    public ParkingLot(long slots) {
        this.capacity = slots;
        this.parkedCars = new HashMap<>();
    }

    public long getCapacity() {
        return capacity;
    }

    public void setCapacity(long capacity) {
        this.capacity = capacity;
    }

    public Map<Long, Car> getParkedCars() {
        return parkedCars;
    }

    public void setParkedCars(Map<Long, Car> parkedCars) {
        this.parkedCars = parkedCars;
    }

    public boolean isEmpty(){
        return Objects.isNull(this.parkedCars) || this.parkedCars.isEmpty();
    }

    public String status(String headline){
        //int pad = 5;
        //String padding = String.format("%-"+5+"s", "");
        StringBuilder stringBuilder = new StringBuilder(headline);//.replace("<PADDING>", padding));
        for(Map.Entry<Long, Car> carEntry : parkedCars.entrySet()){
            //String padding1 = String.format("%-" + (pad + 8 - carEntry.getKey().toString().trim().length()) + "s", padding);
            //String padding2 = String.format("%-" + (pad + 15 - carEntry.getValue().getRegistrationNumber().length()) + "s", padding);
            stringBuilder
                    .append(System.lineSeparator())
                    .append(carEntry.getKey())
                    .append("           ")
                    .append(carEntry.getValue().getRegistrationNumber())
                    .append("      ")
                    .append(carEntry.getValue().getColor());
        }

        return stringBuilder.toString();
    }
}
