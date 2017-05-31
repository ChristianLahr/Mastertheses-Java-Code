package com.lorenzotorricelli.enums;

//ENumaated types, switch and foreach syntax.

public class CurrencyTest {

		  public static void main(String[] args){
		     for(Currency c : Currency.values()){  //For each syntax!!! 
		    	 //Another way to do a for loop...Currency values is a static method for the Enum type Currency
		    switch(c.ordinal()+1){  // A switch
		    case 0: System.out.println(c.ordinal()+1); break;  //what does it print

		    case 1: System.out.println(c.ordinal()+1 + " "  + c + ": is the currency of the EU");  break;
		    case 2: System.out.println(c.ordinal()+1 + " " + c + ": is the reference country in the world " );  break;
		    case 3: System.out.println(c.ordinal()+1 + " " +  c + ": never goes down");  break;
		    case 4: System.out.println(c.ordinal()+1+ " "+ c + ":can emerging currency");  break;
		    
		    
		    
		    }
		}
		  }
}

