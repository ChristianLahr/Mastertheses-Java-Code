package com.lorenzotorricelli.dog;
//Constructor overloading
public class DogConstructor{
	public static void main(String[] args){
		Dog Snoopy=new Dog();   
		System.out.println(Snoopy);
		Snoopy.bark();
		Snoopy.bark(2);
		Snoopy.bark(13.66f);
		Snoopy.bark( 8.4);
	}
}