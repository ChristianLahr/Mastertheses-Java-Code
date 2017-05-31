
package com.lorenzotorricell.cyclespolymorphism;

// Upcasting two different derived classes

public class CyclesPolymorphism  {  

	public static void ride(Cycle c){ //Polymorphism is executed here: the method is not a memeber of the upcast hierarchy structure but it is external (main Ex class in this case) 
		// Even if a reference to the base class is passed The compiler AUTOMATICALLY CALLS THE CORRECT OVERRIDDEN VERSION in the derived class generating the call
		c.pedal(); 
	}  

	public static void main(String[] args) {
		Cycle u=new Unicycle(); // Upcasting. 
		Cycle b=new Bicycle();

		ride(b); //Polymorphic call from an external method 
		ride(u);
		b.pedal();
		//Cycle c=new Cycle();

	}
}


abstract class Cycle{

 abstract void pedal();

}

class Unicycle extends Cycle{  

	@Override	 void pedal(){   

		System.out.println("Riding a unicycle..."); 

	} 
}

class Bicycle extends Cycle{  

	@Override	 void pedal(){   

		System.out.println("Riding a bicycle..."); 

	}
}

//Add a

