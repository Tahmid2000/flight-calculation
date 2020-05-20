package com.company;

import java.util.*;
import java.io.*;

public class AdjacencyList { //core class, implements adjacency list data structure

    private LinkedList<Cities> list;
    private int size;

    public AdjacencyList() {
        list = new LinkedList<Cities>();
    }

    public void add(Cities c) {
        list.add(c);
    }

    public void addEdge(String c1, String c2, int cost, int time) {
        City city1 = new City(c1, cost, time);
        City city2 = new City(c2, cost, time);
        getCities(c1).add(city2);
        getCities(c2).add(city1);
    }

    public Cities getCities(String cityName) {
        for (Cities c : list) {
            if (c.getCityName().equals(cityName))
                return c;
        }
        return null;
    }

    public boolean contains(String c) {
        if (list.contains(getCities(c)) == true)
            return true;
        return false;
    }

    //creates adjacency list with flight data
    public void createAdjList(Scanner flightData) {
        flightData.useDelimiter("(\\|)|(\\n)");
        int numLines = Integer.parseInt(flightData.nextLine());
        size = numLines;
        for (int i = 0; i < numLines; i++) {
            Cities citiesA = createCities(flightData);
            Cities citiesB = createCities(flightData);

            String nameCityA = citiesA.getCityName();
            String nameCityB = citiesB.getCityName();
            int cost = Integer.parseInt(flightData.next());
            int time = Integer.parseInt(flightData.next());

            addEdge(nameCityA, nameCityB, cost, time);
        }
    }

    //used by createAdjList to make separate Cities objects for the whole structure
    public Cities createCities(Scanner flightData) {
        String newCityName = flightData.next();
        if (!contains(newCityName)) {
            Cities c1 = new Cities(newCityName);
            this.add(c1);
            return c1;
        }
        return getCities(newCityName);
    }
}