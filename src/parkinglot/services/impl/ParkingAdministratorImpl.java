package parkinglot.services.impl;

import parkinglot.constants.Constants;
import parkinglot.models.Car;
import parkinglot.models.MinimumDistanceParkingAlgo;
import parkinglot.models.ParkingAlgo;
import parkinglot.services.IParkingAdministrator;
import parkinglot.sinks.Sink;
import parkinglot.exceptions.ParkingException;
import parkinglot.models.ParkingLot;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class ParkingAdministratorImpl implements IParkingAdministrator {
    private ParkingLot parkingLot = null;
    private ParkingAlgo parkingAlgo = null;
    private AtomicLong availableSlotsCounter = null;

    private Sink logger;

    public ParkingAdministratorImpl(Sink logger) {
        this.logger = logger;
    }

    @Override
    public Map<Long, Car> getParkingLotData() throws ParkingException{
        checkIfParkingNotExists();
        checkIfParkingIsEmpty();
        return parkingLot.getParkedCars();
    }

    @Override
    public void status() throws ParkingException {
        checkIfParkingNotExists();
        checkIfParkingIsEmpty();
        try {

            String headLine = "Slot No.<PADDING>Registration No<PADDING>Colour";
            logger.info(parkingLot.status(headLine));
        }catch (Exception e){
            throw new ParkingException(Constants.ERROR_CODES.INTERNAL_ERROR, e);
        }
    }

    @Override
    public void createParkingLot(String arg) throws ParkingException {
        checkIfParkingExists();
        try {
            long capacity = Long.parseLong(arg);
            this.parkingLot = new ParkingLot(capacity);
            this.parkingAlgo = new MinimumDistanceParkingAlgo(capacity);
            this.availableSlotsCounter = new AtomicLong(capacity);
        } catch (Exception e) {
            throw new ParkingException(Constants.ERROR_CODES.INTERNAL_ERROR, e);
        }
        logger.info(Constants.RESPONSE_MESSAGES.PARKING_LOT_CREATED.getMessage(Constants.CAPACITY_VARIABLE, arg));
    }

    @Override
    public void park(String registrationNo, String color) throws ParkingException {
        checkIfParkingNotExists();
        if (availableSlotsCounter.get() == 0) {
            throw new ParkingException(Constants.ERROR_CODES.PARKING_FULL_ERROR);
        }
        try {
            if(checkIfCarAlreadyParked(registrationNo, color)){
                throw new ParkingException(Constants.ERROR_CODES.CAR_ALREADY_PARKED);
            }

            Car car = new Car(registrationNo, color);
            long nextSlot = parkingAlgo.getNextSlot();
            if (nextSlot == 0) {
                throw new ParkingException(Constants.ERROR_CODES.SLOT_NOT_FOUND);
            }
            parkingLot.getParkedCars().put(nextSlot, car);
            availableSlotsCounter.decrementAndGet();
            parkingAlgo.removeSlot(nextSlot);

            logger.info(Constants.RESPONSE_MESSAGES.PARKING_DONE.getMessage(Constants.SLOT_NUMBER_VARIABLE, nextSlot));
        } catch (Exception e) {
            throw new ParkingException(Constants.ERROR_CODES.INTERNAL_ERROR, e);
        }
    }

    @Override
    public void leave(String arg) throws ParkingException {
        checkIfParkingNotExists();
        checkIfParkingIsEmpty();
        try {
            long slot = Long.parseLong(arg);
            if(!parkingLot.getParkedCars().containsKey(slot)){
                throw new ParkingException(Constants.ERROR_CODES.SLOT_NOT_OCCUPIED);
            }
            parkingLot.getParkedCars().remove(slot);
            parkingAlgo.addSlot(slot);
            availableSlotsCounter.incrementAndGet();

            logger.info(Constants.RESPONSE_MESSAGES.PARKING_VACANTED.getMessage(Constants.SLOT_NUMBER_VARIABLE, slot));
        } catch (Exception e) {
            throw new ParkingException(Constants.ERROR_CODES.INTERNAL_ERROR, e);
        }
    }

    private void checkIfParkingExists() throws ParkingException {
        if (Objects.nonNull(parkingLot) && Objects.nonNull(parkingAlgo) && Objects.nonNull(availableSlotsCounter)) {
            throw new ParkingException(Constants.ERROR_CODES.PARKING_LOT_ALREADY_EXISTS);
        }
    }

    private void checkIfParkingIsEmpty() throws ParkingException {
        if (parkingLot.isEmpty() || availableSlotsCounter.get() == parkingLot.getCapacity()) {
            throw new ParkingException(Constants.ERROR_CODES.PARKING_EMPTY_ERROR);
        }
    }

    private void checkIfParkingNotExists() throws ParkingException {
        if (Objects.isNull(parkingLot) || Objects.isNull(parkingAlgo)) {
            throw new ParkingException(Constants.ERROR_CODES.PARKING_LOT_DOES_NOT_EXISTS);
        }
    }

    private boolean checkIfCarAlreadyParked(String registrationNo, String color) throws ParkingException {
        Optional<Map.Entry<Long, Car>> entry = parkingLot.getParkedCars().entrySet().stream()
                .filter(e -> e.getValue().getColor().equalsIgnoreCase(color) && e.getValue().getRegistrationNumber().equalsIgnoreCase(registrationNo))
                .findAny();
        return entry.isPresent();
    }
}
