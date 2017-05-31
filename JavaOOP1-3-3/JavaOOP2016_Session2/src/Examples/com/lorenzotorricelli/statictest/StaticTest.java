package com.lorenzotorricelli.statictest; 

public class StaticTest{                                    
  public static void main(String[] args){
	 
	  Tested.print();
	  
	  
  Tested o1=new Tested();   
   Tested o2=new Tested(); 
     o1.i=6;
    
     o1.print();
     o2.print();
     Tested.staticImodifier();
     o1.print();
     o2.print();   
  }

}

