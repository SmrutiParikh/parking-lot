package parkinglot.drivers;

import parkinglot.constants.Constants;
import parkinglot.services.IParkingAdministrator;
import parkinglot.services.impl.ParkingAnalyserImpl;
import parkinglot.exceptions.ParkingException;
import parkinglot.services.IParkingAnalyser;
import parkinglot.services.impl.ParkingAdministratorImpl;
import parkinglot.sinks.Sink;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class InputParser {
    private IParkingAdministrator parkingAdministrator;
    private IParkingAnalyser parkingAnalyser;
    public static Map<String, Integer> COMMAND_TO_PARAM_COUNT = new HashMap<>();

    public InputParser(Sink sink) {
        parkingAdministrator = new ParkingAdministratorImpl(sink);
        parkingAnalyser = new ParkingAnalyserImpl(sink);
        initCommandToParamsMap();
    }

    private void initCommandToParamsMap() {
        COMMAND_TO_PARAM_COUNT.put(Constants.COMMAND_CREATE, 1);
        COMMAND_TO_PARAM_COUNT.put(Constants.COMMAND_EXIT, 0);
        COMMAND_TO_PARAM_COUNT.put(Constants.COMMAND_STATUS, 0);
        COMMAND_TO_PARAM_COUNT.put(Constants.COMMAND_PARK, 2);
        COMMAND_TO_PARAM_COUNT.put(Constants.COMMAND_LEAVE, 1);
        COMMAND_TO_PARAM_COUNT.put(Constants.COMMAND_ANALYSE, 1);
        COMMAND_TO_PARAM_COUNT = Collections.unmodifiableMap(COMMAND_TO_PARAM_COUNT);
    }

    public void parseAndExecute(String line) throws ParkingException{
        String[] split = line.split(" ");
        String commandName = split[0].trim();
        String[] args = Arrays.copyOfRange(split, 1, split.length);
        if(Pattern.compile(Constants.COMMAND_ANALYSE).matcher(commandName).find()){
            commandName = Constants.COMMAND_ANALYSE;
        }

        if (COMMAND_TO_PARAM_COUNT.containsKey(commandName) && COMMAND_TO_PARAM_COUNT.get(commandName) != args.length) {
            throw new ParkingException(Constants.ERROR_CODES.INVALID_INPUT_ARGS);
        }

        switch (commandName) {
            case Constants.COMMAND_EXIT :
                System.exit(1);
            case Constants.COMMAND_CREATE :
                parkingAdministrator.createParkingLot(args[0]);
                break;
            case Constants.COMMAND_STATUS :
                parkingAdministrator.status();
                break;
            case Constants.COMMAND_PARK :
                parkingAdministrator.park(args[0], args[1]);
                break;
            case Constants.COMMAND_LEAVE :
                parkingAdministrator.leave(args[0]);
                break;
            case Constants.COMMAND_ANALYSE:
                parkingAnalyser.analyse(split[0].trim(), args, parkingAdministrator.getParkingLotData());
                break;
            default:
                throw new ParkingException(Constants.ERROR_CODES.INVALID_INPUT_PARSE);

        }
    }


}
