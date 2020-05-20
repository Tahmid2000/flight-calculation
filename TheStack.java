package com.company;

import java.util.LinkedList;

public class TheStack { //class used for backtracking; implements stack with a linked list
    private LinkedList<LinkedList<String>> paths;

    public TheStack(){
        paths = new LinkedList<LinkedList<String>>();
    }

    public void push(LinkedList<String> poss){
        paths.push(poss);
    }

    public boolean isEmpty(){
        return paths.isEmpty();
    }

    public LinkedList<String> pop(){
        return paths.pop();
    }
}
