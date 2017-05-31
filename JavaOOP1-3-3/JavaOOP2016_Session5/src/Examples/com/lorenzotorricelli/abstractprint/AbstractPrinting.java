package com.lorenzotorricelli.abstractprint;

// Overriding abstract method 




 abstract class AbstractPrinter{   // IF at least method of the class is abstract THEN the class must be declared abstract too, but not the other way around!
 abstract void printer();
 AbstractPrinter(){
   printer();    // the constructor is called. At compile time, printer, an overridden method, is not bound! No errors here, and compiling goes through!
 }
}


class DerivedPrinter extends AbstractPrinter{
  int i=5;
  void printer(){System.out.println("This printer works and prints " + i) ;} // override of abstract method
  
}





public class AbstractPrinting  {  

  
public static void main(String[] args) {
    DerivedPrinter p=new DerivedPrinter(); // BasePrinter p=new Printer();
    p.printer();
//Useless a=new Useless(); abstract: cannot be instantiated!
}
}
