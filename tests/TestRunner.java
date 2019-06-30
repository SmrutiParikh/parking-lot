import parkinglot.Runner;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class TestRunner {
    public static void main(String[] args) {
        try {
            Runner.run(new InputStreamReader(new FileInputStream(new File("bin/parking_lot_file_inputs.txt"))));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
