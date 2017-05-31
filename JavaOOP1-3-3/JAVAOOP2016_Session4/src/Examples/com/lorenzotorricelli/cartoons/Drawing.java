
package com.lorenzotorricelli.cartoons;

import com.lorenzotorricelli.baseinheritance.Derived;

// Constructing derived classes 



class Draw{  //have package access. I can´t be bothered creating new classes, and plus you get a better ideas of what is happening
	//if all the code is contained in a compilation unit
  
  Draw(){
  System.out.println("Blank sheet");
  };    //without defining a default consturctor in the base class, super with no argument cannot be called by the derived claaa!!
  Draw(String s){
  System.out.println("Beginning to draw");
  }
  Draw(String s, String t){
	  System.out.println("Beginning to double draw");
	  }
}


 class Cartoon extends Draw{
  Cartoon(){
	//super(); //once the arg constructor is specified, no defa1ult constructor can be called by the derived class using the super() method
    System.out.println("A blank sheet which should contain a cartoon");
  }
  Cartoon(String s){
   super(s);
    System.out.println("Drawing the cartoon " + s);
  }	
  Cartoon(String s, String t){
	   super(s,t);
	    System.out.println("Drawing the double cartoon " + s);
	  }
 
}




public class Drawing{

public static void main(String[] args) {
      
    Cartoon d1= new Cartoon();
     Cartoon d= new Cartoon("Mickey", " Mouse");
   

}
}