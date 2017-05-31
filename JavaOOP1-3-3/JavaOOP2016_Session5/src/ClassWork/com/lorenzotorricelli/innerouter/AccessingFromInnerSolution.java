package com.lorenzotorricelli.innerouter;






//Returning inner class reference appropriately initialized


public class AccessingFromInnerSolution {

	public static void main(String[] args) {
		OuterSolution o = new OuterSolution();
		InterfaceForInnerClass i=o.createInner();
		 //private inner class. To create an inner class one must defined a method returning a new object in the outer class
		System.out.println(i.geti()); //inner class have access to private outer class data!!!! And the other way around?	 
        System.out.println(o.getInnerData()); //private data accessible from outer class;
         i.executeFoo(); //access dat private
	}
}

class OuterSolution{
	private int i=9;
	
	public double getInnerData(){
		return new InnerSolution().j;
	}
	
	private void foo(){};
	//public Inner  //NO! Inner is inaccessible
	public InterfaceForInnerClass createInner(){ //Upcast the return to interfaceForInnerClass instead!
		return new InnerSolution();	}
	
private  class InnerSolution implements InterfaceForInnerClass {
	      
		public void methodForInnerClass(){};
		
		
		InnerSolution(){    //Inner class constructor
			System.out.println("Inner initialized"); 
			} 
		private int j=8;
	
		public int geti(){
			return i;
			}
		public void executeFoo(){   //private outer method access
	      OuterSolution o=new OuterSolution();
	      o.foo();
	       OuterSolution.this.foo();
		}
		
	}
}

/* Now, by modifying this program

1) We have seen that inner class can access outer class private fields
 check if the other way around is also true.
 2) Add a private method to the outer class. Can the inner class access it? If so, how?
 3) Inner classes for implementation hiding. Make the inner class private and in 
 the outer class write a method that creates an inner class. Can you use it in main??
 4) As above but the inner class now ALSO implements an interface InterfaceForInnerClass. Now upcast to the interface in main!



*/