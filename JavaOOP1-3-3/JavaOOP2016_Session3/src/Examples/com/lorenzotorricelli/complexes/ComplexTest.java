package com.lorenzotorricelli.complexes;

public class ComplexTest {
	public static void main(String[] args) {
		Complex c = new Complex(3.0, 5.0); // Constructor 1
		Complex c2 = new Complex(3, 0.0); // Constructor 2
		Complex c3 = new Complex(3.0, Math.PI, "polar"); // Constructor 3
		c.show();
		c2.show();
		c3.show();

		Complex c4 = c.conjugate();
		c4.show();
		c.show();
		System.out.println(c2.abs());
		c3.sum(c2).show(); // I apply the sum method, then show() !!
		c.product(c4).show();
		c2.product(c).show();
		Complex.eulerExponential(Math.PI).show();
	}
}