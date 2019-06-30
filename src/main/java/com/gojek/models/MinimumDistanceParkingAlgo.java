package com.gojek.models;

import java.util.PriorityQueue;

public class MinimumDistanceParkingAlgo implements ParkingAlgo {
    PriorityQueue<Long> queue;

    public MinimumDistanceParkingAlgo(long slots) {
        this.init(slots);
    }

    private void init(long slots) {
        this.queue = new PriorityQueue<>();
        for (long i = 1; i <= slots; i++) {
            this.queue.add(i);
        }
    }

    @Override
    public void addSlot(long slot) {
        queue.add(slot);
    }

    @Override
    public void removeSlot(long slot) {
        queue.remove(slot);
    }

    @Override
    public Long getNextSlot() {
        return queue.peek();
    }
}
