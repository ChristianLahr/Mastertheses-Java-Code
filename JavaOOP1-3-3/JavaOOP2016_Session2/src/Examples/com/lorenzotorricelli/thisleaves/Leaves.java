package com.lorenzotorricelli.thisleaves;

public class Leaves{
	public static void main(String[] args) {
		Leaf x = new Leaf(0);
		x.increment().increment().increment().print();
		Flower daisy=new Flower();
		daisy.printPetalCount();
	}
} 
