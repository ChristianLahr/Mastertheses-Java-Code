package com.lorenzotorricelli.operators;

public class Operators {

	public static void main(String[] args) {
		// We declare a bunch of local variables.
		int a=5;  //Type declaration always before variable assignment!
		int b=6;  //you assign VALUES to primitives using the operator=
		int c=a+b;
		int d=a-b;
		int e=a*b;
		int f=a/b; // result is 0. 
		// This is because the result is declared as being of integer type! 
		double g=(double) a/b;
		double b1=6.0;
        double  h=a+b1; //promotion
		//and NOT (double) (a/b)
		System.out.println("The sum of " + a 
				+ " and " + b + " is " + c );
		System.out.println("The difference of " + a
						+ " and " + b + " is " + d );
        System.out.println("The product of " + a 
						+ " and " + b + " is " + e );
        System.out.println("The ratio of " + a +
				 " and " + b + " is not " + f + ";"  );
        System.out.println("The ratio of " + a +""
				+ " and " + b + " is " + g );
         
         System.out.println("The sum of " + a +""
 				+ " and " + b1 + " is still " + h );
          
         // More types
         String s="string!";
         System.out.println(s );
         char x='A'; //note the single quotation marks
         System.out.println(x + " " + s);
         boolean bool=true; //boolean takes values true or false
         
         //Logical operator
         System.out.println(bool == false); //System.out.println prints a lot of things, interesting no?
         System.out.println(bool != true);
         System.out.println(a < 10 && b > 30);
         System.out.println(a > 10 || d <= -1);
        
         //Increments and decrements

         System.out.println("++c: " + ++c);	        
         System.out.println("--e: " + --e);
         
         System.out.println("++c: "  + c++);	        
         System.out.println("--e: " + e--);
         
         //It appears to have stayed the same...

         System.out.println("++c :" + ++c);	        
         System.out.println("--e :" + --e);
         
         //and now the increments and decrements happened by 2. But why?

	}

}
