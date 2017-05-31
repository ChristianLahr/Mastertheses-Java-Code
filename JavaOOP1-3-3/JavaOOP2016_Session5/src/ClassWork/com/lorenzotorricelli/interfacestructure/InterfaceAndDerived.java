package com.lorenzotorricelli.interfacestructure;

public class InterfaceAndDerived extends Base implements FirstInterface, SecondInterface{
	//if you comment this out, the compiler it compliaints that you are not implementing a method
	public void hiInterface(){ //if you leave out public it would complain (you are restrciting the access!)
		System.out.println("Hi world! This is my first interface implementation!");
	}
	@Override
	void aLameBaseClassMethod(){
		System.out.println("Instead, this is a boring old school base class method...");
	}

	public void multipleImplementation(){
		System.out.println("Cool! A class can implement more than one interface...");
	}
	public void anInterfaceExtendingMethod(){
		System.out.println("...and I can even extend an Interface and add new methods!!");
	};

	public static void main(String[] args){
		InterfaceAndDerived i=new InterfaceAndDerived();
		i.hiInterface();
		i.aLameBaseClassMethod();
		i.multipleImplementation();
		i.anInterfaceExtendingMethod();
	}
}


abstract class Base{
	void aLameBaseClassMethod(){};
}
