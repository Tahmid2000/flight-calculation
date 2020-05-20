package com.company;

public class City{ //represents path between the root node of the adjacency list and the city

    private String name;
    private int time;
    private int cost;

    public City(String name, int cost, int time){
        this.name = name;
        this.cost = cost;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public int getTime() {
        return time;
    }

}
