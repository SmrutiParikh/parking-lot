package parkinglot.exceptions;

import parkinglot.constants.Constants;

public class ParkingException extends Exception {
    public ParkingException(String s, Exception e) {
        super(s, e);
    }

    public ParkingException(Exception ex) {
        super(ex.getMessage(), ex);
    }

    public ParkingException(Constants.ERROR_CODES errorCodes) {
        super(errorCodes.getMessage(), new Exception(errorCodes.getMessage()));
    }

    public ParkingException(Constants.ERROR_CODES errorCodes, Exception e) {
        super(errorCodes.getMessage(), e);
    }
}

