package com.gojek;

public class TestFileRunner {
    public static void main(String[] args) {
        try {
            String pathname = "/home/smrutiparikh/dailyhunt_git/parking_lot/parking-lot-1.4.2/parking_lot/functional_spec/fixtures/file_input.txt";
            Runner.main(new String[]{pathname});
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
