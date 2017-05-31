package com.lorenzotorricelli.detergent;



class Cleanser {
  public void dilute() { System.out.println("Cleanser dilution"); }
  public void apply() { System.out.println("Cleanser application"); }
  
  }

public class Detergent extends Cleanser {
  
  public void scrub() {  // A new method!
	    
	  System.out.println("Detergent scrub");
  }
  //Overriding!:
  @Override  //Override tag: returns an error if you are NOT overriding!
  public void apply() { 
	  super.apply(); //class the base-class version of the method
	  System.out.println ("Detergent application");
   }
  
  // Test the new class:
  public static void main(String[] args) {
    Detergent cillitBang = new Detergent();
    cillitBang.dilute(); //available in the base class not overriden
    cillitBang.apply(); //available in the base class, overridden in the derived class
    cillitBang.scrub(); //available in the base class
  }
}