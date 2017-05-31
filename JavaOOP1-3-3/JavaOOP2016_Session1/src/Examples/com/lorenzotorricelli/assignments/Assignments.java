package com.lorenzotorricelli.assignments;

public class Assignments{
	static public void main (String[] args){
		Temperature T1 =new Temperature();
		Temperature T2= new Temperature ();
		System.out.println(T1.temp); //When object are created to initialized vairables, the compiler gives them a default value. 
		//for double such value is....
		System.out.println(T2.temp);
		T2=T1; // one object to be "equal" to another one.
		T1.temp=12.19f; //suffix f to indicate that we are using a floating point number 
		System.out.println(T1.temp);
		System.out.println(T2.temp); 
		System.out.println(T1.temp==T2.temp);  //surprise! What has happened?
		
		Temperature T3= new Temperature ();
		T3.temp=12.19f; //Initializing the field at same value!
		System.out.println(T1.equals(T3)); //true or false?
           //Let's try this equals() method...
		System.out.println(T1.equals(T3)); // not quite right. Seems like we still have reference comparison...
		//but hang on...
		Integer i1  =new Integer(42);
		Integer i2= new Integer(42); //(Integer: wrapper class)
		System.out.println(i1.equals(i2)); //I see! It is correctly implemented for checking values on classes already
		//defined in Java (Integer in this case)! This is an example of Overloading (more to come on this...)
	} 

}