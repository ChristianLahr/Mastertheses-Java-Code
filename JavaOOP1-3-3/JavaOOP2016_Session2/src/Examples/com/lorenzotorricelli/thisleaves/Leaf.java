package com.lorenzotorricelli.thisleaves;

public class Leaf {
	  int i ;
	  Leaf(int i){
	  this.i=i;     //a first use of if
	  }
	  
	  Leaf increment() {
	i++;
	    return this; // a second use of if. Multiple calling.
	  }
	  void print() {
	    System.out.println("i = " + i);
	  }
}
