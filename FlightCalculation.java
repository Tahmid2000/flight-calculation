package com.company;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.TreeSet;

public class FlightCalculation { //calculates shortest files using an adjacency list

    private AdjacencyList adjList;
    public FlightCalculation(AdjacencyList adjList){
        this.adjList = adjList;
    }

    //called by main method
    public void printResults(Scanner requestedData, BufferedWriter fileWriter) throws IOException {
        requestedData.useDelimiter("(\\|)|(\\n)");
        int numLines = Integer.parseInt(requestedData.nextLine());
        for (int i = 0; i < numLines; i++) {
            String city1 = requestedData.next();
            String city2 = requestedData.next();
            String type = requestedData.next();
            fileWriter.write("Flight " + (i + 1) + ": " + city1 + ", " + city2);
            if (type.equals("T"))
                fileWriter.write(" (Time)\n");
            if (type.equals("C"))
                fileWriter.write(" (Cost)\n");
            printPaths(getShortestPaths(city1, city2, type), fileWriter);
            fileWriter.write("\n");
        }
    }

    //gets all possible paths
    public LinkedList<LinkedList<String>> getPaths(String source, String dest) {
        TheStack possibilities = new TheStack(); //stack
        LinkedList<String> sou = new LinkedList<>();
        sou.add(source);
        possibilities.push(sou);
        LinkedList<LinkedList<String>> paths = new LinkedList<LinkedList<String>>();

        while (!possibilities.isEmpty()) {
            LinkedList<String> curr = possibilities.pop();
            String top = curr.peek();
            if (top.equals(dest)) {
                LinkedList<String> path = new LinkedList<String>();
                for (String str : curr)
                    path.push(str);
                paths.add(path);
            } else {
                Cities adjacent = adjList.getCities(top);
                for (City c : adjacent.cities) {
                    String s = c.getName();
                    if (!curr.contains(s)) {
                        LinkedList<String> path = new LinkedList<String>();
                        for (String str : curr)
                            path.add(str);
                        path.push(s);
                        possibilities.push(path);
                    }
                }
            }
        }
        return paths;
    }

    //gets 3 shortest paths from all possible paths based on requested file data
    public LinkedList<LinkedList<String>> getShortestPaths(String source, String dest, String var){
        LinkedList<LinkedList<String>> path = getPaths(source, dest);
        LinkedList<LinkedList<String>> shortest = new LinkedList<>();
        int nums[];

        TreeSet<Integer> shortestNums = new TreeSet<Integer>();
        if(var.equals("T")) {
            for (LinkedList<String> l : path) {
                shortestNums.add(getTotalTime(l));
            }
            if(path.size() >= 3)
                nums = new int[3];
            else
                nums = new int[path.size()];
            Iterator<Integer> itr = shortestNums.iterator();
            for(int i = 0; i < nums.length && itr.hasNext(); i++){
                nums[i] = itr.next();
            }
            for(LinkedList<String> l : path){
                if(getTotalTime(l) == nums[0]) {
                    shortest.add(0, l);
                }
                else if(getTotalTime(l) == nums[1]) {
                    if(!shortest.isEmpty())
                        shortest.add(1, l);
                    else
                        shortest.add(l);
                }
                else if(getTotalTime(l) == nums[2]) {
                    if(!shortest.isEmpty() && shortest.size() >1)
                        shortest.add(2, l);
                    else if(!shortest.isEmpty() && shortest.size() == 1)
                        shortest.add(1, l);
                    else
                        shortest.add(l);
                }
            }
            if(shortest.size() > 3){
                while(shortest.size() != 3){
                    shortest.remove(shortest.size()-1);
                }
            }
            if(shortest.size() == 3 && getTotalTime(shortest.get(1)) > getTotalTime(shortest.get(2))){
                LinkedList<String> temp = shortest.get(1);
                shortest.set(1, shortest.get(2));
                shortest.set(2, temp);
            }
        }

        else if(var.equals("C")){
            for (LinkedList<String> l : path) {
                shortestNums.add(getTotalCost(l));
            }
            if(path.size() >= 3)
                nums = new int[3];
            else
                nums = new int[path.size()];
            Iterator<Integer> itr = shortestNums.iterator();
            for(int i = 0; i < nums.length && itr.hasNext(); i++){
                if(itr.hasNext())
                    nums[i] = itr.next();
            }
            for(LinkedList<String> l : path){
                if(getTotalCost(l) == nums[0]) {
                    shortest.add(0, l);
                }
                else if(getTotalCost(l) == nums[1]) {
                    if(!shortest.isEmpty())
                        shortest.add(1, l);
                    else
                        shortest.add(l);
                }
                else if(getTotalCost(l) == nums[2]) {
                    if(!shortest.isEmpty() && shortest.size() >1)
                        shortest.add(2, l);
                    else if(!shortest.isEmpty() && shortest.size() == 1)
                        shortest.add(1, l);
                    else
                        shortest.add(l);
                }
            }
            if(shortest.size() > 3){
                while(shortest.size() != 3){
                    shortest.remove(shortest.size()-1);
                }
            }
            if(shortest.size() == 3 && getTotalCost(shortest.get(1)) > getTotalCost(shortest.get(2))){
                LinkedList<String> temp = shortest.get(1);
                shortest.set(1, shortest.get(2));
                shortest.set(2, temp);
            }
        }
        return shortest;
    }

    //prints shortest paths
    public void printPaths(LinkedList<LinkedList<String>> paths, BufferedWriter fileWriter) throws IOException {
        int j = 0;
        if(paths.size() == 0) {
            fileWriter.write("No routes found.");
            return;
        }
        for (LinkedList<String> l : paths) {
            fileWriter.write("Path " + (j+1) +": ");
            for(int i = 0; i < l.size() - 1; i++){
                fileWriter.write(l.get(i) + " -> ");
            }
            fileWriter.write(l.get(l.size() - 1 ) + ". ");
            int time = getTotalTime(l);
            int cost = getTotalCost(l);
            fileWriter.write("Time: "+ time + " ");
            fileWriter.write("Cost: "+ cost + ".00\n");
            j++;
        }
    }

    //gets total cost of a path
    public int getTotalCost(LinkedList<String> onePath){
        int totalCost = 0;
        String s = onePath.get(0);
        for(int i = 1; i < onePath.size(); i++){
            String d = onePath.get(i);
            Cities adj = adjList.getCities(s);
            totalCost+= adj.getCityWithName(d).getCost();
            s = d;
        }
        return totalCost;
    }

    //gets total time of path
    public int getTotalTime(LinkedList<String> onePath){
        int totalTime = 0;
        String s = onePath.get(0);
        for(int i = 1; i < onePath.size(); i++){
            String d = onePath.get(i);
            Cities adj = adjList.getCities(s);
            totalTime+= adj.getCityWithName(d).getTime();
            s = d;
        }
        return totalTime;
    }
}
