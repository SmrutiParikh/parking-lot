package parkinglot;

import parkinglot.constants.Constants;
import parkinglot.drivers.InputParser;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;

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
                    continue;
                }
                if(line.contains(".txt")){
                   bufferedReader = readFromFile(line);
                }
                else {
                    if (runCommands(line)) break;
                }
            } catch (Exception e){
                logger.error(e);
            }
        }
    }

    private static BufferedReader readFromFile(String fileName) throws ParkingException{
        try {
            return new BufferedReader(new FileReader(new File(fileName)));
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
