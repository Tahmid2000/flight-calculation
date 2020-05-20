package com.company;

import java.io.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException{

        Scanner scan = new Scanner(System.in);
        System.out.println("What is the directory/file name of the flight data file?");
        String dataFile = scan.nextLine();
        System.out.println("What is the directory/file name of the requested flights file?");
        String requestedFile= scan.nextLine();
        System.out.println("What directory/file would you like to write to?");
        String outputFile= scan.nextLine();

        Scanner flightDat = readFlightData(dataFile);
        Scanner pathsToCalc = readRequestedData(requestedFile);
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));

        //creates list
        AdjacencyList theList = new AdjacencyList();
        theList.createAdjList(flightDat);

        //writes all shortest files to output
        FlightCalculation calc = new FlightCalculation(theList);
        calc.printResults(pathsToCalc, writer);

        System.out.println("\nDone! Check your output file for the results.");

        flightDat.close();
        pathsToCalc.close();
        writer.close();
    }

    //returns scanner for flight data
    public static Scanner readFlightData(String name) {
        String flightDatFileName = name;
        File flightDataFile = new File(flightDatFileName);
        Scanner flightDat;
        try{
            flightDat = new Scanner(flightDataFile);
            return flightDat;
        }
        catch (IOException e) {
            System.out.print("\nProblem: can't create scanner obj from file obj\n");
            return null;
        }
    }

    //returns scanner for requested flights data
    public static Scanner readRequestedData(String name) {
        String pathsToCalculateFileName = name;
        File requestedFile = new File(pathsToCalculateFileName);
        Scanner pathsFile;
        try {
            pathsFile = new Scanner(requestedFile);
            return pathsFile;
        }
        catch (IOException e) {
            System.out.print("\nProblem: can't create scanner obj from file obj (file2)\n");
            return null;
        }
    }
}
