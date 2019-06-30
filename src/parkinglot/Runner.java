package parkinglot;

import parkinglot.constants.Constants;
import parkinglot.drivers.InputParser;

import java.io.*;

import parkinglot.exceptions.ParkingException;
import parkinglot.sinks.Sink;
import parkinglot.sinks.StdOutSink;

public class Runner {
    private static Sink logger;
    private static InputParser parser = null;

    static {
        logger = new StdOutSink();
        parser = new InputParser(logger);
    }

    public static void main(String[] args) {
        InputStreamReader in = new InputStreamReader(System.in);
        run(in);
    }

    public static void run(InputStreamReader in) {
        BufferedReader bufferedReader = new BufferedReader(in);
        while (true) {
            try {
                String line = bufferedReader.readLine();
                if(line == null){
                    bufferedReader = new BufferedReader(in);


                        logger.info("------------------------------------------------------\n" +
                                "                  Waiting for Input                   \n" +
                                "------------------------------------------------------\n");
                    continue;
                }
                if(line.contains(".txt")){
                    InputStreamReader fileIn = readFromFile(line);
                    run(fileIn);
                    logger.info("File Read completed!");
                }
                else {
                    if (runCommands(line)) break;
                }
            } catch (Exception e){
                logger.error(e);
            }
        }
    }

    private static InputStreamReader readFromFile(String fileName) throws ParkingException{
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
            logger.error(e);
        }
        return false;
    }
}
