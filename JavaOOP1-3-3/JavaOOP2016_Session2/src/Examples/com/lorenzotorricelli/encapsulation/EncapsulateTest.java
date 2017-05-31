package com.lorenzotorricelli.encapsulation;

public class EncapsulateTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
      Encapsulate encapsulated=new Encapsulate();
       // System.out.println(encapsulate.a); Error
      //  encapsulated.a=3;
	   encapsulated.seta(3); //use of setter
	   
	   System.out.println(encapsulated.geta());
	}

}
