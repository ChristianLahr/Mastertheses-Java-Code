package com.lorenzotorricelli.overloading;

public class OverloadedPrinter {
    void print(){
    	System.out.println("This is a print method") ;
    }
    void print(String s){
    	System.out.println("This is an overloaded print method, printing " + s) ;
    }
}

