package com.lorenzotorricelli.baseinheritance;

// Constructor of derived classes 



class Base{
	Base(){
		System.out.println("Base constructor");
	}

}




public class Derived extends Base { //It does not need to same or stronger privileges

	Derived(){
		//super();
		System.out.println("Derived constructor");
		
	}

	public static void main(String[] args) { // We are learning the main method can be everywhere
		Derived derived=new Derived();
	}
}