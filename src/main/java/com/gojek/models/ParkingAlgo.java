package com.gojek.models;

public interface ParkingAlgo {
    public void addSlot(long slot);
    public void removeSlot(long slot);
    public Long getNextSlot();
}
