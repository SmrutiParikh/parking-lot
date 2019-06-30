package com.gojek;

import com.gojek.constants.Constants;
import com.gojek.drivers.InputParser;
import com.gojek.exceptions.ParkingException;
import com.gojek.sinks.Sink;
import com.gojek.sinks.StdOutSink;

import java.io.*;


public class Runner {
    private static Sink logger;
    private static InputParser parser = null;

    static {
        logger = new StdOutSink();
        parser = new InputParser(logger);
    }

    public static void main(String[] args) {
        InputStreamReader in;
        if(args.length > 0){
            try {
                in = new InputStreamReader(new FileInputStream(new File(args[0])));
            }catch (Exception e){
                logger.error(Constants.ERROR_CODES.INVALID_FILE.getMessage(), new ParkingException(e));
                in = new InputStreamReader(System.in);
            }
        }
        else{
            in = new InputStreamReader(System.in);
        }
        run(in);
    }

    public static void run(InputStreamReader in) {
        BufferedReader bufferedReader = new BufferedReader(in);
        String line;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                if (line.contains(".txt")) {
                    InputStreamReader fileIn = readFromFile(line);
                    //logger.info("\n-----------Input from file : " + line +"-----------\n");
                    run(fileIn);
                    //logger.info("\n-----------File Read completed!-----------\n");
                } else {
                    if (runCommands(line)) break;
                }
            }
        } catch (Exception e){
                //logger.error(e);
        }
    }

    private static InputStreamReader readFromFile(String fileName) throws ParkingException {
        try {
            return new InputStreamReader(new FileInputStream(new File(fileName)));
        }
        catch (Exception ex){
            throw new ParkingException(Constants.ERROR_CODES.INVALID_FILE);
        }
    }

    private static boolean runCommands(String line) throws ParkingException {
        try {
            line = line.trim();
            if (Constants.COMMAND_EXIT.equalsIgnoreCase(line)) {
                return true;
            }
            parser.parseAndExecute(line);
        }catch (ParkingException e){
            //logger.error(e);
        }
        return false;
    }
}
