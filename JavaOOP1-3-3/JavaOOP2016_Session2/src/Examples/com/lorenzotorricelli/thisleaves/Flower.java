package com.lorenzotorricelli.thisleaves;


public class Flower {
  int petalCount = 0;
  String s = "some text";
  Flower(int petals) {
    petalCount = petals;
    System.out.println("Constructor 1, petalCount= "
      + petalCount);
  }
  Flower(String ss) {
	 s = ss; 
	 System.out.println("Constructor 2, s = " + ss);
}
  Flower(String s, int petals) {
    this(petals); //Calling the constructor whose signature is integers only
//!    this(s); // Can’t call two!
    this.s = s; // Another use of "this"!!
    System.out.println("Constructor 3, String & int args");
  }
  Flower() {
    this("A bonus message", 42);  //Defining the default constructor calling constructor 3
    //using this
    System.out.println ("Default constructor (no args)");
  }
  void printPetalCount() {
//! this(11); // Not inside non-constructor!
	  System.out.println("petalCount = " + petalCount + " s = "+ s);
  }
  public static void main(String[] args) {
    Flower x = new Flower();
    x.printPetalCount();
  }
}