package com.lorenzotorricelli.primenumbers;

//Prime numbers finder, up to n
public class Primes{
  public static void main(String[] args){
   // n=Integer.parseInt(args[0]); // Transforms strings in numbers
    int n=26;
	for(int j=2; j<=Math.sqrt(n); j++){          
    	if(n%j==0){
    	System.out.println(n + " it's not prime");
           return;    } //return finishes the loop
          
    }
  System.out.println(n + " is prime");
  }
}
