package com.lorenzotorricelli.geometricrv;
import java.util.*;

//A geometric random variable using a while loop

public class WhileRandom {

	public static void main(String[] args) {
		int launch=0;
		Random RandomNumberGenerator=new Random();
		do{
		
		launch++;}
		while(RandomNumberGenerator.nextInt(2)==0);
 System.out.println("I got a tail after " + launch +" launches");
	}

}
