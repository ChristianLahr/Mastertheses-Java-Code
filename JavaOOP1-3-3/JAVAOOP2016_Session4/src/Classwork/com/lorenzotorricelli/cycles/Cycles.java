

package com.lorenzotorricelli.cycles;



// Upcasting using two different derived classes




class Cycle{
	
}

class Unicycle extends Cycle{ 


}

class Bicycle extends Cycle{ 
}




public class Cycles{
public static void ride(Cycle c){ 
	//System.out.println("Riding a cycle...");

} 

public static void main(String[] args) {
	Unicycle u=new Unicycle(); // Upcasting.
	Cycle b=new Bicycle();

	Cycles.ride(b); 
	Cycles.ride(u);

}
}