package com.lorenzotorricelli.shapes;
import java.util.*;

class Shape {
	public void draw() {}  //Not implemented..."abstract" class
}

class Circle extends Shape {
	@Override 
	public void draw() { 
		System.out.println("Drawing a Circle"); 
	}
} 



class Square extends Shape {
	@Override 
	public void draw() { 
		System.out.println("Drawing a square"); 
	}
} 

class Triangle extends Shape {
	@Override 
	public void draw() { 
		System.out.println("Drawing a triangle"); 
	}
} 

class RandomShapeGenerator {   //A "factory" that randomly creates shapes.

	private Random rand = new Random();
	public Shape nextShape() {
		switch(rand.nextInt(3)) {
		default: ; //compulsory
		case 0: return new Circle();
		case 1: return new Square();
		case 2: return new Triangle();
		} }
}
public class Shapes{
	private static RandomShapeGenerator gen =new RandomShapeGenerator();
	public static void main(String[] args) {
		Shape[] s = new Shape[9];
		// Fill up the array with shapes:
		for(int i = 0; i < s.length; i++)
			s[i] = gen.nextShape();  //automatic upcasting
		// Make polymorphic method calls:
		for(Shape shp : s)  //polymorphic access to the array s
			shp.draw();    //call of the correct overriden method
	}
}