package com.gojek.services.impl;

import com.gojek.constants.Constants;
import com.gojek.exceptions.ParkingException;
import com.gojek.models.Car;
import com.gojek.services.IParkingAnalyser;
import com.gojek.sinks.Sink;

import java.util.Map;
import java.util.stream.Collectors;

public class ParkingAnalyserImpl implements IParkingAnalyser {
    private Sink logger;

    public ParkingAnalyserImpl(Sink logger) {
        this.logger = logger;
    }

    public String execute(Map<Long, Car> data, String mapper, String entity, String filter, String[] filterValue) throws ParkingException {
        if(!isListedParam(mapper) || !isListedParam(filter) ){
            throw new ParkingException(Constants.ERROR_CODES.INVALID_INPUT_ARGS);
        }
        if (null == entity || "cars".equalsIgnoreCase(entity)) {
            try {

                return data.entrySet().stream()
                        .filter(e -> null != e.getValue())
                        .filter(e -> {
                                    if (filter.contains(Constants.ANALYSE_PARAMS.COLOR.getName())) {
                                        return e.getValue().getColor().equalsIgnoreCase(filterValue[0]);
                                    }
                                    if (filter.contains(Constants.ANALYSE_PARAMS.REGISTRATION_NUMBER.getName())) {
                                        return e.getValue().getRegistrationNumber().equalsIgnoreCase(filterValue[0]);
                                    }
                                    return filter.contains(Constants.ANALYSE_PARAMS.SLOT_NUMBER.getName()) && e.getKey().toString().equals(filterValue[0]);
                                }
                        )
                        .map(e -> {
                            if (mapper.contains(Constants.ANALYSE_PARAMS.COLOR.getName())) {
                                return e.getValue().getColor();
                            }
                            if (mapper.contains(Constants.ANALYSE_PARAMS.REGISTRATION_NUMBER.getName())) {
                                return e.getValue().getRegistrationNumber();
                            }
                            return e.getKey().toString();
                        })
                        .collect(Collectors.joining(", "));
            }
            catch (Exception e){
                throw new ParkingException(Constants.ERROR_CODES.INTERNAL_ERROR, e);
            }
        }
        throw new ParkingException(Constants.ERROR_CODES.CAR_NOT_FOUND);
    }

    @Override
    public void analyse(String command, String[] args, Map<Long, Car> parkingLotData) throws ParkingException {
        int forIndex = command.indexOf(Constants.FOR_PATTERN);
        boolean containsWith = command.contains(Constants.WITH_PATTERN);
        int withIndex = containsWith ? command.indexOf(Constants.WITH_PATTERN) : forIndex;
        String output = command.substring(0, forIndex);
        String entity = containsWith ? command.substring(forIndex + Constants.FOR_PATTERN.length(), withIndex) : null;
        String filter = command.substring(withIndex + (containsWith ? Constants.WITH_PATTERN.length() : Constants.FOR_PATTERN.length()), command.length());

        String result = execute(parkingLotData, output, entity, filter, args);
        if(result.trim().isEmpty() ){
            throw new ParkingException(Constants.ERROR_CODES.CAR_NOT_FOUND);
        }
        logger.info(result);
    }

    public boolean isListedParam(String externalParam){
        int occurrence = 0;
        for( Constants.ANALYSE_PARAMS analyse_param : Constants.ANALYSE_PARAMS.values()){
            if(externalParam.contains(analyse_param.getName())){
                occurrence++;
            }
        }
        return occurrence > 0;
    }
}
