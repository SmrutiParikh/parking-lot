package parkinglot.constants;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

public class Constants {
    public static final String COMMAND_EXIT = "exit";
    public static final String COMMAND_CREATE = "create_parking_lot";
    public static final String COMMAND_STATUS = "status";
    public static final String COMMAND_PARK ="park";
    public static final String COMMAND_LEAVE ="leave";
    public static final String COMMAND_ANALYSE ="\\s*_for_\\s*";

    public static enum ANALYSE_PARAMS{
        REGISTRATION_NUMBER("registration_number"),
        COLOR("colour"),
        SLOT_NUMBER("slot_number");

        private String name;

        private ANALYSE_PARAMS(String param) {
            this.name = param;
        }

        public String getName() {
            return name;
        }
    }


    public static enum ERROR_CODES {
        PARKING_FULL_ERROR("Sorry, parking lot is full"),
        PARKING_EMPTY_ERROR("Sorry, parking lot is empty"),
        PARKING_LOT_ALREADY_EXISTS("Sorry, parking lot is already created"),
        PARKING_LOT_DOES_NOT_EXISTS("Sorry, parking lot does not exist"),
        CAR_ALREADY_PARKED("Sorry, car is already parked"),
        CAR_NOT_FOUND("Not found"),
        SLOT_NOT_FOUND("Slot Not found"),
        SLOT_NOT_OCCUPIED("Slot Not Occupied"),
        INVALID_INPUT_ARGS("Not valid input, incorrect args"),
        INVALID_INPUT_PARSE("Not valid input, not parsable input"),
        INVALID_FILE("Not valid file"),
        INTERNAL_ERROR("Internal Error");

        private String message = "";

        private ERROR_CODES(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

    public static enum RESPONSE_MESSAGES {
        PARKING_LOT_CREATED("Created a parking lot with <CAPACITY> slots"),
        PARKING_DONE("Allocated slot number: <SLOT_NUMBER>"),
        PARKING_VACANTED("Slot number <SLOT_NUMBER> is free");

        private String message;

        private RESPONSE_MESSAGES(String message) {
            this.message = message;
        }

        public String getMessage(String variable, Object value) {
            return message.replace(variable, Objects.toString(value));
        }
    }

    public static final String CAPACITY_VARIABLE = "<CAPACITY>";
    public static final String SLOT_NUMBER_VARIABLE = "<SLOT_NUMBER>";

    public static final String FOR_PATTERN = "_for_";
    public static final String WITH_PATTERN = "_with_";
}
