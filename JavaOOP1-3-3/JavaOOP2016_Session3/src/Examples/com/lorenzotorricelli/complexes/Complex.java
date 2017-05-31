package com.lorenzotorricelli.complexes;



/*This Complex class includes pretty much everything we have covered so far. Please
read the code trying to understand as much as you can,
test the methods in main() (and take note of possible mistakes). We will discuss this 
next week

EXERCISE: Implement the class calculating the principal branch of the complex logartihm
along with every possible other method which may be necessary (yes, there are some...)

public Complex principalBranchOfComplexLogarithm(Complex c){
      .....
	}*/
	  




public class Complex {
	private double realPart;
	private double imaginaryPart;


	//CONSTRUCTORS

	public Complex (double z, double w){ //Constructor
		realPart=z;  
		imaginaryPart= w;
	}

	public Complex (double z){ //Overloaded constructor
		realPart=z;

	}


	public Complex (double r, double theta, String s){ 
		//Calls the first constructor using .this! Note that the Math.cos(theta) produces
		//a double so we are fine with the signature! String, write "polar"
		this(r*Math.cos(theta), r*Math.cos(theta)); // We use .this!!!!

	}
	
//	public static Complex complexFromPolar(double r, double theta){ 
//		return new Complex(r*Math.cos(theta),r*Math.sin(theta));
	//}


	//GETTERS and SETTERS

	public void setRealPart(double realPart) {
		this.realPart = realPart;                    //another use of .this!
	}


	public double getRealPart() {
		return realPart;
	}

	public void setImaginaryPart(double imaginaryPart) {
		this.imaginaryPart = imaginaryPart;
	}

	public double getImaginaryPart() {
		return imaginaryPart;
	}


	public Complex conjugate(){   
		return new Complex(realPart,-imaginaryPart);  //returning a new object created on the spot!
	}


	//Modulus...a simple method returning a double

	public	 double abs(){
		return Math.sqrt(realPart*realPart+imaginaryPart*imaginaryPart);
	}



	// And now... A STATIC METHOD! The Euler exponential  is
	//logically part of the complex numbers (it returns a complex) but it 
	//does not need any specific complex to work, only a double parameter (the argument, the "angle")

	public static Complex eulerExponential(double argument){
		return new Complex(1, argument, "polar" ); // We simply call the "polar coordinates constructor"

	}

	//Sum and product binary operators of the current object
	//with another complex

	public Complex sum(Complex c){  
		return new Complex(c.realPart+realPart, c.imaginaryPart+imaginaryPart);


	}

	public Complex product(Complex c){
		return new Complex ( c.realPart*realPart-c.imaginaryPart*imaginaryPart,
				c.imaginaryPart*realPart+c.realPart*imaginaryPart);


	}



	// Imaginary sign operator. Useful for printing-Just for fun!

	String imaginarySign () {
		return imaginaryPart >=0 ?  "+": "-";
	}

	void show(){  // A print method show. It is very fancy, if you want to look at it
		// good, but it is not really important
		if (imaginaryPart !=0.0){
			System.out.println(realPart + " " + imaginarySign() + " i " + Math.abs(imaginaryPart));//gets the right sign  BEFORE the imaginary unit!
		}else{
			System.out.println(realPart); //removes "i 0"
		}
	}
}






