package com.lorenzotorricelli.innerouter;






//Returning inner class reference appropriately initialized


public class AccessingFromInner {

	public static void main(String[] args) {
		Outer o = new Outer();
		Outer.Inner i= o.new Inner();  // operator o.new to create inner class!
		System.out.println(i.geti()); //inner class have access to private outer class data!!!! And the other way around?	 
}
}

class Outer{
	private int i=9;
   class Inner {
	//private?
		
		Inner(){    //Inner class constructor
			System.out.println("Inner initialized"); 
			} 
	public int geti(){
			return i;
			} 
	}
}

/* Now, by modifying this program

1) We have seen that inner class can access outer class private fields
 check if the other way around is also true.
 2) Add a private method to the outer class. Can the inner class access it? If so, how?
 3) Inner classes for implementation hiding. Make the inner class private and in 
 the outer class write a method that creates an inner class. Can you use it in main??
 4) As above but the inner class now ALSO implements an interface InterfaceForInnerClass. Now upcast to the interface in main!



*/