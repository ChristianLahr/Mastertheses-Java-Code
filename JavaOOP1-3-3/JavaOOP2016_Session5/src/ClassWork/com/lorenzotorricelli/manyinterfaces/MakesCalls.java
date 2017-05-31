package com.lorenzotorricelli.manyinterfaces;

interface FirstInterface {
 void firstMethod();
}


interface SecondInterface {
void secondMethod();
}


interface InheritingInterface extends FirstInterface, SecondInterface{
	void inheritingMethod();
	
}
class FirstClass{
	void aClassMethod(){
		System.out.println("A class method");
	}
}

class LastClass extends FirstClass implements InheritingInterface {
 public void firstMethod(){
	 System.out.println("Implementation of method 1"); 
 }

 public void secondMethod(){
	System.out.println ("Implementation of method 2");
 
 }

 public void inheritingMethod(){
		System.out.println ("Implementation of inheriting method");
 }	 	 
		void aClassMethod(){
			System.out.println("Overriding of class method");
		}

 }
 


public class MakesCalls{
	public static void caller1(FirstInterface first){
	    first.firstMethod();  
	}; 
	public static void caller2(SecondInterface second){
		second.secondMethod();}; 
	public static void caller3(InheritingInterface inherit){
			inherit.inheritingMethod();}; 
    public static void caller4(FirstClass f){
				f.aClassMethod();}; 
		    
	 public static void main(String[] args){
		 LastClass last=new LastClass();
		 caller1(last);
		 caller2(last);
		 caller3(last);
		 caller4(last);
	}
}