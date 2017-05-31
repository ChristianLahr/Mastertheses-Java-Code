


package com.lorenzotorricelli.amphibians;


// Upcasting; simpler version

class Amphibian{
  
  
   void swimming(){       //providing base class methods
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
}

 public class AnimalBehaviour{
  static void amphibianBehavior(Amphibian a){
      a.swimming();
      a.walking();
      a.creaking();
     
  
  }
    
    
  public static void main(String[] args){
  //Frog f=new Frog();
     Frog frog=new Frog(); //reference upcast.
  frog.swimming(); // base class versions are called
 frog.walking();
  frog.creaking();
  
       Amphibian a=new Amphibian();
       a.swimming(); // base class versions are called
       a.walking();
        a.creaking();
        
       
 //Method upcast
 // Frog secondfrog=new Frog();   
 // AnimalBehaviour.amphibianBehavior(secondfrog);  // Since the methods have not been overridden, the call is made to the amphibian methods 
  
  
  }
}

