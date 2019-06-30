import parkinglot.Runner;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class TestFileRunner {
    public static void main(String[] args) {
        try {
            String path = System.getProperty("user.dir");
            path = path.substring(0, path.lastIndexOf("/") +1);;
            String pathname = path + "bin/parking_lot_file_inputs.txt";
            Runner.run(new InputStreamReader(new FileInputStream(new File(pathname))));
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
