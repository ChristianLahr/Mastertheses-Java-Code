
package com.lorenzotorricelli.component;

// Constructing derived classes and cleaning them up



class Vice{//Evil1 e1=new Evil1();
  
//  Evil2 e2=new Evil2();   //initialization at the point of definition
  
 // Evil3 e3=new Evil3();
  Vice() {                            //default constructors 
    System.out.println("The root of all evils" );
  

  }

}



class Evil1 {
	Evil1 () {
		System.out.println("Satan ");
	}
}



class Evil2 {
	Evil2 () {
		System.out.println("Beelzebub");
	}

}



class Evil3 {
	Evil3 () {
		System.out.println("Lucifer ");
	}
}


public class Damnation extends Vice{
		// Evil1 e1=new Evil1();
		 
		//  Evil2 e2=new Evil2();
		  
		//  Evil3 e3=new Evil3();  //Comment this out  while commenting the analogous declaration in the constructor, and see what happens
		 

	Damnation(){
		System.out.println("More evil");

		Evil1 e1=new Evil1();

	Evil2 e2=new Evil2();

		Evil3 e3=new Evil3();
	}

	public static void main(String[] args) {


		Damnation d=new Damnation(); //Output: Base class constructor called first, so repetition of the above. THEN member data are initialized and the components constructors
		//are called, so the component messages are System.out.printlned: finally the message is System.out.printlned 

	}
}



