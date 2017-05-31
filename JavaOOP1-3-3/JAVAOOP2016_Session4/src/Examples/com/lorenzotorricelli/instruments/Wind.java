package com.lorenzotorricelli.instruments;

//Upcasting. Calling a method of the base class using a reference to a derived class

    class Instrument {
      public void adjust() {
    	  System.out.println("Adjusting pitch...");
      }
      static void tune(Instrument i) {
    	 i.adjust();
    	 }
}
    // Wind objects are instruments!
    
    public class Wind extends Instrument {
      public static void main(String[] args) {
       
    	  Instrument ocarine=new Wind(); //Upcasting at definition
    	  Wind flute = new Wind(); //No upcasting 
       Instrument.tune(ocarine);   //This is now a method called with an instrument reference
      Instrument.tune(flute);  //Method called with a wind reference; implicit upcasting when method is called
      }
} 

