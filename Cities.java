package com.company;
import java.util.*;

public class Cities { //one list within the adjacency list data structure
    LinkedList<City> cities;
    private String cityName;

    public Cities(String cityName)
    {
        this.cityName = cityName;
        cities = new LinkedList<City>();
    }

    public String getCityName(){
        return this.cityName;
    }

    public void add(City c){
        cities.add(c);
    }

    public City getCityWithName(String cityName){
        for(City c: cities){
            if(c.getName() == cityName)
                return c;
        }
        return null;
    }
}
