package com.gojek.drivers;

import com.gojek.constants.Constants;
import com.gojek.exceptions.ParkingException;
import com.gojek.services.IParkingAdministrator;
import com.gojek.services.IParkingAnalyser;
import com.gojek.services.impl.ParkingAdministratorImpl;
import com.gojek.sinks.Sink;
import com.gojek.services.impl.ParkingAnalyserImpl;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class InputParser {
    private IParkingAdministrator parkingAdministrator;
    private IParkingAnalyser parkingAnalyser;
    public static Map<String, Integer> commandToParamCount = new HashMap<>();

    public InputParser(Sink sink) {
        parkingAdministrator = new ParkingAdministratorImpl(sink);
        parkingAnalyser = new ParkingAnalyserImpl(sink);
        initCommandToParamsMap();
    }

    private void initCommandToParamsMap() {
        commandToParamCount.put(Constants.COMMAND_CREATE, 1);
        commandToParamCount.put(Constants.COMMAND_EXIT, 0);
        commandToParamCount.put(Constants.COMMAND_STATUS, 0);
        commandToParamCount.put(Constants.COMMAND_PARK, 2);
        commandToParamCount.put(Constants.COMMAND_LEAVE, 1);
        commandToParamCount.put(Constants.COMMAND_ANALYSE, 1);
        commandToParamCount = Collections.unmodifiableMap(commandToParamCount);
    }

    public void parseAndExecute(String line) throws ParkingException {
        String[] split = line.split(" ");
        String commandName = split[0].trim();
        String[] args = Arrays.copyOfRange(split, 1, split.length);
        if(Pattern.compile(Constants.COMMAND_ANALYSE).matcher(commandName).find()){
            commandName = Constants.COMMAND_ANALYSE;
        }

        if (commandToParamCount.containsKey(commandName) && commandToParamCount.get(commandName) != args.length) {
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
