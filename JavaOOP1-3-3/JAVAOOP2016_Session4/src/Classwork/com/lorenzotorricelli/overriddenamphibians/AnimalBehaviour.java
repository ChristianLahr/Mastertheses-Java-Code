
package com.lorenzotorricelli.overriddenamphibians;

// Upcasting; simpler version

class Amphibian{
  
  
  void swimming(){       //providing methods accepting as a parameter class objects
	  System.out.println("Amphibian swimming");
  };
  void walking(){
    System.out.println("Amphibian walking");
  };
  void creaking(){
    System.out.println("Amphibian creaking");
  };
}

class Frog extends Amphibian{
  Frog(){ 
  System.out.println("I am a frog");}  //no methods overridden
  void swimming(){       //providing methods accepting as a parameter class objects
	    System.out.println("Frog swimming");
	  };
	  void walking(){
	    System.out.println("Frog walking");
	  };
	  void creaking(){
	    System.out.println("Frog creaking");
	  };

}

 public class AnimalBehaviour{
  static void amphibianBehavior(Amphibian a){
      a.swimming();
      a.walking();
      a.creaking();
     
  
  }
    
    
  public static void main(String[] args){
  //Frog f=new Frog();
     Frog frog=new Frog(); //reference upcast. Change the type declaration with amphibian
  frog.swimming(); // overridden version are called, that is those of FROG
 frog.walking();
  frog.creaking();
 //Method upcast
  Frog secondfrog=new Frog();   
  AnimalBehaviour.amphibianBehavior(secondfrog);  // Methods overridden! Returns a frog behaviour!!
  
  
  }
}

