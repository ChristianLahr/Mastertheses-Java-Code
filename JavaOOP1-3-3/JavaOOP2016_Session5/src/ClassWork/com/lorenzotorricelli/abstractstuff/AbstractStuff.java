package com.lorenzotorricelli.abstractstuff;



public class  AbstractStuff{  
	  
	 static void updown(Useless u){
	     u.MethodOne();        //"downcaster". note the parenthesis use. Artisanal way for calling the method in the derived class extending the abstract one
	 }      
	  
	public static void main(String[] args) {
	            Useless u=new OneMethod(); //upcast
	            AbstractStuff.updown(u);
	}
	}
 
 abstract class Useless{
 abstract void MethodOne();
	}

	class OneMethod extends Useless{
	  void MethodOne(){ System.out.println("This class has one method");
	  }

	  }
