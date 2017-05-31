package com.lorenzotorricelli.hiworldOOP;

public class HiWorld {

	public static void main(String[] args) {
		Message message= new Message(); //Object creation
        System.out.println("Hi world! " + message.s ); //Access to object field
        message.print();  //Access to class method
	}

}



  